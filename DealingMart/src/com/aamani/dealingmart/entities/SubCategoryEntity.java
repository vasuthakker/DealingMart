package com.aamani.dealingmart.entities;

/**
 * Subcategory entity
 * 
 * @author Vasu
 * 
 */
public class SubCategoryEntity {

	private int id;
	private long categoryId;
	private String subCategory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return subCategory;
	}

}
