package shipment.report;

public final class Constants {
	public static String APP_NAME = "Shipment Report";

	public static class TAB1 {
		public static String Shipment_Number = "Shipment Number";
		public static String Status = "Status";
		public static String Warehouse_Code = "Warehouse Code";
		public static String Requested_Shipping_Date_SO = "Requested Shipping Date (SO)";
		public static String Shipment_Method = "Shipment Method";
		public static String Shipped_With = "Shipped With";
		public static String Tracking_Reference = "Tracking Reference";
		public static String Tracking_Reference_RD = "Tracking Reference (RD)";
		public static String Customer = "Customer";
		public static String Customer_Reference = "Customer Reference";
		public static String Customer_Email = "Customer Email";
		public static String Phone_Number = "Phone Number";
		public static String Address1 = "Address 1";
		public static String Address2 = "Address 2";
		public static String Address3 = "Address 3";
		public static String Town_City = "Town/City";
		public static String Post_Code = "Post Code";
		public static String Country = "Country";
		public static String Region_State = "Region/State";
		public static String Product_Code = "Product Code";
		public static String Product_Alternate_Code = "Product Alternate Code";
		public static String Product_Name = "Product Name";
		public static String UOM = "UOM";
		public static String Product_Public_Notes = "Product Public Notes";
		public static String Product_Private_Notes = "Product Private Notes";
		public static String Qty_Requested = "Qty Requested";
		public static String Qty_Packed = "Qty Packed";
		public static String Qty_Backorder = "Qty Backorder";
		public static String Unit_Price_Inc_Tax = "Unit Price Inc Tax";
		public static String Line_Total_Inc_Tax = "Line Total Inc Tax";
		public static String Notes = "Notes";
		public static String Order_Notes_Public = "Order Notes (Public)";
		public static String Actual_Shipping_Date_Shipment = "Actual Shipping Date (Shipment)";
		public static String Shipped_By = "Shipped By";
	}

	public static class FASTWAY_BAGS_GD {
		public static String SKU = "SKU";
		public static String Barcode = "Barcode";
		public static String Location = "Location";
		public static String Bag = "Bag";
		public static String Description = "Description";
	}

	public static class TAB3 {
		public static String Reference = "Reference";
		public static String Contact_Name = "Contact Name";
		public static String Company_Name_Required = "Company Name";
		public static String Address1_Required = "Address 1";
		public static String Address2 = "Address 2";
		public static String Suburb_Required = "Suburb";
		public static String City = "City";
		public static String Post_Code_required = "Post Code";
		public static String Email_Address = "Email Address";
		public static String Phone_Number = "Phone";
		public static String Special1 = "Special 1";
		public static String Special2 = "Special 2";
		public static String Special3 = "Special 3";
		public static String Packaging = "Packaging";
		public static String Weight = "Weight";
		public static String Count_Quantity = "Quantity";
		public static String Packaging_types = "Packaging types";
		public static String SKU = "SKU";
		public static String Qty_Requested = "Qty Requested";
		public static String Unit_Price_Inc_Tax = "Unit Price Inc Tax";

	}

	public static class IN_STOCK {
		public static String Barcode = "Barcode";
		public static String Quantity = "Quantity";
		public static String Date = "Date";
		public static String Note = "Note";
	}

	public final static String FastwayQuery = "SELECT t.CUSTOMER_REFERENCE AS REFERENCE, " + "t.ORDER_NOTES_PUBLIC AS CONTACT_NAME, "
			+ "t. CUSTOMER as COMPANY_NAME, " + "t.ADDRESS1, " + "t.ADDRESS2, " + "t.ADDRESS3 as SUBURB, " + "t.TOWN_CITY as CITY, " + "t.POST_CODE, "
			+ "t.CUSTOMER_EMAIL as EMAIL_ADDRESS, " + "t.PHONE_NUMBER as PHONE, "
			+ "CASE WHEN f.barcode IS NOT NULL THEN Concat(f.location, '_____', f.barcode) ELSE '' END AS SPECIAL1, " + "'' AS SPECIAL2, "
			+ "t.PRODUCT_NAME as SPECIAL3, " + "f.BAG as PACKAGING, " + "'1' AS WEIGHT, " + "'1' AS QUANTITY, " + "'' AS PACKAGING_TYPES, "
			+ " t.PRODUCT_ALTERNATE_CODE, " + " t.qty_requested, " + " t.unit_price_inc_tax " + "FROM TRADE_ME t LEFT JOIN FASTWAY_BAGS f "
			+ "ON t.PRODUCT_ALTERNATE_CODE = f.SKU;";

	private Constants() {
	}
}
