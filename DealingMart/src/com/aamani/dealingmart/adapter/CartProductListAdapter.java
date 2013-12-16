package com.aamani.dealingmart.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.ShoppingCartActivity;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.CartHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class CartProductListAdapter extends BaseAdapter {

	protected static final String TAG = "CartProductListAdapter";
	private Activity activity;
	private List<ProductEntity> productList;
	private LayoutInflater infalter;

	private static final int INCREMENT = 0;
	private static final int DEACREMENT = 1;

	private ImageLoader imageLoader;

	public CartProductListAdapter(Activity activity,
			List<ProductEntity> productList, ImageLoader imageLoader) {
		this.activity = activity;
		this.productList = productList;
		infalter = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = infalter.inflate(R.layout.shopping_cart_item, null);
		}

		final ImageView productImageView = (ImageView) convertView
				.findViewById(R.id.cart_item_product_imageview);
		TextView productNametextView = (TextView) convertView
				.findViewById(R.id.cart_item_product_name_textview);
		final EditText productCountEditText = (EditText) convertView
				.findViewById(R.id.cart_item_productcount_edittext);
		RelativeLayout productDeleteLayout = (RelativeLayout) convertView
				.findViewById(R.id.cart_item_delete_product_layout);
		final TextView productPriceTextView = (TextView) convertView
				.findViewById(R.id.cart_item_product_price_textview);
		ImageView increamentTextView = (ImageView) convertView
				.findViewById(R.id.cart_item_increment_number_imageview);
		ImageView deacreamentTextView = (ImageView) convertView
				.findViewById(R.id.cart_item_deacrement_number_imageview);
		final ProgressBar loadingProgressBar = (ProgressBar) convertView
				.findViewById(R.id.cart_item_progressbar);

		final ProductEntity product = productList.get(position);

		if (product != null) {
			productNametextView.setText(product.getProductName());
			// new AsycImageLoaderTask(productImageView, loadingProgressBar)
			// .execute(product.getProductImage());

			// image
			imageLoader.loadImage(product.getProductImage(),
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							loadingProgressBar.setVisibility(View.GONE);
							productImageView.setImageBitmap(loadedImage);
						}
					});

			productPriceTextView.setText(String.valueOf(product
					.getProductPrice() * product.getProductCount()));

			productCountEditText.setText(String.valueOf(product
					.getProductCount()));

			increamentTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int count = Integer.valueOf(productCountEditText.getText()
							.toString());
					if (count < 999) {
						count++;
						productCountEditText.setText(String.valueOf(count));
						ShoppingCartActivity.changeTotal(
								product.getProductPrice(), INCREMENT);
					}

				}
			});

			deacreamentTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int count = Integer.valueOf(productCountEditText.getText()
							.toString());
					if (count > 1) {
						count--;
						ShoppingCartActivity.changeTotal(
								product.getProductPrice(), DEACREMENT);
						productCountEditText.setText(String.valueOf(count));
					}

				}
			});

			productCountEditText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {

					try {
						int count = Integer.parseInt(s.toString());
						productPriceTextView.setText(String.valueOf(count
								* product.getProductPrice()));
					} catch (NumberFormatException e) {
						Log.e(TAG, "NumberFormatException", e);

					}
				}
			});

			productDeleteLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (productList.contains(product)) {
						CartHelper.deleteProduct(activity,
								product.getProductId());
						productList.remove(product);
						ShoppingCartActivity.changeTotal(
								(product.getProductCount() * product
										.getProductPrice()), DEACREMENT);
						notifyDataSetChanged();
					}
				}
			});
		}

		return convertView;
	}
}
