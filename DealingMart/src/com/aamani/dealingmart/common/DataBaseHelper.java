package com.aamani.dealingmart.common;

import java.util.List;

import com.aamani.dealingmart.helper.TableCreationQueries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper class for database
 * @author Vasu
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DataBaseHelper";

	// The Android's default system path of your application database.
	@SuppressLint("SdCardPath")
	private static String DB_NAME = DealingMartConstatns.DATABASE_NAME;

	private SQLiteDatabase myDataBase;

	private static final int DATABASE_VERSION = DealingMartConstatns.DATABASE_VERSION;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, DATABASE_VERSION);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		List<String> tableCreationQueries = TableCreationQueries
				.getCreateTableQueries();
		for (String query : tableCreationQueries) {
			db.execSQL(query);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// truncateTables(db);
	}

	/**
	 * Method to read values from table
	 * 
	 * @param tableName
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return cursor
	 */
	public Cursor readValues(SQLiteDatabase db, String tableName,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = null;

		try {

			cursor = db.query(tableName, columns, selection, selectionArgs,
					groupBy, having, orderBy);
		} catch (SQLException e) {
			Log.e(TAG, "SQLException ", e);
		}
		return cursor;
	}

	public static void truncateOrderTable(Context context) {
		SQLiteDatabase db = new DataBaseHelper(context).getWritableDatabase();
		db.execSQL("DROP TABLE DEALINGMART_CART");
		List<String> queries = TableCreationQueries.getCreateTableQueries();
		db.execSQL(queries.get(0));

	}

}