package com.aamani.dealingmart.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for containing list of table creation queries
 * 
 * @author Vasu
 * 
 */
public class TableCreationQueries {
	
	public static List<String> getCreateTableQueries() {
		List<String> tableCreationQueries = new ArrayList<String>();
		
		tableCreationQueries
				.add("CREATE TABLE IF NOT EXISTS DEALINGMART_CART(_ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_ID TEXT NOT NULL,PRODUCT_NAME TEXT NOT NULL,PRODUCT_IMAGE NOT NULL,PRODUCT_PRICE INTEGER NOT NULL,IS_PUSHED INTEGER NOT NULL,ATTRIBUTE_ID INTEGER)");
		
		tableCreationQueries
				.add("CREATE TABLE IF NOT EXISTS CATEGORY(_ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT NOT NULL)");
		
		tableCreationQueries
				.add("CREATE TABLE SUB_CATEGORY(_ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY_ID INTEGER NOT NULL,SUB_CATEGORY TEXT NOT NULL)");
		
		return tableCreationQueries;
	}
	
}
