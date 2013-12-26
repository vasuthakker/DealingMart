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
import com.aamani.dealingmart.entities.ProductEntity;

/**
 * Helper class for cart
 * 
 * @author Vasu
 * 
 */
public class CartHelper {

	private static final String TABLE_NAME = "DEALINGMART_CART";

	public static final String KEY_ID = "_ID";
	public static final String KEY_PRODUCT_ID = "PRODUCT_ID";
	public static final String KEY_PRODUCT_NAME = "PRODUCT_NAME";
	public static final String KEY_PRODUCT_IMAGE = "PRODUCT_IMAGE";
	public static final String KEY_PRODUCT_PRICE = "PRODUCT_PRICE";
	public static final String KEY_PRODUCT_COUNT = "PRODUCT_COUNT";

	private static final String TAG = "CartHelper";

	private static final String SELECT_QUERY_WITH_COUNT = "SELECT *,COUNT(*)  AS PRODUCT_COUNT FROM "
			+ TABLE_NAME + " GROUP BY " + KEY_PRODUCT_ID;

	/**
	 * Method to insert products into cart
	 */
	public static void insertPorductToCart(Context context,
			ProductEntity product) {
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.insert(TABLE_NAME, null, getContentValueFromEntity(product));
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} finally {
			db.close();
		}
	}

	// get content values from product entity
	private static ContentValues getContentValueFromEntity(ProductEntity product) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_PRODUCT_ID, product.getProductId());
		cv.put(KEY_PRODUCT_NAME, product.getProductName());
		cv.put(KEY_PRODUCT_IMAGE, product.getProductImage());
		cv.put(KEY_PRODUCT_PRICE, product.getProductPrice());
		return cv;
	}

	/**
	 * Fetch products
	 * 
	 * @param context
	 * @return
	 */
	public static List<ProductEntity> fetchProducts(Context context) {
		List<ProductEntity> products = new ArrayList<ProductEntity>();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			Cursor cursor = db.rawQuery(SELECT_QUERY_WITH_COUNT, null);
			ProductEntity product = null;
			while (cursor.moveToNext()) {
				product = new ProductEntity();
				product = getEntityFromCursor(cursor);
				product.setProductCount(cursor.getInt(cursor
						.getColumnIndex(KEY_PRODUCT_COUNT)));
				products.add(product);
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
		return products;
	}

	/**
	 * Fetch products
	 * 
	 * @param context
	 * @return
	 */
	public static List<ProductEntity> fetchProducts(Context context,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		List<ProductEntity> products = new ArrayList<ProductEntity>();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null,
					selection, selectionArgs, groupBy, having, orderBy);
			while (cursor.moveToNext()) {
				products.add(getEntityFromCursor(cursor));
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
		return products;
	}

	/**
	 * Fetch products
	 * 
	 * @param context
	 * @return
	 */
	public static int fetchProdcutsCount(Context context) {
		int productCount = 0;
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			Cursor cursor = dbHelper.readValues(db, TABLE_NAME, null, null,
					null, null, null, null);
			if (cursor != null && cursor.moveToNext()) {
				productCount = cursor.getCount();
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
		return productCount;
	}

	// get entity from cursor
	private static ProductEntity getEntityFromCursor(Cursor cursor) {
		ProductEntity product = new ProductEntity();
		product.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		product.setProductId(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_ID)));
		product.setProductName(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_NAME)));
		product.setProductPrice(cursor.getInt(cursor
				.getColumnIndex(KEY_PRODUCT_PRICE)));
		product.setProductImage(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_IMAGE)));
		return product;
	}

	/**
	 * Method for deleting a product
	 * 
	 * @param activity
	 * @param productId
	 */
	public static void deleteProduct(Context context, String productId,
			boolean deleteSingle) {
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			if (!deleteSingle) {
				db.delete(TABLE_NAME, KEY_PRODUCT_ID + "=?",
						new String[] { productId });
			} else {
				int orderId = fetchProducts(context, KEY_PRODUCT_ID + "=?",
						new String[] { productId }, null, null, null).get(0)
						.getId();
				db.delete(TABLE_NAME, KEY_ID + "=?",
						new String[] { String.valueOf(orderId) });
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "SQLiteException", e);
		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException", e);
		} finally {
			db.close();
		}

	}
}
