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

import android.content.Context;
import android.util.Log;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.entities.ShippingAddressEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.utility.Utils;

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
	private static final String METHOD_GET_CATEGORY = "getCategory";
	private static final String METHOD_GET_SUBCATEGORY = "getSubCategory";
	private static final String METHOD_GET_OFFERS = "fetchSpecialOffers";
	private static final String METHOD_AUTHENTICATION = "userAuthentication";
	private static final String METHOD_CHECK_SHIPPING_ADDRESS = "checkShippingAddress";
	private static final String METHOD_INSERT_SHIPPING_ID = "insertShippingAddress";
	private static final String METHOD_INSERT_PRODUCT_INTO_CART = "insertProductIntoCart";
	private static final String METHOD_GET_BRAND_NAMES = "getBrandNamess";
	private static final String METHOD_INSERT_GCM_ID = "registerDeviceForPusNotification";
	
	// request
	private static final String REQUEST_PRODUCT_IMAGES = "allProd";
	private static final String REQUEST_PRODUCT_IDS = "setProdId";
	private static final String REQUEST_PRODUCT_PRICE = "setProdPrice";
	private static final String REQUEST_PRODUCT_NAME = "setProdName";
	private static final String REQUEST_BRAND_NAME = "setBrandName";
	private static final String REQUEST_PRODUCT_RATINGS = "setProdReview";
	private static final String REQUEST_PRODUCT_SPECIFICATION = "fetchProductSpecification";

	// constants
	private static final String PRODUCT_ID = "prodId";
	private static final String CATEGORY = "category";
	private static final String PRODUCT_TOTAL_QUANTITY = "totalQuantity";
	private static final String CART_PRODUCT_ID = "productId";

	private static final String KEY_PRODUCT_NAME = "productName";

	private static final String EMAIL_ID = "email_id";
	private static final String PASSWORD = "password";

	private static final String SHIP_ID = "shipId";
	private static final String NAME = "name";
	private static final String PHONE = "phone";
	private static final String EMAIL = "email";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String PINCODE = "pincode";
	private static final String COUNTRY = "country";
	private static final String ADDRESS = "address";
	private static final String GOOGLE_ACCOUNT = "googleAccount";
	private static final String GCM_ID = "gcmId";
	
	private static final String SESSION_ID = "sessionId";

	private static final String KEY_LIMIT = "limit";

	

	

	

	/**
	 * Method for getting products
	 * 
	 * @param category
	 * @param subCategory
	 * @return
	 */
	public static List<ProductEntity> getProducts(String category,
			String subCategory, String productName, int limit) {

		List<ProductEntity> products = new ArrayList<ProductEntity>();
		Utils.setStrictPolicy();

		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_IMAGES,
				products, productName, limit);
		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_IDS,
				products, productName, limit);
		getProdcutFromWebService(category, subCategory, REQUEST_PRODUCT_PRICE,
				products, productName, limit);
		getProdcutFromWebService(category, subCategory, REQUEST_BRAND_NAME,
				products, productName, limit);
		getProdcutFromWebService(category, subCategory,
				REQUEST_PRODUCT_RATINGS, products, productName, limit);
		return getProdcutFromWebService(category, subCategory,
				REQUEST_PRODUCT_NAME, products, productName, limit);

	}

	/**
	 * Fetch specifications
	 */
	public static List<CategoryEntity> getCategory(Context context) {

		List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
		String SOAP_ACTION = NAMESPACE + METHOD_GET_CATEGORY;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_GET_CATEGORY);

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
	public static List<String> getSpecialOffers(Context context) {

		List<String> offers = new ArrayList<String>();
		String SOAP_ACTION = NAMESPACE + METHOD_GET_OFFERS;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_GET_OFFERS);

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
				for (int i = 0; i < categoryCount; i++) {
					offers.add(result.getProperty(i).toString());
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

		return offers;
	}

	/**
	 * Fetch specifications
	 */
	public static List<SubCategoryEntity> getSubCategory(Context context,
			CategoryEntity category) {

		List<SubCategoryEntity> subCategories = new ArrayList<SubCategoryEntity>();
		String SOAP_ACTION = NAMESPACE + METHOD_GET_SUBCATEGORY;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_GET_SUBCATEGORY);
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
			List<ProductEntity> productList, String productName, int limit) {

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
		} else if (requestCategory.equals(REQUEST_BRAND_NAME)) {
			METHOD_NAME = METHOD_GET_BRAND_NAMES;
		} else if (requestCategory.equals(REQUEST_PRODUCT_RATINGS)) {
			METHOD_NAME = METHOD_GET_PRODUCT_REVIEW;
		} else {
			METHOD_NAME = METHOD_GET_PRODUCT_SUBCATEGORY;
		}

		SOAP_ACTION.append(METHOD_NAME);

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		// adding category and subcategory
		request.addProperty(KEY_LIMIT, limit);
		if (productName != null && !productName.isEmpty()) {
			request.addProperty(KEY_PRODUCT_NAME, productName);
		} else {
			request.addProperty(KEY_CATEGORY, category);
			request.addProperty(KEY_SUBCATEGORY, subCategory);
		}

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
				} else if (requestCategory.equals(REQUEST_BRAND_NAME)) {
					for (int i = 0; i < productCount; i++) {
						String brandName = response.getProperty(i).toString();
						if (brandName == null || brandName.isEmpty() || brandName.equals("anyType{}")) {
							continue;
						} else {
							productList.get(i).setBrandName(
									response.getProperty(i).toString());
						}
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

	/**
	 * Method for checking shipping address
	 * 
	 * @param context
	 * @param email
	 * @return
	 */
	public static ShippingAddressEntity checkShippingAddress(Context context,
			String email) {

		ShippingAddressEntity address = null;
		String SOAP_ACTION = NAMESPACE + METHOD_CHECK_SHIPPING_ADDRESS;

		SoapObject request = new SoapObject(NAMESPACE,
				METHOD_CHECK_SHIPPING_ADDRESS);

		request.addProperty(EMAIL_ID, email);

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

			if (result != null && result.getPropertyCount() > 0) {
				address = new ShippingAddressEntity();

				address.setId(Integer.parseInt(result.getProperty("id")
						.toString()));
				address.setName(result.getProperty("name").toString());
				address.setPhone(result.getProperty("phone").toString());
				address.setEmail(result.getProperty("email").toString());
				address.setAddress(result.getProperty("address").toString());
				address.setCity(result.getProperty("city").toString());
				address.setState(result.getProperty("city").toString());
				address.setPincode(result.getProperty("pincode").toString());
				address.setCountry(result.getProperty("country").toString());
			}
		} catch (HttpHostConnectException e) {
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		} catch (Throwable e) {
			Log.e(TAG, "Exception", e);
		}

		return address;

	}

	/**
	 * Method for pushing shipping detail
	 * 
	 * @param context
	 * @param name
	 * @param phone
	 * @param email
	 * @param address
	 * @param city
	 * @param state
	 * @param pincode
	 * @param country
	 * @return
	 */
	public static int insertShippingAddress(Context context,
			ShippingAddressEntity address) {

		int shippingId = 0;
		String SOAP_ACTION = NAMESPACE + METHOD_INSERT_SHIPPING_ID;

		SoapObject request = new SoapObject(NAMESPACE,
				METHOD_INSERT_SHIPPING_ID);

		request.addProperty(SHIP_ID, address.getId());
		request.addProperty(NAME, address.getName());
		request.addProperty(PHONE, address.getPhone());
		request.addProperty(EMAIL, address.getEmail());
		request.addProperty(ADDRESS, address.getAddress());
		request.addProperty(CITY, address.getCity());
		request.addProperty(STATE, address.getState());
		request.addProperty(PINCODE, address.getPincode());
		request.addProperty(COUNTRY, address.getCountry());

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

			if (result != null && result.getPropertyCount() > 0) {
				shippingId = Integer.parseInt(result.getProperty(0).toString());
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

		return shippingId;

	}
	
	/**
	 * Method for inserting gcm id to the server
	 * @param context
	 * @param address
	 * @return
	 */
	public static int registerDeviceForPushNotification(Context context,
			String googleAccount,String gcmId) {

		int insertedGcmId = 0;
		String SOAP_ACTION = NAMESPACE + METHOD_INSERT_GCM_ID;

		SoapObject request = new SoapObject(NAMESPACE,
				METHOD_INSERT_GCM_ID);

		request.addProperty(GOOGLE_ACCOUNT, googleAccount);
		request.addProperty(GCM_ID, gcmId);

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

			if (result != null && result.getPropertyCount() > 0) {
				insertedGcmId = Integer.parseInt(result.getProperty(0).toString());
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

		return insertedGcmId;

	}

	/**
	 * Method for authenticating user
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean authenticate(String userName, String password) {
		boolean isValid = false;
		String SOAP_ACTION = NAMESPACE + METHOD_AUTHENTICATION;

		SoapObject request = new SoapObject(NAMESPACE, METHOD_AUTHENTICATION);

		request.addProperty(EMAIL_ID, userName);
		request.addProperty(PASSWORD, password);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		SoapObject result = null;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					WEB_SERVICE_URL);
			androidHttpTransport
					.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			androidHttpTransport.call(SOAP_ACTION, envelope);

			result = (SoapObject) envelope.bodyIn;

			if (result.getProperty(0).toString() != null) {
				isValid = Boolean
						.parseBoolean(result.getProperty(0).toString());
			}
		} catch (NumberFormatException e) {
			Log.e(TAG, "NumberFormatException", e);
		} catch (HttpHostConnectException e) {
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		}

		return isValid;
	}

	/**
	 * Method for pushing products into cart
	 * 
	 * @param product
	 * @return
	 */
	public static boolean pushProductFromCart(String sessionId,
			ProductEntity product) {
		boolean isInseted = false;

		String SOAP_ACTION = NAMESPACE + METHOD_INSERT_PRODUCT_INTO_CART;

		SoapObject request = new SoapObject(NAMESPACE,
				METHOD_INSERT_PRODUCT_INTO_CART);

		request.addProperty(SESSION_ID, sessionId);
		request.addProperty(CART_PRODUCT_ID, product.getProductId());
		request.addProperty(PRODUCT_TOTAL_QUANTITY, product.getProductCount());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		SoapObject result = null;

		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					WEB_SERVICE_URL);
			androidHttpTransport
					.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			androidHttpTransport.call(SOAP_ACTION, envelope);

			result = (SoapObject) envelope.bodyIn;

			if (result.getProperty(0).toString() != null) {
				int insertedId = Integer.valueOf(result.getProperty(0)
						.toString());
				if (insertedId > 0) {
					isInseted = true;
				}
			}
		} catch (NumberFormatException e) {
			Log.e(TAG, "NumberFormatException", e);
		} catch (HttpHostConnectException e) {
			Log.e(TAG, "HttpHostConnectException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "ArrayIndexOutOfBoundsException", e);
		}

		return isInseted;
	}
}
