package com.aamani.dealingmart.adapter;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.ProductDetailActivity;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import com.nostra13.universalimageloader.core.assist.MemoryCacheUtil;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * Adapter class to display products
 * 
 * @author Vasu
 * 
 */
public class RecentProductAdapter extends BaseAdapter {
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<ProductEntity> productList;
	private ImageLoader imageLoader;
	
	public RecentProductAdapter(Activity activity,
			List<ProductEntity> productList, ImageLoader imageLoader) {
		this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.productList = productList;
		
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
	public View getView(int position, View view, ViewGroup arg2) {
		if (view == null) {
			view = inflater.inflate(R.layout.new_arrivals_layout_item, null);
		}
		
		RelativeLayout productLayout = (RelativeLayout) view
				.findViewById(R.id.new_arrivals_product_layout);
		final ImageView productImageView = (ImageView) view
				.findViewById(R.id.new_arrivals_product_imageview);
		ImageView hotProductImageView = (ImageView) view
				.findViewById(R.id.new_arrivals_fav_icon);
		TextView productPriceTextView = (TextView) view
				.findViewById(R.id.new_arrivals_price_textview);
		TextView productNameTextView = (TextView) view
				.findViewById(R.id.new_arrivals_name_textview);
		final ProgressBar loadProgressBar = (ProgressBar) view
				.findViewById(R.id.new_arrivals_progress_bar);
		
		loadProgressBar.setVisibility(View.GONE);
		
		final ProductEntity product = productList.get(position);
		
		if (product != null) {
			productLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity,
							ProductDetailActivity.class);
					intent.putExtra(DealingMartConstatns.PRODUCT_OBJECT,
							product);
					activity.startActivity(intent);
				}
			});
			String productName = product.getProductName();
			if (productName.length() > 40) {
				productName = productName.substring(0, 40) + "...";
			}
			productNameTextView.setText(productName);
			productPriceTextView.setText("Rs:" + product.getProductPrice());
			
			if (productImageView != null && product.getProductImage() != null
					&& !product.getProductImage().isEmpty()) {
				// new AsycImageLoaderTask(productImageView, loadProgressBar)
				// .execute(product.getProductImage());
				
				if (activity != null && imageLoader != null) {
					// imageLoader.loadImage(product.getProductImage(),
					// new SimpleImageLoadingListener() {
					// @Override
					// public void onLoadingComplete(String imageUri,
					// View view, Bitmap loadedImage) {
					// loadProgressBar.setVisibility(View.GONE);
					// productImageView
					// .setImageBitmap(loadedImage);
					// }
					// });
					
					setImageToImageView(product.getProductImage(),
							productImageView, loadProgressBar);
				}
				
			}
			
			if (product.getProductRating() >= 4) {
				hotProductImageView.setVisibility(View.VISIBLE);
			}
			else {
				hotProductImageView.setVisibility(View.GONE);
			}
		}
		
		return view;
		
	}
	
	private void setImageToImageView(String imageUri, ImageView view,
			final ProgressBar loadProgressBar) {
		imageLoader.displayImage(imageUri, view,
				new SimpleImageLoadingListener() {
					boolean cacheFound;
					
					@Override
					public void onLoadingStarted(String url, View view) {
						List<String> memCache = MemoryCacheUtil
								.findCacheKeysForImageUri(url, ImageLoader
										.getInstance().getMemoryCache());
						cacheFound = !memCache.isEmpty();
						if (!cacheFound) {
							File discCache = DiscCacheUtil.findInCache(url,
									ImageLoader.getInstance().getDiscCache());
							if (discCache != null) {
								cacheFound = discCache.exists();
							}
						}
					}
					
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (cacheFound) {
							loadProgressBar.setVisibility(View.GONE);
							MemoryCacheUtil.removeFromCache(imageUri,
									ImageLoader.getInstance().getMemoryCache());
							DiscCacheUtil.removeFromCache(imageUri, ImageLoader
									.getInstance().getDiscCache());
							
							ImageLoader.getInstance().displayImage(imageUri,
									(ImageView) view);
						}
					}
				});
		
	}
}
