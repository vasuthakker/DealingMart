package com.aamani.dealingmart.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.aamani.dealingmart.common.DealingMartConstatns;

public class Utils {

	static String subCategory = "allProd";
	static String category = "Bags & Accessories";

	private static final String TAG = "Utils";

	/**
	 * Method for setting strict policy
	 */
	public static void setStrictPolicy() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
	}

	/**
	 * Method for converting dp to pixels
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dpToPx(Context context, int dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return Math.round((float) dp * density);
	}

	/**
	 * Get a bitmap from the source url
	 * 
	 * @param src
	 * @return
	 */
	public static Bitmap getBitmapFromURL(String src) {
		Bitmap bmp = null;
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			bmp = BitmapFactory.decodeStream(input);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}
		return bmp;
	}

	/**
	 * Set preference
	 */
	public static void setPreference(Context context, String key, String value) {
		SharedPreferences preference = context.getSharedPreferences(
				DealingMartConstatns.SHARED_PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * Set preference
	 */
	public static String getPreference(Context context, String key) {
		SharedPreferences preference = context.getSharedPreferences(
				DealingMartConstatns.SHARED_PREFERENCE, Context.MODE_PRIVATE);
		return preference.getString(key, null);
	}

}
