package org.master.report;

import javax.persistence.Entity;

import org.master.model.Person;
import org.master.model.Product;

public class OrderReport {
	private String personName;
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
