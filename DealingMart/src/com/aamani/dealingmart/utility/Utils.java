package com.aamani.dealingmart.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.util.Log;
import android.util.Patterns;

import com.aamani.dealingmart.common.DealingMartConstatns;

/**
 * Utility class
 * 
 * @author Vasu
 * 
 */
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

	/**
	 * Get Unique id for device
	 * 
	 * @param context
	 * @return
	 */
	public static String getUniquId(Context context) {
		// Getting a unique device id
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * Method for getting google account
	 * 
	 * @param context
	 * @return
	 */
	public static String getGoogleAccount(Context context) {
		String googleAccount = null;
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(context).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()
					&& account.type.equals("com.google")) {
				googleAccount = account.name;
			}
		}
		return googleAccount;
	}

}
