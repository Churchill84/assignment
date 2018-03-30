package org.master.report;

public class OrderReport {
	private String personName;
	private String personEmail;
	private String personAddress;
	private String productDesc;
	private double quantity;
	private String productImage;

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductImage() {
		return productImage;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getQuantity() {
		return quantity;
	}

}
