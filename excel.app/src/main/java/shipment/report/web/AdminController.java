package shipment.report.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shipment.report.Util;
import shipment.report.db.DbService;
import shipment.report.db.model.Bag;
import shipment.report.db.model.Stock;
import shipment.report.db.model.TradeMe;
import shipment.report.original.Constants;

@Controller
public class AdminController {
	private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines();
	private static Log logger = LogFactory.getLog(AdminController.class);
	private static final String REDIRECT_ADMIN = "redirect:/admin";

	private DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");

	@Autowired
	DbService dbService;

	@PostMapping("/load/tab1")
	public String tradeMeUpload(@RequestParam("file") MultipartFile file, @RequestParam("clear") boolean clear, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (isFileEmpty(file, redirectAttributes)) return REDIRECT_ADMIN;

		logger.info(file.getOriginalFilename() + " uploaded from " + request.getRemoteAddr() + ".");
		Reader in;
		Iterable<CSVRecord> records;
		try {
			in = new InputStreamReader(file.getInputStream());
			records = CSV_FORMAT.parse(in);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("Failed to parse csv file: " + file.getOriginalFilename(), e);
			return REDIRECT_ADMIN;
		}
		ArrayList<TradeMe> tradeMes = new ArrayList<TradeMe>();
		ArrayList<TradeMe> tradeMeShips = new ArrayList<TradeMe>();
		for (CSVRecord record : records) {
			TradeMe data = parseTradeMe(record);
			if (!data.getProductCode().equalsIgnoreCase("SHIP")) {
				tradeMes.add(data);
			} else {
				tradeMeShips.add(data);
			}
		}
		for (TradeMe ship : tradeMeShips) {
			for (TradeMe tradeMe : tradeMes) {
				if (ship.getShipmentNumber() != null && ship.getProductCode() != null) {
					if (ship.getShipmentNumber().equals(tradeMe.getShipmentNumber())) { //&& ship.getProductCode().equals(tradeMe.getProductCode())
						tradeMe.setUnitPriceIncTax(ship.getUnitPriceIncTax());
					}
				}
			}
		}
		try {
			if (clear) {
				dbService.reloadTradeMe(tradeMes);
			} else {
				dbService.addOrUpdateTradeMes(tradeMes);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("Failed to update H2: " + file.getOriginalFilename(), e);
			return REDIRECT_ADMIN;
		}
		redirectAttributes.addFlashAttribute("message", "Table updated!");
		return REDIRECT_ADMIN;
	}

	/**
	 * @param file upload file
	 * @param clear remove previous data
	 */
	@PostMapping("/load/tab2")
	public String bagUpload(@RequestParam("file") MultipartFile file, @RequestParam("clear") boolean clear, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		if (isFileEmpty(file, redirectAttributes)) return REDIRECT_ADMIN;

		logger.info(file.getOriginalFilename() + " uploaded from " + request.getRemoteAddr() + ".");
		Reader in;
		Iterable<CSVRecord> records;
		try {
			in = new InputStreamReader(file.getInputStream());
			records = CSV_FORMAT.parse(in);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("failed to parse csv file: " + file.getOriginalFilename(), e);
			return REDIRECT_ADMIN;
		}
		ArrayList<Bag> bags = new ArrayList<Bag>();
		for (CSVRecord record : records) {
			Bag data = parseBag(record);
			bags.add(data);
		}
		try {
			if (clear) {
				dbService.reloadBag(bags);
			} else {
				dbService.addOrUpdateBags(bags);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("Failed to update H2: " + file.getOriginalFilename(), e);
			return REDIRECT_ADMIN;
		}
		redirectAttributes.addFlashAttribute("message", "Table updated!");
		return REDIRECT_ADMIN;
	}

	@PostMapping("/remove/tab1")
	public String tradeMeRemove(@RequestParam("shipmentNumber") String shipmentNumber, @RequestParam("productCode") String productCode,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("Remove TradeMe recorder for Shipment Number " + shipmentNumber + " from " + request.getRemoteAddr() + ".");
		try {
			dbService.removeTradeMe(shipmentNumber, productCode);
			redirectAttributes.addFlashAttribute("message", "Record removed!");
		} catch (Exception e) {
			logger.warn("Fail to remove record", e);
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/remove/tab2")
	public String bagRemove(@RequestParam("sku") String sku, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("Remove bag recorder for SKU" + sku + " from " + request.getRemoteAddr() + ".");
		try {
			dbService.removeBag(sku);
			redirectAttributes.addFlashAttribute("message", "Record removed!");
		} catch (Exception e) {
			logger.warn("Fail to remove record", e);
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/remove/in_stock")
	public String stockRemove(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("Remove all stock records from " + request.getRemoteAddr() + ".");
		try {
			dbService.removeAllStocks();
			redirectAttributes.addFlashAttribute("message", "Stock Records removed!");
		} catch (Exception e) {
			logger.warn("Fail to remove record", e);
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return REDIRECT_ADMIN;
	}

	@PostMapping("/stock/in_stock/{opt}")
	public String stockIn(@RequestParam("file") MultipartFile file, @PathVariable String opt, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (isFileEmpty(file, redirectAttributes)) return REDIRECT_ADMIN;

		logger.info("Started stock in file: " + file.getOriginalFilename() + " from " + request.getRemoteAddr() + ".");

		Reader in;
		Iterable<CSVRecord> records;
		try {
			in = new InputStreamReader(file.getInputStream());
			records = CSV_FORMAT.parse(in);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("Failed to parse csv file: " + file.getOriginalFilename(), e);
			return REDIRECT_ADMIN;
		}

		List<Stock> stocks = new ArrayList<Stock>();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		boolean isAdd = opt.trim().equalsIgnoreCase("in"); // stock in or stock out
		for (CSVRecord record : records) {
			if (record.get(0).trim().isEmpty()) continue;
			Stock stock = parseStock(record, time, isAdd);
			stocks.add(stock);
		}

		try {
			dbService.addStocks(stocks);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("Failed in stock transaction for file: " + file.getOriginalFilename(), e);
			return REDIRECT_ADMIN;
		}

		redirectAttributes.addFlashAttribute("message", "Stock transaction succeeded!");
		return REDIRECT_ADMIN;
	}

	@GetMapping("/download")
	public @ResponseBody void downloadCsv(@RequestParam("table") String table, HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		String fileName = getFileName(table);
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");
		try {
			CSVFormat csvFileFormat = setCsvHeader(table);
			Appendable ad = response.getWriter();
			CSVPrinter csvFilePrinter = new CSVPrinter(ad, csvFileFormat);
			List<String[]> values = setCvsValues(table);
			csvFilePrinter.printRecords(values);
			csvFilePrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	private String getFileName(String table) {
		String date = dateFormat.format(new Date());
		String fileName = table + date;
		if ("tab1".equals(table)) {
			fileName = "trademe_shipment_" + date;
		} else if ("tab2".equals(table)) {
			fileName = "fastway_bag_" + date;
		} else if ("tab3".equals(table)) {
			fileName = "fastway_shipment_" + date;
		}

		return fileName;
	}

	private CSVFormat setCsvHeader(String table) {
		CSVFormat csvFileFormat = null;
		if ("tab1".equalsIgnoreCase(table)) {
			csvFileFormat = CSVFormat.DEFAULT.withHeader(new String[] { Constants.TAB1.SHIPMENT_NUMBER, Constants.TAB1.Status, Constants.TAB1.Warehouse_Code,
					Constants.TAB1.Requested_Shipping_Date_SO, Constants.TAB1.Shipment_Method, Constants.TAB1.Shipped_With, Constants.TAB1.Tracking_Reference,
					Constants.TAB1.Tracking_Reference_RD, Constants.TAB1.Customer, Constants.TAB1.Customer_Reference, Constants.TAB1.Customer_Email,
					Constants.TAB1.Phone_Number, Constants.TAB1.Address1, Constants.TAB1.Address2, Constants.TAB1.Address3, Constants.TAB1.Town_City,
					Constants.TAB1.Post_Code, Constants.TAB1.Country, Constants.TAB1.Region_State, Constants.TAB1.Product_Code,
					Constants.TAB1.Product_Alternate_Code, Constants.TAB1.Product_Name, Constants.TAB1.UOM, Constants.TAB1.Product_Public_Notes,
					Constants.TAB1.Product_Private_Notes, Constants.TAB1.Qty_Requested, Constants.TAB1.Qty_Packed, Constants.TAB1.Qty_Backorder,
					Constants.TAB1.Unit_Price_Inc_Tax, Constants.TAB1.Line_Total_Inc_Tax, Constants.TAB1.Notes, Constants.TAB1.Order_Notes_Public,
					Constants.TAB1.Actual_Shipping_Date_Shipment, Constants.TAB1.Shipped_By });
		} else if ("tab2".equalsIgnoreCase(table)) {
			csvFileFormat = CSVFormat.DEFAULT.withHeader(new String[] { Constants.FASTWAY_BAGS_GD.SKU, Constants.FASTWAY_BAGS_GD.Barcode,
					Constants.FASTWAY_BAGS_GD.Location, Constants.FASTWAY_BAGS_GD.Bag, Constants.FASTWAY_BAGS_GD.Description });
		} else if ("tab3".equalsIgnoreCase(table)) {
			csvFileFormat = CSVFormat.DEFAULT.withHeader(new String[] { Constants.TAB3.Reference, Constants.TAB3.Contact_Name,
					Constants.TAB3.Company_Name_Required, Constants.TAB3.Address1_Required, Constants.TAB3.Address2, Constants.TAB3.Suburb_Required,
					Constants.TAB3.City, Constants.TAB3.Post_Code_required, Constants.TAB3.Email_Address, Constants.TAB3.Phone_Number, Constants.TAB3.Special1,
					Constants.TAB3.Special2, Constants.TAB3.Special3, Constants.TAB3.Packaging, Constants.TAB3.Weight, Constants.TAB3.Count_Quantity,
					Constants.TAB3.Packaging_types, Constants.TAB3.SKU, Constants.TAB3.Qty_Requested, Constants.TAB3.Unit_Price_Inc_Tax });
		} else if ("in_stock".equalsIgnoreCase(table)) {
			csvFileFormat = CSVFormat.DEFAULT.withHeader(new String[] { Constants.IN_STOCK.Barcode, Constants.IN_STOCK.Quantity, Constants.IN_STOCK.Date,
					Constants.IN_STOCK.Note });
		}
		return csvFileFormat;
	}

	private List<String[]> setCvsValues(String table) {
		List<String[]> values = new ArrayList<String[]>();
		if ("tab1".equalsIgnoreCase(table)) {
			List<TradeMe> tradeMes = dbService.getAllTradeMe();
			for (TradeMe tradeMe : tradeMes) {
				values.add(new String[] { tradeMe.getShipmentNumber(), tradeMe.getStatus(), tradeMe.getWarehouseCode(), tradeMe.getRequestedShippingDateSO(),
						tradeMe.getShipmentMethod(), tradeMe.getShippedWith(), tradeMe.getTrackingReference(), tradeMe.getTrackingReferenceRD(),
						tradeMe.getCustomer(), tradeMe.getCustomerReference(), tradeMe.getCustomerEmail(), tradeMe.getPhoneNumber(), tradeMe.getAddress1(),
						tradeMe.getAddress2(), tradeMe.getAddress3(), tradeMe.getTownCity(), tradeMe.getPostCode(), tradeMe.getCountry(),
						tradeMe.getRegionState(), tradeMe.getProductCode(), tradeMe.getProductAlternateCode(), tradeMe.getProductName(), tradeMe.getUOM(),
						tradeMe.getProductPublicNotes(), tradeMe.getProductPrivateNotes(), String.valueOf(tradeMe.getQtyRequested()), tradeMe.getQtyPacked(),
						tradeMe.getQtyBackorder(), tradeMe.getUnitPriceIncTax(), tradeMe.getLineTotalIncTax(), tradeMe.getNotes(),
						tradeMe.getOrderNotesPublic(), tradeMe.getActualShippingDateShipment(), tradeMe.getShippedBy(), });
			}
		} else if ("tab2".equalsIgnoreCase(table)) {
			List<Bag> bags = dbService.getAllBag();
			for (Bag bag : bags) {
				values.add(new String[] { bag.getSku(), bag.getBarcode(), bag.getLocation(), bag.getBag(), bag.getDescription() });
			}
		} else if ("tab3".equalsIgnoreCase(table)) {
			List<Object[]> fastWays = dbService.getAllFastWay();
			for (Object[] fastWay : fastWays) {
				String[] data = new String[fastWay.length];
				for (int i = 0; i < fastWay.length; i++) {
					data[i] = String.valueOf(fastWay[i]);
					if (i == 0) { // Reference
						data[i] = Util.chgReference(data[i]);
					} else if (i == 1) { //notes
						data[i] = Util.chgNote(data[i]);
					} else if (i == 6 && data[5].trim().isEmpty()) {
						data[5] = data[6];
					}
				}
				values.add(data);
			}
		} else if ("in_stock".equalsIgnoreCase(table)) {
			List<Stock> stocks = dbService.getAllStocks();
			for (Stock stock : stocks) {
				values.add(new String[] { stock.getBarcode(), String.valueOf(stock.getQuantity()), stock.getDate(), stock.getNote() });
			}
		}
		return values;
	}

	private Bag parseBag(CSVRecord record) {
		Bag bag = new Bag();
		bag.setSku(record.get(0));
		bag.setBarcode(record.get(1));
		bag.setLocation(record.get(2));
		bag.setBag(record.get(3));
		bag.setDescription(record.get(4));
		return bag;
	}

	private Stock parseStock(CSVRecord record, String time, boolean isAdd) {
		Stock stock = new Stock();
		stock.setBarcode(record.get(0));
		if (isAdd) {
			stock.setQuantity((int) Double.parseDouble(record.get(1).trim()));
		} else {
			stock.setQuantity(-(int) Double.parseDouble(record.get(1).trim()));
		}
		if (record.size() < 3 || record.get(2).trim().isEmpty()) {
			stock.setDate(time);
		} else {
			stock.setDate(record.get(2).trim());
		}
		if (record.size() > 3) {
			stock.setNote(record.get(3));
		}
		return stock;
	}

	private TradeMe parseTradeMe(CSVRecord record) {
		TradeMe tradeMe = new TradeMe();
		tradeMe.setShipmentNumber(record.get(0));
		tradeMe.setStatus(record.get(1));
		tradeMe.setWarehouseCode(record.get(2));
		tradeMe.setRequestedShippingDateSO(record.get(3));
		tradeMe.setShipmentMethod(record.get(4));
		tradeMe.setShippedWith(record.get(5));
		tradeMe.setTrackingReference(record.get(6));
		tradeMe.setTrackingReferenceRD(record.get(7));
		tradeMe.setCustomer(record.get(8));
		tradeMe.setCustomerReference(record.get(9));
		tradeMe.setCustomerEmail(record.get(10));
		tradeMe.setPhoneNumber(record.get(11));
		tradeMe.setAddress1(record.get(12));
		tradeMe.setAddress2(record.get(13));
		tradeMe.setAddress3(record.get(14));
		tradeMe.setTownCity(record.get(15));
		tradeMe.setPostCode(record.get(16));
		tradeMe.setCountry(record.get(17));
		tradeMe.setRegionState(record.get(18));
		tradeMe.setProductCode(record.get(19));
		tradeMe.setProductAlternateCode(record.get(20));
		tradeMe.setProductName(record.get(21));
		tradeMe.setUOM(record.get(22));
		tradeMe.setProductPublicNotes(record.get(23));
		tradeMe.setProductPrivateNotes(record.get(24));
		double d = Double.parseDouble(record.get(25));
		tradeMe.setQtyRequested((int) d);
		tradeMe.setQtyPacked(record.get(26));
		tradeMe.setQtyBackorder(record.get(27));
		tradeMe.setUnitPriceIncTax(record.get(28));
		tradeMe.setLineTotalIncTax(record.get(29));
		tradeMe.setNotes(record.get(30));
		tradeMe.setOrderNotesPublic(record.get(31));
		tradeMe.setActualShippingDateShipment(record.get(32));
		tradeMe.setShippedBy(record.get(33));

		return tradeMe;
	}

	private boolean isFileEmpty(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			logger.warn(file.getOriginalFilename() + " is empty.");
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
			return true;
		}
		return false;
	}
}
