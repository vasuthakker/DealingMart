package com.aamani.dealingmart.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.HttpHostConnectException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.utility.Utils;

import android.content.Context;
import android.util.Log;

public class WebServiceHelper {

	private static final String TAG = "WebServiceHelper";

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
	private static final String METHOD_GET_PRODUCT_REVIEW = "getProdsReviews";

	// request
	private static final String REQUEST_PRODUCT_IMAGES = "allProd";
	private static final String REQUEST_PRODUCT_IDS = "setProdId";
	private static final String REQUEST_PRODUCT_PRICE = "setProdPrice";
	private static final String REQUEST_PRODUCT_NAME = "setProdName";
	private static final String REQUEST_PRODUCT_RATINGS = "setProdReview";

	private static final String REQUEST_CATEGORY = "getCategory";

	private static final String REQUEST_SUBCATEGORY = "getSubCategory";

	private static final String PRODUCT_ID = "prodId";
	private static final String CATEGORY = "category";
	private static final String REQUEST_PRODUCT_SPECIFICATION = "fetchProductSpecification";

	private static final String KEY_PRODUCT_NAME = "productName";

	/**
	 * Method for getting products
	 * 
	 * @param category
	 * @param subCategory
	 * @return
	 */
	public static List<ProductEntity> getProducts(String category,
			String subCategory, String productName) {

		List<ProductEntity> products = new ArrayList<ProductEntity>();
		Utils.setStrictPolicy();

		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_IMAGES,
				products, productName);
		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_IDS,
				products, productName);
		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_PRICE,
				products, productName);
		getProdcutFromWebService(category, subCategory,
				REQUEST_PRODUCT_RATINGS, products, productName);
		return getProdcutFromWebService(category, subCategory,
				REQUEST_PRODUCT_NAME, products, productName);

	}

	/**
	 * Fetch specifications
	 */
	public static List<CategoryEntity> getCategory(Context context) {

		List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
		String SOAP_ACTION = NAMESPACE + REQUEST_CATEGORY;

		SoapObject request = new SoapObject(NAMESPACE, REQUEST_CATEGORY);

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

			result = (SoapObject) ((SoapObject) envelope.bodyIn).getProperty(0);

			if (result != null) {
				int categoryCount = result.getPropertyCount();
				CategoryEntity category = null;
				for (int i = 0; i < categoryCount; i++) {
					category = new CategoryEntity();
					category.setCategory(result.getProperty(i).toString());
					categories.add(category);
				}
			}
		} catch (HttpHostConnectException e) {
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		}

		return categories;
	}

	/**
	 * Fetch specifications
	 */
	public static List<SubCategoryEntity> getSubCategory(Context context,
			CategoryEntity category) {

		List<SubCategoryEntity> subCategories = new ArrayList<SubCategoryEntity>();
		String SOAP_ACTION = NAMESPACE + REQUEST_SUBCATEGORY;

		SoapObject request = new SoapObject(NAMESPACE, REQUEST_SUBCATEGORY);
		request.addProperty(CATEGORY, category.getCategory());

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

			result = (SoapObject) ((SoapObject) envelope.bodyIn).getProperty(0);

			if (result != null) {
				int categoryCount = result.getPropertyCount();
				SubCategoryEntity subCategory = null;
				for (int i = 0; i < categoryCount; i++) {
					subCategory = new SubCategoryEntity();
					subCategory.setCategoryId(category.getId());
					subCategory
							.setSubCategory(result.getProperty(i).toString());
					subCategories.add(subCategory);
				}
			}
		} catch (HttpHostConnectException e) {
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		}

		return subCategories;
	}

	/**
	 * Fetch specifications
	 */
	public static String getProductSpectification(Context context, String prodId) {

		String specifiaction = null;
		String SOAP_ACTION = NAMESPACE + REQUEST_PRODUCT_SPECIFICATION;

		SoapObject request = new SoapObject(NAMESPACE,
				REQUEST_PRODUCT_SPECIFICATION);
		request.addProperty(PRODUCT_ID, prodId);

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

			if (!result.getProperty(0).toString().equals(null)
					|| result.getProperty(0).toString() != null) {
				specifiaction = result.getProperty(0).toString();
			}
		} catch (HttpHostConnectException e) {
			specifiaction = context.getString(R.string.no_spec_found);
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			specifiaction = context.getString(R.string.no_spec_found);
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			specifiaction = context.getString(R.string.no_spec_found);
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			specifiaction = context.getString(R.string.no_spec_found);
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		}

		return specifiaction;
	}

	// load products from a web service
	private static List<ProductEntity> getProdcutFromWebService(
			String category, String subCategory, String requestCategory,
			List<ProductEntity> productList, String productName) {

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
		} else if (requestCategory.equals(REQUEST_PRODUCT_RATINGS)) {
			METHOD_NAME = METHOD_GET_PRODUCT_REVIEW;
		} else {
			METHOD_NAME = METHOD_GET_PRODUCT_SUBCATEGORY;
		}

		SOAP_ACTION.append(METHOD_NAME);

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// adding category and subcategory
		request.addProperty(KEY_CATEGORY, category);
		request.addProperty(KEY_SUBCATEGORY, subCategory);
		request.addProperty(KEY_PRODUCT_NAME, productName);

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
				} else if (requestCategory.equals(REQUEST_PRODUCT_RATINGS)) {
					for (int i = 0; i < productCount; i++) {
						productList.get(i).setProductRating(
								Integer.parseInt(response.getProperty(i)
										.toString()));
						Log.i(TAG, response.getProperty(i).toString());
					}
				}
			}

		} catch (HttpHostConnectException e) {
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		}
		return productList;
	}
}
