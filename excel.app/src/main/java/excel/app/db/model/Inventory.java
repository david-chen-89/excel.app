package excel.app.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Inventory")
public class Inventory {
	@Id
	private String shipmentNumber;
	private String status;
	private String warehouseCode;
	private String requestedShippingDateSO;
	private String shipmentMethod;
	private String shippedWith;
	private String trackingReference;
	private String trackingReferenceRD;
	private String customer;
	private String customerReference;
	private String customerEmail;
	private String phoneNumber;
	private String address1;
	private String address2;
	private String address3;
	private String townCity;
	private String postCode;
	private String country;
	private String regionState;
	private String productCode;
	private String productAlternateCode;
	private String productName;
	private String UOM;
	private String productPublicNotes;
	private String productPrivateNotes;
	private String qtyRequested;
	private String qtyPacked;
	private String qtyBackorder;
	private String unitPriceIncTax;
	private String lineTotalIncTax;
	private String notes;
	private String orderNotesPublic;
	private String actualShippingDateShipment;
	private String shippedBy;

	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getRequestedShippingDateSO() {
		return requestedShippingDateSO;
	}

	public void setRequestedShippingDateSO(String requestedShippingDateSO) {
		this.requestedShippingDateSO = requestedShippingDateSO;
	}

	public String getShipmentMethod() {
		return shipmentMethod;
	}

	public void setShipmentMethod(String shipmentMethod) {
		this.shipmentMethod = shipmentMethod;
	}

	public String getShippedWith() {
		return shippedWith;
	}

	public void setShippedWith(String shippedWith) {
		this.shippedWith = shippedWith;
	}

	public String getTrackingReference() {
		return trackingReference;
	}

	public void setTrackingReference(String trackingReference) {
		this.trackingReference = trackingReference;
	}

	public String getTrackingReferenceRD() {
		return trackingReferenceRD;
	}

	public void setTrackingReferenceRD(String trackingReferenceRD) {
		this.trackingReferenceRD = trackingReferenceRD;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getTownCity() {
		return townCity;
	}

	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegionState() {
		return regionState;
	}

	public void setRegionState(String regionState) {
		this.regionState = regionState;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductAlternateCode() {
		return productAlternateCode;
	}

	public void setProductAlternateCode(String productAlternateCode) {
		this.productAlternateCode = productAlternateCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public String getProductPublicNotes() {
		return productPublicNotes;
	}

	public void setProductPublicNotes(String productPublicNotes) {
		this.productPublicNotes = productPublicNotes;
	}

	public String getProductPrivateNotes() {
		return productPrivateNotes;
	}

	public void setProductPrivateNotes(String productPrivateNotes) {
		this.productPrivateNotes = productPrivateNotes;
	}

	public String getQtyRequested() {
		return qtyRequested;
	}

	public void setQtyRequested(String qtyRequested) {
		this.qtyRequested = qtyRequested;
	}

	public String getQtyPacked() {
		return qtyPacked;
	}

	public void setQtyPacked(String qtyPacked) {
		this.qtyPacked = qtyPacked;
	}

	public String getQtyBackorder() {
		return qtyBackorder;
	}

	public void setQtyBackorder(String qtyBackorder) {
		this.qtyBackorder = qtyBackorder;
	}

	public String getUnitPriceIncTax() {
		return unitPriceIncTax;
	}

	public void setUnitPriceIncTax(String unitPriceIncTax) {
		this.unitPriceIncTax = unitPriceIncTax;
	}

	public String getLineTotalIncTax() {
		return lineTotalIncTax;
	}

	public void setLineTotalIncTax(String lineTotalIncTax) {
		this.lineTotalIncTax = lineTotalIncTax;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrderNotesPublic() {
		return orderNotesPublic;
	}

	public void setOrderNotesPublic(String orderNotesPublic) {
		this.orderNotesPublic = orderNotesPublic;
	}

	public String getActualShippingDateShipment() {
		return actualShippingDateShipment;
	}

	public void setActualShippingDateShipment(String actualShippingDateShipment) {
		this.actualShippingDateShipment = actualShippingDateShipment;
	}

	public String getShippedBy() {
		return shippedBy;
	}

	public void setShippedBy(String shippedBy) {
		this.shippedBy = shippedBy;
	}
}
