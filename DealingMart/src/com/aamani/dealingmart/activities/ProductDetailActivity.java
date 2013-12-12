package com.aamani.dealingmart.activities;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aamani.dealingmart.R;

/**
 * Activity for product detail
 * 
 * @author Vasu
 * 
 */
public class ProductDetailActivity extends Activity {

	private TextView productNameTextView;
	private ImageViewTouch productImage;
	private TextView productPrizeTextView;
	private Button buyNowButton;
	private RatingBar productRatingBar;
	private TextView productDetailTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);

		// UI
		productNameTextView = (TextView) findViewById(R.id.product_detail_name_textview);
		productPrizeTextView = (TextView) findViewById(R.id.product_detail_prize_textview);
		productDetailTextView = (TextView) findViewById(R.id.product_detail_detail_textview);
		productImage = (ImageViewTouch) findViewById(R.id.product_detail_image_imageview);
		buyNowButton = (Button) findViewById(R.id.product_detail_buynow_button);
		productRatingBar = (RatingBar) findViewById(R.id.product_detail_ratingbar);

		productNameTextView.setText("Note 3");
		productPrizeTextView.setText("Rs: 45,000");
		productDetailTextView.setText("Galaxy Note 3");

		Bitmap image = BitmapFactory.decodeResource(getResources(),
				R.drawable.note_3);

		productImage.setImageBitmap(image, null, -1, -1);
	}

}
