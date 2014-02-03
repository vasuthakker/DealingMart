package com.aamani.dealingmart.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.ProductAttributeAdapter;
import com.aamani.dealingmart.asynctask.AsycImageLoaderTask;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductAttributeEntity;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.fragments.HomeMiddleFragment;
import com.aamani.dealingmart.helper.CartHelper;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.aamani.dealingmart.utility.Utils;

/**
 * Activity for product detail
 * 
 * @author Vasu
 * 
 */
public class ProductDetailActivity extends Activity {
	
	private static final String SHARE_PRODUCT_TITLE = "Share Product";
	
	private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
	private static final String WHATSAPP_PACKAGE = "com.whatsapp";
	private static final String TWITTER_PACKAGE = "com.twitter.android";
	
	private TextView productNameTextView;
	private ImageView productImage;
	private TextView productPriceTextView;
	private Button buyNowButton;
	private RatingBar productRatingBar;
	private TextView productSpecificationTextView;
	
	private ImageView shareSMSImageView;
	private ImageView shareEmailImageView;
	private ImageView shareWhatsappImageView;
	private ImageView shareTwitterImageView;
	private ImageView shareFacebookImageView;
	
	private RelativeLayout zoomImageLayout;
	
	private ProductEntity productObject;
	
	private ProgressBar loadingProgressbar;
	
	private RelativeLayout cartNumberLayout;
	private TextView cartNumberTextView;
	private RelativeLayout cartClickLayout;
	
	private LinearLayout featureLayout;
	private ListView featureListView;
	private LinearLayout attributeLayout;
	private ListView attributeListView;
	
	private StringBuilder productDetailInfo = new StringBuilder();
	
	private static final String METHOD_GET_PRODUCT_ATTRIBUTE = "getProductAttributes";
	
	private static final String METHOD_GET_PRODUCT_FEATURES = "getProductFeatures";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		
		// UI
		productNameTextView = (TextView) findViewById(R.id.product_detail_name_textview);
		
		productPriceTextView = (TextView) findViewById(R.id.product_detail_prize_textview);
		productImage = (ImageView) findViewById(R.id.product_detail_image_imageview);
		buyNowButton = (Button) findViewById(R.id.product_detail_buynow_button);
		productRatingBar = (RatingBar) findViewById(R.id.product_detail_ratingbar);
		
		shareSMSImageView = (ImageView) findViewById(R.id.product_detail_share_sms_imageview);
		zoomImageLayout = (RelativeLayout) findViewById(R.id.product_detail_zoom_image_layout);
		shareEmailImageView = (ImageView) findViewById(R.id.product_detail_share_email_imageview);
		shareWhatsappImageView = (ImageView) findViewById(R.id.product_detail_share_whatsapp_icon);
		shareTwitterImageView = (ImageView) findViewById(R.id.product_detail_share_twitter_imageview);
		shareFacebookImageView = (ImageView) findViewById(R.id.product_detail_share_facebook_imageview);
		
		productSpecificationTextView = (TextView) findViewById(R.id.product_detail_specification_textview);
		cartNumberLayout = (RelativeLayout) findViewById(R.id.activity_cart_number_layout);
		cartNumberTextView = (TextView) findViewById(R.id.activity_cart_number_textview);
		cartClickLayout = (RelativeLayout) findViewById(R.id.acitivty_shopping_cart_view_layout);
		
		loadingProgressbar = (ProgressBar) findViewById(R.id.product_detail_progress_Bar);
		featureLayout = (LinearLayout) findViewById(R.id.size_layout);
		featureListView = (ListView) findViewById(R.id.productdetail_size_listview);
		
		attributeLayout = (LinearLayout) findViewById(R.id.product_detail_attribute_layout);
		attributeListView = (ListView) findViewById(R.id.product_detail_attribute_listview);
		
		// getting product object from the intent
		productObject = (ProductEntity) getIntent().getSerializableExtra(
				DealingMartConstatns.PRODUCT_OBJECT);
		
		buyNowButton.setEnabled(false);
		
		if (productObject != null) {
			productNameTextView.setText(productObject.getProductName());
			
			String origionalPrice = "Rs:" + productObject.getProductPrice();
			
			if (productObject.getProductDiscount() > 0) {
				
				// deducting discount
				SpannableStringBuilder sb = new SpannableStringBuilder(
						origionalPrice
								+ "  Rs:"
								+ (productObject.getProductPrice() - productObject
										.getProductPrice()
										* (productObject.getProductDiscount() / 100)));
				sb.setSpan(new StrikethroughSpan(), 0, origionalPrice.length(),
						SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				productPriceTextView.setText(sb);
				
				productObject.setProductPrice(productObject.getProductPrice()
						- productObject.getProductPrice()
						* (productObject.getProductDiscount() / 100));
			}
			else {
				productPriceTextView.setText("Rs:"
						+ productObject.getProductPrice());
			}
			
			new SetSpecificationTask().execute();
			
			// setting image
			new AsycImageLoaderTask(productImage, loadingProgressbar)
					.execute(productObject.getProductImage());
			
			new GetFeaturesTask().execute(productObject.getProductId());
			
			new GetAttributesTask().execute(productObject.getProductId());
			
			productRatingBar.setRating(productObject.getProductRating());
			
			productDetailInfo.append(getString(
					R.string.product__info_detail_text).replace("*",
					productObject.getProductName()).replace(
					"$",
					getString(R.string.product_url).replace("$",
							productObject.getProductId())));
			
		}
		
		shareSMSImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent shareIntent = new Intent(Intent.ACTION_VIEW);
				
				shareIntent.setType("vnd.android-dir/mms-sms");
				
				shareIntent.putExtra("sms_body", productDetailInfo.toString());
				
				startActivity(Intent.createChooser(shareIntent,
						SHARE_PRODUCT_TITLE));
				
			}
		});
		
		// imageshare
		shareEmailImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						getString(R.string.email_subject));
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						productDetailInfo.toString());
				
				shareIntent.setType("message/rfc822");
				
				startActivity(Intent.createChooser(shareIntent,
						SHARE_PRODUCT_TITLE));
				
			}
		});
		
		// whatsapp share
		shareWhatsappImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent whatsappIntent = getPackageManager()
						.getLaunchIntentForPackage(WHATSAPP_PACKAGE);
				if (whatsappIntent != null) {
					whatsappIntent = new Intent(Intent.ACTION_SEND);
					whatsappIntent.setType("text/plain");
					whatsappIntent.setPackage(WHATSAPP_PACKAGE);
					if (whatsappIntent != null) {
						whatsappIntent.putExtra(Intent.EXTRA_TEXT,
								productDetailInfo.toString());//
						startActivity(Intent.createChooser(whatsappIntent,
								SHARE_PRODUCT_TITLE));
					}
				}
			}
		});
		
		// twitter share
		shareTwitterImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent twitterIntent = getPackageManager()
						.getLaunchIntentForPackage(TWITTER_PACKAGE);
				if (twitterIntent != null) {
					twitterIntent = new Intent(Intent.ACTION_SEND);
					twitterIntent.setType("text/plain");
					twitterIntent.setPackage(TWITTER_PACKAGE);
					if (twitterIntent != null) {
						twitterIntent.putExtra(Intent.EXTRA_TEXT,
								productDetailInfo.toString());//
						startActivity(Intent.createChooser(twitterIntent,
								SHARE_PRODUCT_TITLE));
					}
				}
				else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.twitter_not_installed),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// facebook share
		shareFacebookImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent facebookIntent = getPackageManager()
						.getLaunchIntentForPackage(FACEBOOK_PACKAGE);
				if (facebookIntent != null) {
					facebookIntent = new Intent(Intent.ACTION_SEND);
					facebookIntent.setType("text/plain");
					facebookIntent.setPackage(FACEBOOK_PACKAGE);
					if (facebookIntent != null) {
						facebookIntent.putExtra(Intent.EXTRA_TEXT,
								productDetailInfo.toString());//
						startActivity(Intent.createChooser(facebookIntent,
								SHARE_PRODUCT_TITLE));
					}
				}
				else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.facebook_not_installed),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		zoomImageLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						FullImageActivity.class);
				intent.putExtra(DealingMartConstatns.PRODUCT_OBJECT,
						productObject);
				startActivity(intent);
			}
		});
		
		buyNowButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CartHelper.insertPorductToCart(getApplicationContext(),
						productObject);
				
				HomeMiddleFragment.updateProductNumber(CartHelper
						.fetchProdcutsCount(getApplicationContext()));
				
				Intent intent = new Intent(getApplicationContext(),
						ShoppingCartActivity.class);
				startActivity(intent);
				
			}
		});
		
		cartClickLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ShoppingCartActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int cartProductCount = CartHelper
				.fetchProdcutsCount(getApplicationContext());
		setCartNumber(cartProductCount);
	}
	
	private void setCartNumber(int count) {
		if (count > 0) {
			cartNumberLayout.setVisibility(View.VISIBLE);
			cartNumberTextView.setText(String.valueOf(count));
		}
		else {
			cartNumberLayout.setVisibility(View.GONE);
		}
	}
	
	// Async task for fetching specification
	private class SetSpecificationTask extends AsyncTask<Void, Void, String> {
		
		@Override
		protected String doInBackground(Void... params) {
			return WebServiceHelper.getProductSpectification(
					getApplicationContext(), productObject.getProductId());
		}
		
		@Override
		protected void onPostExecute(String specification) {
			buyNowButton.setEnabled(true);
			
			productSpecificationTextView.setText(specification);
		}
	}
	
	// Async task for fetching features
	private class GetFeaturesTask extends AsyncTask<String, Void, Void> {
		private List<ProductAttributeEntity> attributes;
		
		@Override
		protected Void doInBackground(String... params) {
			attributes = WebServiceHelper.getProductAttributes(params[0],
					METHOD_GET_PRODUCT_FEATURES);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void specification) {
			
			buyNowButton.setEnabled(true);
			
			if (attributes != null && !attributes.isEmpty()) {
				featureListView.setAdapter(new ProductAttributeAdapter(
						getApplicationContext(), attributes));
				featureListView.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT,
						Utils.dpToPx(getApplicationContext(),
								31 * attributes.size())));
			}
			else {
				featureLayout.setVisibility(View.GONE);
			}
		}
	}
	
	// Async task for fetching features
	private class GetAttributesTask extends AsyncTask<String, Void, Void> {
		protected static final String TAG = "GetAttributesTask";
		private List<ProductAttributeEntity> attributes;
		
		@Override
		protected Void doInBackground(String... params) {
			attributes = WebServiceHelper.getProductAttributes(params[0],
					METHOD_GET_PRODUCT_ATTRIBUTE);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void specification) {
			buyNowButton.setEnabled(true);
			
			if (attributes != null && !attributes.isEmpty()) {
				
				attributeListView.setAdapter(new ProductAttributeAdapter(
						getApplicationContext(), attributes));
				attributeListView
						.setLayoutParams(new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, Utils.dpToPx(
										getApplicationContext(),
										31 * attributes.size())));
				
				buyNowButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(
								getApplicationContext(),
								"Please select your choice from the product detail",
								Toast.LENGTH_SHORT).show();
					}
				});
				
				attributeListView
						.setOnItemClickListener(new OnItemClickListener() {
							
							@Override
							public void onItemClick(AdapterView<?> arg0,
									View view, int position, long id) {
								
								productObject.setAttributeId(attributes.get(
										position).getAttributeId());
								try {
									productObject.setProductPrice(Integer
											.parseInt(attributes.get(position)
													.getAttributeValue()));
								}
								catch (NumberFormatException e) {
									Log.e(TAG, "NumberFormatException", e);
								}
								CartHelper.insertPorductToCart(
										getApplicationContext(), productObject);
								
								HomeMiddleFragment.updateProductNumber(CartHelper
										.fetchProdcutsCount(getApplicationContext()));
								
								Intent intent = new Intent(
										getApplicationContext(),
										ShoppingCartActivity.class);
								startActivity(intent);
								
							}
						});
			}
			else {
				attributeLayout.setVisibility(View.GONE);
			}
		}
	}
}
