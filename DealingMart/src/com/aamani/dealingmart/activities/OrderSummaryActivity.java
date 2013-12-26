package com.aamani.dealingmart.activities;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.CartProductListAdapter;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.CartHelper;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Activity for order_summary
 * 
 * @author Vasu
 * 
 */
public class OrderSummaryActivity extends Activity {

	private ListView orderListView;
	private TextView totalAmountTextView;
	private Button payNowButton;

	private String emailId;
	private String sessionId;

	private int shipId;

	private ImageLoader imageLoader;

	private List<ProductEntity> orders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_summary_page);

		orderListView = (ListView) findViewById(R.id.order_summary_listview);

		totalAmountTextView = (TextView) findViewById(R.id.order_summary_total_amount_textview);

		payNowButton = (Button) findViewById(R.id.order_summary_pay_amount);

		emailId = getIntent().getStringExtra(DealingMartConstatns.EMAIL_ID);
		shipId = getIntent().getIntExtra(DealingMartConstatns.SHIP_ID, 0);
		sessionId = getIntent().getStringExtra(DealingMartConstatns.SESSION_ID);

		payNowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pushProductsToServer();
			}
		});

		

	}

	private void pushProductsToServer() {
		new PushProductFromCartAsyncTask().execute();

	}

	@Override
	public void onResume() {
		super.onResume();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(getApplicationContext()));
		
		orders = CartHelper.fetchProducts(getApplicationContext());

		int totalAmount = 0;
		for (ProductEntity product : orders) {
			totalAmount += (product.getProductPrice() * product
					.getProductCount());
		}

		totalAmountTextView.setText("Rs:" + totalAmount);

		orderListView.setAdapter(new CartProductListAdapter(this, orders,
				imageLoader, false));

	}

	@Override
	public void onPause() {
		super.onPause();
		imageLoader.destroy();
	}

	private class PushProductFromCartAsyncTask extends
			AsyncTask<Void, Void, Boolean> {

		ProgressDialog dialog = null;

		@Override
		public void onPreExecute() {
			dialog = new ProgressDialog(OrderSummaryActivity.this);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setMessage("");
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean isProductInserted = true;
			orders=CartHelper.fetchProducts(getApplicationContext());
			for (final ProductEntity product : orders) {
				isProductInserted = WebServiceHelper.pushProductFromCart(
						sessionId, product);
				if (!isProductInserted) {
					break;
				}
			}
			return isProductInserted;
		}

		@Override
		public void onPostExecute(Boolean result) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (result) {
				Intent intent = new Intent(getApplicationContext(),
						PaymentActivity.class);
				intent.putExtra(DealingMartConstatns.SESSION_ID, sessionId);
				intent.putExtra(DealingMartConstatns.EMAIL_ID, emailId);
				intent.putExtra(DealingMartConstatns.SHIP_ID, shipId);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		}

	}

}
