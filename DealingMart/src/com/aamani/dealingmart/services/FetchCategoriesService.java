package com.aamani.dealingmart.services;

import java.util.Iterator;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;

import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.helper.CategoryHelper;
import com.aamani.dealingmart.helper.SubCategoryHelper;
import com.aamani.dealingmart.helper.WebServiceHelper;

/**
 * Intent service for fetching categories and subcategory from server
 * 
 * @author Vasu
 * 
 */
public class FetchCategoriesService extends IntentService {

	private static final String TAG = "FetchCategoriesService";

	public FetchCategoriesService() {
		super("FetchCategoriesService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		List<CategoryEntity> categories = WebServiceHelper
				.getCategory(getApplicationContext());

		Iterator<CategoryEntity> categoryIterator = categories.iterator();

		while (categoryIterator.hasNext()) {
			CategoryEntity category = categoryIterator.next();
			List<SubCategoryEntity> subCategories = WebServiceHelper
					.getSubCategory(getApplicationContext(), category);
			if (subCategories == null || subCategories.isEmpty()) {
				categoryIterator.remove();
			} else {
				long categoryId = CategoryHelper.insertCategory(
						getApplicationContext(), category, null);
				SubCategoryHelper.insertSubCategories(getApplicationContext(),
						subCategories, categoryId);
			}
		}

	}

}
