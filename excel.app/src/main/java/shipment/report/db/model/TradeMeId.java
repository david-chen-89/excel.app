package shipment.report.db.model;

import java.io.Serializable;

public class TradeMeId implements Serializable {
	private static final long serialVersionUID = -5599297373779983662L;

	private String shipmentNumber;
	private String productCode;

	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((shipmentNumber == null) ? 0 : shipmentNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TradeMeId other = (TradeMeId) obj;
		if (productCode == null) {
			if (other.productCode != null) return false;
		} else if (!productCode.equals(other.productCode)) return false;
		if (shipmentNumber == null) {
			if (other.shipmentNumber != null) return false;
		} else if (!shipmentNumber.equals(other.shipmentNumber)) return false;
		return true;
	}

}
