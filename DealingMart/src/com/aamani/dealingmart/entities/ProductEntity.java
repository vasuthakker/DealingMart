package com.aamani.dealingmart.entities;

import java.io.Serializable;

import com.aamani.dealingmart.common.DealingMartConstatns;

/**
 * Product entity
 * @author Vasu
 *
 */
@SuppressWarnings("serial")
public class ProductEntity implements Serializable {

	private int id;
	private String productId;
	private String productName;
	private String productImage;
	private float productPrice;
	private int productCount;
	private int productRating;
	private String brandName;
	private float productDiscount;
	private int isPushedInCart=DealingMartConstatns.STATUS_INACTIVE;
	private int attributeId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getProductRating() {
		return productRating;
	}

	public void setProductRating(int productRating) {
		this.productRating = productRating;
	}

	@Override
	public String toString() {
		return productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public float getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(float productDiscount) {
		this.productDiscount = productDiscount;
	}

	public float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

	public int getIsPushedInCart() {
		return isPushedInCart;
	}

	public void setIsPushedInCart(int isPushedInCart) {
		this.isPushedInCart = isPushedInCart;
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}



}
