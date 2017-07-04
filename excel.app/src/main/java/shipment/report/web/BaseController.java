package shipment.report.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shipment.report.Constants;
import shipment.report.db.DbService;
import shipment.report.db.model.Bag;
import shipment.report.db.model.TradeMe;

@Controller
public class BaseController {
	private static Log logger = LogFactory.getLog(BaseController.class);

	@Autowired
	DbService dbService;

	@GetMapping({ "/", "tab3" })
	public String tab3(Map<String, Object> model) {
		model.put("active", "tab3");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(Constants.TAB3.Reference);
		columns.add(Constants.TAB3.Contact_Name);
		columns.add(Constants.TAB3.Company_Name_Required);
		columns.add(Constants.TAB3.Address1_Required);
		columns.add(Constants.TAB3.Address2);
		columns.add(Constants.TAB3.Suburb_Required);
		columns.add(Constants.TAB3.City);
		columns.add(Constants.TAB3.Post_Code_required);
		columns.add(Constants.TAB3.Email_Address);
		columns.add(Constants.TAB3.Phone_Number);
		columns.add(Constants.TAB3.Special1);
		columns.add(Constants.TAB3.Special2);
		columns.add(Constants.TAB3.Special3);
		columns.add(Constants.TAB3.Packaging);
		columns.add(Constants.TAB3.Weight);
		columns.add(Constants.TAB3.Count_Quantity);
		columns.add(Constants.TAB3.Packaging_types);
		model.put("columns", columns);

		try {
			ArrayList<String[]> data = new ArrayList<String[]>();
			List<Object[]> fastWays = dbService.getAllFastWay();
			for (Object[] fastWay : fastWays) {
				String[] values = new String[fastWay.length];
				for (int i = 0; i < fastWay.length; i++) {
					values[i] = (String) fastWay[i];
				}
				data.add(values);
				model.put("data", data);
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return "index";
	}

	@GetMapping("tab1")
	public String tab1(Map<String, Object> model) {
		model.put("active", "tab1");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(Constants.TAB1.Shipment_Number);
		columns.add(Constants.TAB1.Status);
		columns.add(Constants.TAB1.Warehouse_Code);
		columns.add(Constants.TAB1.Requested_Shipping_Date_SO);
		columns.add(Constants.TAB1.Shipment_Method);
		columns.add(Constants.TAB1.Shipped_With);
		columns.add(Constants.TAB1.Tracking_Reference);
		columns.add(Constants.TAB1.Tracking_Reference_RD);
		columns.add(Constants.TAB1.Customer);
		columns.add(Constants.TAB1.Customer_Reference);
		columns.add(Constants.TAB1.Customer_Email);
		columns.add(Constants.TAB1.Phone_Number);
		columns.add(Constants.TAB1.Address1);
		columns.add(Constants.TAB1.Address2);
		columns.add(Constants.TAB1.Address3);
		columns.add(Constants.TAB1.Town_City);
		columns.add(Constants.TAB1.Post_Code);
		columns.add(Constants.TAB1.Country);
		columns.add(Constants.TAB1.Region_State);
		columns.add(Constants.TAB1.Product_Code);
		columns.add(Constants.TAB1.Product_Alternate_Code);
		columns.add(Constants.TAB1.Product_Name);
		columns.add(Constants.TAB1.UOM);
		columns.add(Constants.TAB1.Product_Public_Notes);
		columns.add(Constants.TAB1.Product_Private_Notes);
		columns.add(Constants.TAB1.Qty_Requested);
		columns.add(Constants.TAB1.Qty_Packed);
		columns.add(Constants.TAB1.Qty_Backorder);
		columns.add(Constants.TAB1.Unit_Price_Inc_Tax);
		columns.add(Constants.TAB1.Line_Total_Inc_Tax);
		columns.add(Constants.TAB1.Notes);
		columns.add(Constants.TAB1.Order_Notes_Public);
		columns.add(Constants.TAB1.Actual_Shipping_Date_Shipment);
		columns.add(Constants.TAB1.Shipped_By);
		model.put("columns", columns);

		try {
			ArrayList<String[]> data = new ArrayList<String[]>();
			List<TradeMe> inventories = dbService.getAllTradeMe();
			for (TradeMe inventory : inventories) {
				data.add(new String[] { inventory.getShipmentNumber(), inventory.getStatus(), inventory.getWarehouseCode(),
						inventory.getRequestedShippingDateSO(), inventory.getShipmentMethod(), inventory.getShippedWith(), inventory.getTrackingReference(),
						inventory.getTrackingReferenceRD(), inventory.getCustomer(), inventory.getCustomerReference(), inventory.getCustomerEmail(),
						inventory.getPhoneNumber(), inventory.getAddress1(), inventory.getAddress2(), inventory.getAddress3(), inventory.getTownCity(),
						inventory.getPostCode(), inventory.getCountry(), inventory.getRegionState(), inventory.getProductCode(),
						inventory.getProductAlternateCode(), inventory.getProductName(), inventory.getUOM(), inventory.getProductPublicNotes(),
						inventory.getProductPrivateNotes(), inventory.getQtyRequested(), inventory.getQtyPacked(), inventory.getQtyBackorder(),
						inventory.getUnitPriceIncTax(), inventory.getLineTotalIncTax(), inventory.getNotes(), inventory.getOrderNotesPublic(),
						inventory.getActualShippingDateShipment(), inventory.getShippedBy() });
			}
			model.put("data", data);
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "index";
	}

	@GetMapping("tab2")
	public String tab2(Map<String, Object> model) {
		model.put("active", "tab2");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(Constants.FASTWAY_BAGS_GD.SKU);
		columns.add(Constants.FASTWAY_BAGS_GD.Barcode);
		columns.add(Constants.FASTWAY_BAGS_GD.Location);
		columns.add(Constants.FASTWAY_BAGS_GD.Bag);
		columns.add(Constants.FASTWAY_BAGS_GD.Description);
		model.put("columns", columns);

		try {
			ArrayList<String[]> data = new ArrayList<String[]>();
			List<Bag> fastways = dbService.getAllBag();
			for (Bag fastway : fastways) {
				data.add(new String[] { fastway.getSku(), fastway.getBarcode(), fastway.getLocation(), fastway.getBag(), fastway.getDescription() });
			}
			model.put("data", data);
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return "index";
	}

	@GetMapping("admin")
	public String admin(Map<String, Object> model) {
		model.put("active", "admin");
		return "admin";
	}
}
