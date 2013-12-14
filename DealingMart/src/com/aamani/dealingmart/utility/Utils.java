package com.aamani.dealingmart.utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.ProductEntity;

public class Utils {

	static String subCategory = "allProd";
	static String category = "Bags & Accessories";

	private static final String WEB_SERVICE_URL = "http://temp.dealingmart.com/WebService.asmx";

	private static final String NAMESPACE = "http://dealingmart.org/";

	private static final String KEY_CATEGORY = "category";
	private static final String KEY_SUBCATEGORY = "lsCategory";

	// methods
	private static final String METHOD_GET_PRODUCT_IMAGES = "getImages";
	private static final String METHOD_GET_PRODUCT_IDS = "getProdIds";
	private static final String METHOD_GET_PRODUCT_PRICES = "getProdPrices";
	private static final String METHOD_GET_PRODUCT_NAMES = "getProdsNames";
	private static final String METHOD_GET_PRODUCT_SUBCATEGORY = "getSubCategory";

	// request
	private static final String REQUEST_PRODUCT_IMAGES = "allProd";
	private static final String REQUEST_PRODUCT_IDS = "setProdId";
	private static final String REQUEST_PRODUCT_PRICE = "setProdPrice";
	private static final String REQUEST_PRODUCT_NAME = "setProdName";
	private static final String REQUEST_PRODUCT_SPECIFICATION = "fetchProductSpecification";

	private static final String TAG = "Utils";

	public static List<ProductEntity> getProducts(String category,
			String subCategory) {

		List<ProductEntity> products = new ArrayList<ProductEntity>();
		setStrictPolicy();

		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_IMAGES,
				products);
		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_IDS,
				products);
		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_PRICE,
				products);
		return getProdcutFromWebService(category, subCategory,
				REQUEST_PRODUCT_NAME, products);

	}

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

	// load products from a web service
	private static List<ProductEntity> getProdcutFromWebService(
			String category, String subCategory, String requestCategory,
			List<ProductEntity> productList) {

		StringBuilder SOAP_ACTION = new StringBuilder(NAMESPACE);
		String METHOD_NAME;

		if (requestCategory.equals(REQUEST_PRODUCT_IMAGES)) {
			METHOD_NAME = METHOD_GET_PRODUCT_IMAGES;
		} else if (requestCategory.equals(REQUEST_PRODUCT_IDS)) {
			METHOD_NAME = METHOD_GET_PRODUCT_IDS;
		} else if (requestCategory.equals(REQUEST_PRODUCT_PRICE)) {
			METHOD_NAME = METHOD_GET_PRODUCT_PRICES;
		} else if (requestCategory.equals(REQUEST_PRODUCT_NAME)) {
			METHOD_NAME = METHOD_GET_PRODUCT_NAMES;
		} else {
			METHOD_NAME = METHOD_GET_PRODUCT_SUBCATEGORY;
		}

		SOAP_ACTION.append(METHOD_NAME);

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// adding category and subcategory
		request.addProperty(KEY_CATEGORY, category);
		request.addProperty(KEY_SUBCATEGORY, subCategory);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		SoapObject response = null;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					WEB_SERVICE_URL);
			androidHttpTransport.call(SOAP_ACTION.toString(), envelope);

			androidHttpTransport
					.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			response = (SoapObject) ((SoapObject) envelope.bodyIn)
					.getProperty(0);

			int productCount = response.getPropertyCount();

			if (response != null) {
				// Log.i("urls length..", urlsLen+"");
				if (requestCategory.equals(REQUEST_PRODUCT_IMAGES)) {
					ProductEntity product;
					for (int i = 0; i < productCount; i++) {
						product = new ProductEntity();
						product.setProductImage(response.getProperty(i)
								.toString());
						Log.i(TAG, response.getProperty(i).toString());
						productList.add(product);
					}

				} else if (requestCategory.equals(REQUEST_PRODUCT_IDS)) {
					for (int i = 0; i < productCount; i++) {
						productList.get(i).setProductId(
								response.getProperty(i).toString());
						Log.i(TAG, response.getProperty(i).toString());
					}
				} else if (requestCategory.equals(REQUEST_PRODUCT_PRICE)) {
					for (int i = 0; i < productCount; i++) {
						productList.get(i).setProductPrice(
								Integer.parseInt(response.getProperty(i)
										.toString()));
						Log.i(TAG, response.getProperty(i).toString());
					}
				} else if (requestCategory.equals(REQUEST_PRODUCT_NAME)) {
					for (int i = 0; i < productCount; i++) {
						productList.get(i).setProductName(
								response.getProperty(i).toString());
						Log.i(TAG, response.getProperty(i).toString());
					}
				}
			}

		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
		return productList;
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
	 * Fetch specifications
	 */
	public static String getProductSpectification(Context context, String prodId) {

		String specifiaction = null;
		String SOAP_ACTION = NAMESPACE + REQUEST_PRODUCT_SPECIFICATION;

		SoapObject request = new SoapObject(NAMESPACE,
				REQUEST_PRODUCT_SPECIFICATION);
		request.addProperty("prodId", prodId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		SoapObject result = null;
		// SoapObject result1=null;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					WEB_SERVICE_URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			androidHttpTransport
					.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			result = (SoapObject) envelope.bodyIn;
			// result1=(SoapObject)result.getProperty(0);

			if (!result.getProperty(0).toString().equals(null)
					|| result.getProperty(0).toString() != null) {
				specifiaction = result.getProperty(0).toString();
			}

		} catch (Exception e) {
			specifiaction = context.getString(R.string.no_spec_found);
			Log.e(TAG, "Exception", e);
		}

		return specifiaction;
	}

}
