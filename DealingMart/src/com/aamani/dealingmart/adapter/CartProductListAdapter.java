package com.aamani.dealingmart.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.CartHelper;
import com.aamani.dealingmart.interfaces.OnItemCountChangeListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class CartProductListAdapter extends BaseAdapter {

	protected static final String TAG = "CartProductListAdapter";
	private Activity activity;
	private List<ProductEntity> productList;
	private LayoutInflater infalter;

	private static final int INCREMENT = 0;
	private static final int DEACREMENT = 1;
	private boolean isIncDmtDisplayed;

	private OnItemCountChangeListener onCountChange;

	private ImageLoader imageLoader;

	public CartProductListAdapter(Activity activity,
			List<ProductEntity> productList, ImageLoader imageLoader,
			boolean isIncDmtDisplayed) {
		this.activity = activity;
		this.productList = productList;
		infalter = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = imageLoader;
		this.isIncDmtDisplayed = isIncDmtDisplayed;
		try {
			onCountChange = (OnItemCountChangeListener) activity;
		} catch (ClassCastException e) {
			Log.e(TAG, "ClassCastException", e);
		}
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
		RelativeLayout increamentLayout = (RelativeLayout) convertView
				.findViewById(R.id.cart_item_increment_number_layout);
		RelativeLayout deacreamentLayout = (RelativeLayout) convertView
				.findViewById(R.id.cart_item_deacrement_number_layout);
		final ProgressBar loadingProgressBar = (ProgressBar) convertView
				.findViewById(R.id.cart_item_progressbar);

		final ProductEntity product = productList.get(position);

		if (!isIncDmtDisplayed) {
			increamentLayout.setVisibility(View.GONE);
			deacreamentLayout.setVisibility(View.GONE);
			productDeleteLayout.setVisibility(View.GONE);
		}

		if (product != null) {

			productNametextView.setText(product.getProductName());
			// new AsycImageLoaderTask(productImageView, loadingProgressBar)
			// .execute(product.getProductImage());

			// image
			try {
				imageLoader.loadImage(product.getProductImage(),
						new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								loadingProgressBar.setVisibility(View.GONE);
								productImageView.setImageBitmap(loadedImage);
							}
						});
			} catch (IllegalStateException e) {
				Log.e(TAG, "IllegalStateException", e);
			}
			productPriceTextView.setText(String.valueOf(product
					.getProductPrice() * product.getProductCount()));

			productCountEditText.setText(String.valueOf(product
					.getProductCount()));

			increamentLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int count = Integer.valueOf(productCountEditText.getText()
							.toString());
					if (count < 999) {
						count++;
						productCountEditText.setText(String.valueOf(count));
						productPriceTextView.setText(String.valueOf(product
								.getProductPrice() * count));
						onCountChange.changeCount(product.getProductPrice(),
								INCREMENT);
						CartHelper.insertPorductToCart(activity, product);
					}

				}
			});

			deacreamentLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int count = Integer.valueOf(productCountEditText.getText()
							.toString());
					if (count > 1) {
						count--;
						onCountChange.changeCount(product.getProductPrice(),
								DEACREMENT);
						productPriceTextView.setText(String.valueOf(product
								.getProductPrice() * count));
						productCountEditText.setText(String.valueOf(count));
						CartHelper.deleteProduct(activity,
								String.valueOf(product.getProductId()), true);
					}

				}
			});

			productDeleteLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (productList.contains(product)) {
						CartHelper.deleteProduct(activity,
								product.getProductId(), false);
						productList.remove(product);
						onCountChange.changeCount((Integer
								.valueOf(productCountEditText.getText()
										.toString()) * product
								.getProductPrice()), DEACREMENT);
						notifyDataSetChanged();
					}
				}
			});
		}

		return convertView;
	}
}
