package excel.app.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import excel.app.Constants;
import excel.app.db.DbService;
import excel.app.db.model.Fastway;

@Controller
public class BaseController {

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

		ArrayList<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "G147330617-1", "", "Chantelle Ensor ", "55 Landscape Rd", "", "Papatoetoe", "Auckland", "2025", "bajan.kiwis@gmail.com", "",
				"LA00192_____A00192", "1__________6.89", "TOSHIBA 19V4.74A 5.5*2.5  Laptop Charger", "9", "1", "1", "" });
		data.add(new String[] { "G148740112-1", "black'", "Lisa Strickett ", "6 Cypress Place", "", "Owhata", "Rotorua", "3010", "blkhrt@clear.net.nz", "",
				"LE00014_____E00014", "2__________5.83", "XBOX 360 battery + small pin Cable  white_", "9", "1", "1", "" });
		data.add(new String[] { "G148949418", "", "Connor Hanley ", "30 Ben Lomond Crescent", "", "Pakuranga", "Auckland", "2010", "iconx23x@gmail.com", "",
				"LH00072_____H00072", "1__________6.89", "Ethernet Switch 5 Ports + adapter", "6", "1", "1", "1" });
		data.add(new String[] { "G149092553", "", "Dre Edwards ", "25A Rautawhiri Street", "", "Helensville", "Helensville", "800", "dreedwards7@gmail.com",
				"", "LM00011_____M00011", "1__________7.89", "Puppy Pet Training Potty Grass Tray Toilet", "1", "1", "1", "1" });
		model.put("data", data);
		return "index";
	}

	@GetMapping("tab1")
	public String tab1(Map<String, Object> model) {
		model.put("active", "tab1");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(Constants.FASTWAY_BAGS_GD.SKU);
		columns.add(Constants.FASTWAY_BAGS_GD.Barcode);
		columns.add(Constants.FASTWAY_BAGS_GD.Location);
		columns.add(Constants.FASTWAY_BAGS_GD.Bag);
		columns.add(Constants.FASTWAY_BAGS_GD.Description);
		model.put("columns", columns);

		ArrayList<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "COMPUTER138nzsale", "A00001", "	LA00001", "9", "DP to VGA 1.8M cable" });
		data.add(new String[] { "computer118", "A00002", "LA00002", "9", "D/P MALE TO DVI ADPT 24+5" });
		data.add(new String[] { "computer120", "A00003", "LA00003", "9", "D/P MALE TO VGA ADPT" });
		data.add(new String[] { "computer192", "A00004", "LA00004", "9", "HDMI FEMALE TO DVI MALE 24+5 ADPT" });
		model.put("data", data);
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

		ArrayList<String[]> data = new ArrayList<String[]>();
		List<Fastway> fastways = dbService.getAllFastway();
		for (Fastway fastway : fastways) {
			data.add(new String[] { fastway.getSku(), fastway.getBarcode(), fastway.getLocation(), fastway.getBag(), fastway.getDescription() });
		}
		model.put("data", data);
		return "index";
	}

	@GetMapping("admin")
	public String admin(Map<String, Object> model) {
		model.put("active", "admin");
		return "admin";
	}
}
