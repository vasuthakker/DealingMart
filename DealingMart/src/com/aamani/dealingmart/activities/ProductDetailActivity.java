package com.aamani.dealingmart.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.asynctask.AsycImageLoaderTask;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
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
	private TextView productPrizeTextView;
	private Button buyNowButton;
	private RatingBar productRatingBar;
	private TextView productOfferTextView;
	private TextView productSpecificationTextView;

	private ImageView shareSMSImageView;
	private ImageView shareEmailImageView;
	private ImageView shareWhatsappImageView;
	private ImageView shareTwitterImageView;
	private ImageView shareFacebookImageView;

	private RelativeLayout zoomImageLayout;

	private ProductEntity productObject;

	private ProgressBar loadingProgressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);

		// UI
		productNameTextView = (TextView) findViewById(R.id.product_detail_name_textview);
		productPrizeTextView = (TextView) findViewById(R.id.product_detail_prize_textview);
		productOfferTextView = (TextView) findViewById(R.id.product_detail_offer_textview);
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

		loadingProgressbar = (ProgressBar) findViewById(R.id.product_detail_progress_Bar);

		// getting product object from the intent
		productObject = (ProductEntity) getIntent().getSerializableExtra(
				DealingMartConstatns.PRODUCT_OBJECT);

		if (productObject != null) {
			productNameTextView.setText(productObject.getProductName());
			productPrizeTextView.setText("Rs:"
					+ productObject.getProductPrice());

			productSpecificationTextView.setText(Utils
					.getProductSpectification(getApplicationContext(),
							productObject.getProductId()));

			// setting image
			new AsycImageLoaderTask(productImage, loadingProgressbar)
					.execute(productObject.getProductImage());
		}

		shareSMSImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent shareIntent = new Intent(Intent.ACTION_VIEW);

				shareIntent.setType("vnd.android-dir/mms-sms");
				shareIntent.putExtra(
						"sms_body",
						getString(R.string.product_share_message).replace("$",
								"Note 3"));

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
				shareIntent.putExtra(
						android.content.Intent.EXTRA_TEXT,
						getString(R.string.product_share_message).replace("$",
								"Note 3"));

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
								getString(R.string.product_share_message)
										.replace("$", "Note 3"));//
						startActivity(Intent.createChooser(whatsappIntent,
								SHARE_PRODUCT_TITLE));
					}
				} else {

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
								getString(R.string.product_share_message)
										.replace("$", "Note 3"));//
						startActivity(Intent.createChooser(twitterIntent,
								SHARE_PRODUCT_TITLE));
					}
				} else {
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
								getString(R.string.product_share_message)
										.replace("$", "Note 3"));//
						startActivity(Intent.createChooser(facebookIntent,
								SHARE_PRODUCT_TITLE));
					}
				} else {
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
	}
}
