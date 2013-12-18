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
import com.aamani.dealingmart.entities.CategoryEntity;

/**
 * Helper class for category
 * 
 * @author Vasu
 * 
 */
public class CategoryHelper {

	private static final String TABLE_NAME = "CATEGORY";

	private static final String KEY_ID = "_ID";
	private static final String KEY_CATEGORY = "CATEGORY";

	private static final String TAG = "CategoryHelper";

	/**
	 * Method for inserting category
	 * 
	 * @param context
	 * @param category
	 * @return
	 */
	public static long insertCategory(Context context, CategoryEntity category,
			SQLiteDatabase database) {
		long categoryId = 0;
		SQLiteDatabase db = null;
		if (database == null) {
			DataBaseHelper dbHelper = new DataBaseHelper(context);
			db = dbHelper.getWritableDatabase();
		} else {
			db = database;
		}
		try {
			categoryId = db.insert(TABLE_NAME, null,
					getContentValueForEntity(category));
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} finally {
			if (database == null && db != null) {
				db.close();
			}
		}
		return categoryId;
	}

	/**
	 * Method for inserting category
	 * 
	 * @param context
	 * @param category
	 */
	public static void insertCategories(Context context,
			List<CategoryEntity> categories) {

		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			for (CategoryEntity category : categories) {
				insertCategory(context, category, db);
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} finally {
			db.close();
		}
	}

	// get content values from entity
	private static ContentValues getContentValueForEntity(
			CategoryEntity category) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, category.getCategory());
		return cv;
	}

	/**
	 * Method for fetching categories
	 * 
	 * @param context
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public static List<CategoryEntity> fetchCategories(Context context,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {

		List<CategoryEntity> categories = new ArrayList<CategoryEntity>();

		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null,
					selection, selectionArgs, groupBy, having, orderBy);

			while (cursor.moveToNext()) {
				categories.add(getEntityFromCursror(cursor));
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} catch (CursorIndexOutOfBoundsException e) {
			Log.e(TAG, "CursorIndexOutOfBoundsException", e);
		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException", e);
		} finally {
			db.close();
		}
		return categories;
	}

	/**
	 * Method for fetching category
	 * 
	 * @param context
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public static CategoryEntity fetchCategory(Context context, int categoryId) {

		CategoryEntity category = new CategoryEntity();

		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null, KEY_ID
					+ "=?", new String[] { String.valueOf(categoryId) }, null,
					null, null);

			if (cursor.moveToNext()) {
				category = getEntityFromCursror(cursor);
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} catch (CursorIndexOutOfBoundsException e) {
			Log.e(TAG, "CursorIndexOutOfBoundsException", e);
		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException", e);
		} finally {
			db.close();
		}
		return category;
	}

	// get entity from cursor
	private static CategoryEntity getEntityFromCursror(Cursor cursor) {
		CategoryEntity category = new CategoryEntity();
		category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		category.setCategory(cursor.getString(cursor
				.getColumnIndex(KEY_CATEGORY)));
		return category;
	}
}
