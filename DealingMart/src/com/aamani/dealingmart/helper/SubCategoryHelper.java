package com.aamani.dealingmart.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.aamani.dealingmart.common.DataBaseHelper;
import com.aamani.dealingmart.entities.SubCategoryEntity;

/**
 * Helper class for sub category
 * 
 * @author Vasu
 * 
 */
public class SubCategoryHelper {

	private static final String TABLE_NAME = "SUB_CATEGORY";

	public static final String KEY_ID = "_ID";
	public static final String KEY_CATEGORY_ID = "CATEGORY_ID";
	public static final String KEY_SUB_CATEGORY = "SUB_CATEGORY";

	private static final String TAG = "SubCategoryHelper";

	/**
	 * Method for inserting subcategory
	 * 
	 * @param context
	 * @param subCategory
	 * @param database
	 */
	public static void insertSubCategory(Context context,
			SubCategoryEntity subCategory, SQLiteDatabase database) {
		SQLiteDatabase db = null;
		if (database == null) {
			DataBaseHelper dbHelper = new DataBaseHelper(context);
			db = dbHelper.getWritableDatabase();
		} else {
			db = database;
		}
		try {
			db.insert(TABLE_NAME, null, getContentValuesFromEntity(subCategory));
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} finally {
			if (database == null && db != null) {
				db.close();
			}
		}

	}

	// method for getting content values from an entity
	private static ContentValues getContentValuesFromEntity(
			SubCategoryEntity subCategory) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY_ID, subCategory.getCategoryId());
		cv.put(KEY_SUB_CATEGORY, subCategory.getSubCategory());
		return cv;
	}

	/**
	 * Method for inserting subcategories
	 * 
	 * @param context
	 * @param subCategories
	 * @param categoryId
	 */
	public static void insertSubCategories(Context context,
			List<SubCategoryEntity> subCategories, long categoryId) {
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			for (SubCategoryEntity subCategory : subCategories) {
				subCategory.setCategoryId(categoryId);
				insertSubCategory(context, subCategory, db);
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} finally {
			db.close();
		}
	}

	/**
	 * Method for selecting a value
	 * 
	 * @param context
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public static List<SubCategoryEntity> fetchSubCategories(Context context,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		List<SubCategoryEntity> subCategories = new ArrayList<SubCategoryEntity>();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null,
					selection, selectionArgs, groupBy, having, orderBy);

			while (cursor.moveToNext()) {
				subCategories.add(getEntityFromCursor(cursor));
			}
			cursor.close();
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} catch (CursorIndexOutOfBoundsException e) {
			Log.e(TAG, "CursorIndexOutOfBoundsException", e);
		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException", e);
		} finally {
			db.close();
		}
		return subCategories;
	}

	// get entity from a cursor value
	private static SubCategoryEntity getEntityFromCursor(Cursor cursor) {
		SubCategoryEntity subCategory = new SubCategoryEntity();
		subCategory.setCategoryId(cursor.getInt(cursor
				.getColumnIndex(KEY_CATEGORY_ID)));
		subCategory.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		subCategory.setSubCategory(cursor.getString(cursor
				.getColumnIndex(KEY_SUB_CATEGORY)));
		return subCategory;
	}
}
