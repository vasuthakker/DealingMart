package com.aamani.dealingmart.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.CartProductListAdapter;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.CartHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Activity for shopping cart
 * 
 * @author Vasu
 * 
 */
public class ShoppingCartActivity extends Activity {

	private ListView cartProductListView;
	private static TextView totalTextView;
	private Button continueShoppingButton;
	private static int totalValue;

	private static final int INCREMENT = 0;
	private static final int DEACREMENT = 1;

	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);

		cartProductListView = (ListView) findViewById(R.id.cart_listview);
		continueShoppingButton = (Button) findViewById(R.id.cart_continue_button);
		totalTextView = (TextView) findViewById(R.id.cart_total_textview);

	}

	public static void changeTotal(int value, int incment_dcment) {

		if (incment_dcment == INCREMENT) {
			totalValue = totalValue + value;
		} else {
			totalValue = totalValue - value;
		}
		totalTextView.setText("Total: " + totalValue);
	}

	@Override
	public void onResume() {
		super.onResume();
		totalValue = 0;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(getApplicationContext()));
		List<ProductEntity> cartProducts = CartHelper
				.fetchProducts(getApplicationContext());

		cartProductListView.setAdapter(new CartProductListAdapter(this,
				cartProducts, imageLoader));

		for (ProductEntity product : cartProducts) {

			totalValue += (product.getProductPrice() * product
					.getProductCount());
		}

		totalTextView.setText("Total: " + totalValue);
	}

	@Override
	public void onPause() {
		super.onPause();
		imageLoader.destroy();
	}
}
