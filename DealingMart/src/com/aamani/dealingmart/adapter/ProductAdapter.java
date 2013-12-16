package com.aamani.dealingmart.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.ProductDetailActivity;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ProductAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<ProductEntity> productList;
	private ImageLoader imageLoader;

	public ProductAdapter(Activity activity, List<ProductEntity> productList,ImageLoader imageLoader) {
		this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.productList = productList;

		this.imageLoader=imageLoader;
		
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
	public View getView(int position, View view, ViewGroup arg2) {
		if (view == null) {
			view = inflater.inflate(R.layout.product_horizontal_item, null);
		}

		LinearLayout productLayout = (LinearLayout) view
				.findViewById(R.id.product_item_layout);
		final ImageView productImageView = (ImageView) view
				.findViewById(R.id.hor_product_image);
		ImageView hotProductImageView = (ImageView) view
				.findViewById(R.id.hor_product_star_image);
		TextView offerTextView = (TextView) view
				.findViewById(R.id.hor_offer_textview);
		TextView productPriceTextView = (TextView) view
				.findViewById(R.id.hor_porduct_prize_textview);
		TextView productNameTextView = (TextView) view
				.findViewById(R.id.hor_porduct_name_textview);
		final ProgressBar loadProgressBar = (ProgressBar) view
				.findViewById(R.id.hor_product_load_progressbar);

		final ProductEntity product = productList.get(position);

		if (product != null) {
			productLayout.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					Intent intent = new Intent(activity,
							ProductDetailActivity.class);
					intent.putExtra(DealingMartConstatns.PRODUCT_OBJECT,
							product);
					activity.startActivity(intent);
					return true;
				}
			});
			productNameTextView.setText(product.getProductName());
			productPriceTextView.setText("Rs:" + product.getProductPrice());

			if (productImageView != null && product.getProductImage() != null
					&& !product.getProductImage().isEmpty()) {
				// new AsycImageLoaderTask(productImageView, loadProgressBar)
				// .execute(product.getProductImage());

				if (activity != null && imageLoader != null) {
					imageLoader.loadImage(product.getProductImage(),
							new SimpleImageLoadingListener() {
								@Override
								public void onLoadingComplete(String imageUri,
										View view, Bitmap loadedImage) {
									loadProgressBar.setVisibility(View.GONE);
									productImageView
											.setImageBitmap(loadedImage);
								}
							});
				}

			}
		}
		
		return view;

	}
}
