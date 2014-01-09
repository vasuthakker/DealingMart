package com.aamani.dealingmart.entities;

/**
 * Product size entity
 * 
 * @author Vasu
 * 
 */

public class ProductAttributeEntity {
	
	private int attributeId;
	private String productId;
	private String attributeTitle;
	private String attributeValue;
	
	public int getAttributeId() {
		return attributeId;
	}
	
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getAttributeTitle() {
		return attributeTitle;
	}
	
	public void setAttributeTitle(String attributeTitle) {
		this.attributeTitle = attributeTitle;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
}
