package com.aamani.dealingmart.activities;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.fragments.HomeMiddleFragment;
import com.aamani.dealingmart.helper.CartHelper;
import com.aamani.dealingmart.utility.Utils;

public class FullImageActivity extends Activity {

	private LinearLayout productDetailLayout;
	private ImageView downImageView;
	private ImageViewTouch fullImageView;
	private ProductEntity productObject;

	private TextView productNameTextView;
	private TextView productPriceTextView;

	private Button buyNowButton;

	private ProgressBar loadingProgressBar;

	private Bitmap image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);

		// UI
		productDetailLayout = (LinearLayout) findViewById(R.id.full_image_product_detail_layout);
		downImageView = (ImageView) findViewById(R.id.full_image_down_arrow_imageview);
		fullImageView = (ImageViewTouch) findViewById(R.id.full_image_view);

		productNameTextView = (TextView) findViewById(R.id.full_image_product_name_textview);
		productPriceTextView = (TextView) findViewById(R.id.full_image_product_price_textview);

		loadingProgressBar = (ProgressBar) findViewById(R.id.full_image_progressbar);

		buyNowButton = (Button) findViewById(R.id.full_image_buy_now_button);

		downImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (productDetailLayout.getVisibility() == View.VISIBLE) {
					productDetailLayout.animate().alpha(0f);
					productDetailLayout.animate().y(-50f);
					// downImageView.animate().y(-30f);
					productDetailLayout.setVisibility(View.GONE);
				} else {
					productDetailLayout.animate().alpha(1f);
					productDetailLayout.animate().y(50f);
					// downImageView.animate().y(-50f);
					productDetailLayout.setVisibility(View.VISIBLE);
				}

			}
		});

		productObject = (ProductEntity) getIntent().getSerializableExtra(
				DealingMartConstatns.PRODUCT_OBJECT);

		if (productObject != null) {

			new AsycImageLoaderTask().execute(productObject.getProductImage());
			String productName = productObject.getProductName();
			if (productName.length() > 25) {
				productName = productName.substring(0, 25) + "...";
			}
			productNameTextView.setText(productName);
			productPriceTextView.setText("Rs:"
					+ productObject.getProductPrice());

		}

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
	}

	private class AsycImageLoaderTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			image = Utils.getBitmapFromURL(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (image != null && fullImageView != null) {
				loadingProgressBar.setVisibility(View.GONE);
				fullImageView.setImageBitmap(image, null, -1, -1);
			}
		}

	}

}
