package com.aamani.dealingmart.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.CartProductListAdapter;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.CartHelper;
import com.aamani.dealingmart.interfaces.OnItemCountChangeListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Activity for shopping cart
 * 
 * @author Vasu
 * 
 */
public class ShoppingCartActivity extends Activity implements
		OnItemCountChangeListener {

	private ListView cartProductListView;
	private TextView totalTextView;
	private Button continueShoppingButton;
	private float totalValue = 0f;

	private static final int INCREMENT = 0;

	private ImageLoader imageLoader;

	private List<ProductEntity> cartProducts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);

		cartProductListView = (ListView) findViewById(R.id.cart_listview);
		continueShoppingButton = (Button) findViewById(R.id.cart_continue_button);
		totalTextView = (TextView) findViewById(R.id.cart_total_textview);

		continueShoppingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				checkCartAndContinue();
			}
		});

	}

	private void checkCartAndContinue() {
		if (cartProducts.size() > 0) {
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.no_product_in_the_cart),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		totalValue = 0;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration
				.createDefault(getApplicationContext()));
		cartProducts = CartHelper.fetchProducts(getApplicationContext(),false);

		cartProductListView.setAdapter(new CartProductListAdapter(this,
				cartProducts, imageLoader, true));

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

	@Override
	public void changeCount(float price, int inc_dmt) {
		if (inc_dmt == INCREMENT) {
			totalValue = totalValue + price;
		} else {
			totalValue = totalValue - price;
		}
		totalTextView.setText("Total: " + totalValue);
	}
}
