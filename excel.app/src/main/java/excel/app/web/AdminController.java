package excel.app.web;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import excel.app.db.DbService;
import excel.app.db.model.Fastway;
import excel.app.db.model.Inventory;

@Controller
public class AdminController {
	private static Log logger = LogFactory.getLog(AdminController.class);

	private static final String REDIRECT_ADMIN = "redirect:/admin";

	@Autowired
	DbService dbService;

	@PostMapping("/load/tab1")
	public String tab1Upload(@RequestParam("file") MultipartFile file, @RequestParam("clear") boolean clear, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
			return REDIRECT_ADMIN;
		}

		Reader in;
		Iterable<CSVRecord> records;
		try {
			in = new InputStreamReader(file.getInputStream());
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("failed to parse csv file: " + file.getName(), e);
			return REDIRECT_ADMIN;
		}
		ArrayList<Inventory> inventories = new ArrayList<>();
		for (CSVRecord record : records) {
			Inventory data = parseInventory(record);
			inventories.add(data);
		}
		try {
			if (clear) {
				dbService.reloadInventory(inventories);
			} else {
				dbService.addOrUpdateInventories(inventories);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("failed to update H2: " + file.getName(), e);
			return REDIRECT_ADMIN;
		}
		redirectAttributes.addFlashAttribute("message", "Table updated!");
		return REDIRECT_ADMIN;
	}

	@PostMapping("/load/tab2")
	public String tab2Upload(@RequestParam("file") MultipartFile file, @RequestParam("clear") boolean clear, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
			return REDIRECT_ADMIN;
		}

		Reader in;
		Iterable<CSVRecord> records;
		try {
			in = new InputStreamReader(file.getInputStream());
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("failed to parse csv file: " + file.getName(), e);
			return REDIRECT_ADMIN;
		}
		ArrayList<Fastway> fastways = new ArrayList<>();
		for (CSVRecord record : records) {
			Fastway data = parseFastway(record);
			fastways.add(data);
		}
		try {
			if (clear) {
				dbService.reloadFastway(fastways);
			} else {
				dbService.addOrUpdateFastways(fastways);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			logger.warn("failed to update H2: " + file.getName(), e);
			return REDIRECT_ADMIN;
		}
		redirectAttributes.addFlashAttribute("message", "Table updated!");
		return REDIRECT_ADMIN;
	}

	@PostMapping("/remove/tab1")
	public String tab1Remove(@RequestParam("barcode") String barcode, RedirectAttributes redirectAttributes) {
		dbService.removeFastway(barcode);
		redirectAttributes.addFlashAttribute("message", "Record removed!");
		return REDIRECT_ADMIN;
	}

	@PostMapping("/remove/tab2")
	public String tab2Remove(@RequestParam("shipmentNum") String shipmentNum, RedirectAttributes redirectAttributes) {
		dbService.removeInventory(shipmentNum);
		redirectAttributes.addFlashAttribute("message", "Record removed!");
		return REDIRECT_ADMIN;
	}

	private Fastway parseFastway(CSVRecord record) {
		Fastway fastway = new Fastway();
		fastway.setSku(record.get(0));
		fastway.setBarcode(record.get(1));
		fastway.setLocation(record.get(2));
		fastway.setBag(record.get(3));
		fastway.setDescription(record.get(4));
		return fastway;
	}

	private Inventory parseInventory(CSVRecord record) {
		Inventory inventory = new Inventory();
		//		fastway.setSku(record.get(0));
		inventory.setShipmentNumber(record.get(0));
		inventory.setStatus(record.get(0));
		inventory.setWarehouseCode(record.get(0));
		inventory.setRequestedShippingDateSO(record.get(0));
		inventory.setShipmentMethod(record.get(0));
		inventory.setShippedWith(record.get(0));
		inventory.setTrackingReference(record.get(0));
		inventory.setTrackingReferenceRD(record.get(0));
		inventory.setCustomer(record.get(0));
		inventory.setCustomerReference(record.get(0));
		inventory.setCustomerEmail(record.get(0));
		inventory.setPhoneNumber(record.get(0));
		inventory.setAddress1(record.get(0));
		inventory.setAddress2(record.get(0));
		inventory.setAddress3(record.get(0));
		inventory.setTownCity(record.get(0));
		inventory.setPostCode(record.get(0));
		inventory.setCountry(record.get(0));
		inventory.setRegionState(record.get(0));
		inventory.setProductCode(record.get(0));
		inventory.setProductAlternateCode(record.get(0));
		inventory.setProductName(record.get(0));
		inventory.setUOM(record.get(0));
		inventory.setProductPublicNotes(record.get(0));
		inventory.setProductPrivateNotes(record.get(0));
		inventory.setQtyRequested(record.get(0));
		inventory.setQtyPacked(record.get(0));
		inventory.setQtyBackorder(record.get(0));
		inventory.setUnitPriceIncTax(record.get(0));
		inventory.setLineTotalIncTax(record.get(0));
		inventory.setNotes(record.get(0));
		inventory.setOrderNotesPublic(record.get(0));
		inventory.setActualShippingDateShipment(record.get(0));
		inventory.setShippedBy(record.get(0));

		return inventory;
	}
}
