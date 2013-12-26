package com.aamani.dealingmart.fragments;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.ProductDetailActivity;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.aamani.dealingmart.view.HorizontalListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class HomeDetailFragment extends Fragment {

	private ViewPager dealViewPager;
	private int[] dealImageArray = new int[] { R.drawable.banner_1,
			R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_4 };

	private static final int ROTATE_TIME = 4000;

	private HorizontalListView brandListView;
	private ImageLoader imageLoader;
	private LinearLayout offerLayout;

	private ListView offerListView;

	private ListView recentProductListView;
	private LinearLayout recentProductsLoadingLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_home_detail_, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

		offerLayout = (LinearLayout) getActivity().findViewById(
				R.id.offer_layout);

		// get offers
		new ASyncLoadOffersTask().execute();

		// UI elements
		dealViewPager = (ViewPager) getActivity().findViewById(
				R.id.deal_viewpager);

		recentProductsLoadingLayout = (LinearLayout) getActivity()
				.findViewById(R.id.recent_product_loading_layout);

		dealViewPager.setAdapter(new DealImageAdapter(dealImageArray));

		recentProductListView = (ListView) getActivity().findViewById(
				R.id.recent_product_listview);

		offerListView = (ListView) getActivity().findViewById(
				R.id.home_detail_offer_listview);

		// productHorizontalListView.setAdapter(new ProductAdapter(
		// getActivity(), 20,imageLoader));

		brandListView = (HorizontalListView) getActivity().findViewById(
				R.id.brands_listview);

		final Handler dealRoatateHandler = new Handler();
		dealRoatateHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				int currentItem = dealViewPager.getCurrentItem();
				if (currentItem == 3) {
					currentItem = 0;
				} else {
					currentItem++;
				}
				dealViewPager.setCurrentItem(currentItem);
				dealRoatateHandler.postDelayed(this, ROTATE_TIME);
			}
		}, ROTATE_TIME);

		new AsyncLoadRecentProductsTask().execute();

	}

	// Async task for loading offers from server
	private class ASyncLoadOffersTask extends AsyncTask<Void, Void, Void> {

		List<String> offers;

		@Override
		protected Void doInBackground(Void... params) {
			offers = WebServiceHelper.getSpecialOffers(getActivity());
			return null;
		}

		@Override
		public void onPostExecute(Void param) {
			if (offerListView != null) {
				if (offers != null && !offers.isEmpty()
						&& getActivity() != null) {
					offerListView
							.setAdapter(new ArrayAdapter<String>(getActivity(),
									R.layout.offer_textview_item, offers));
				} else {
					offerLayout.setVisibility(View.GONE);
				}
			}
		}
	}

	// Async task for loading last 10 added products
	private class AsyncLoadRecentProductsTask extends
			AsyncTask<Void, Void, Void> {
		private List<ProductEntity> recentProducts;

		@Override
		public void onPreExecute() {
			if (recentProducts == null || recentProducts.isEmpty()) {
				recentProductsLoadingLayout.setVisibility(View.VISIBLE);
				recentProductListView.setVisibility(View.GONE);
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			recentProducts = WebServiceHelper.getProducts(null, null, null, 10);
			return null;
		}

		@Override
		public void onPostExecute(Void param) {
			recentProductsLoadingLayout.setVisibility(View.GONE);
			if (getActivity() != null && recentProductListView != null
					&& recentProducts != null && !recentProducts.isEmpty()) {
				recentProductListView.setVisibility(View.VISIBLE);
				recentProductListView
						.setAdapter(new ArrayAdapter<ProductEntity>(
								getActivity(), R.layout.offer_textview_item,
								recentProducts));

				recentProductListView
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int position, long id) {
								Intent intent = new Intent(getActivity(),
										ProductDetailActivity.class);
								intent.putExtra(
										DealingMartConstatns.PRODUCT_OBJECT,
										recentProducts.get(position));
								startActivity(intent);
							}
						});
			}

		}

	}

	private class DealImageAdapter extends PagerAdapter {

		private int[] dealImageArray;

		public DealImageAdapter(int[] dealImageArray) {
			this.dealImageArray = dealImageArray;
		}

		@Override
		public int getCount() {
			return dealImageArray.length;
		}

		// @Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((View) object);
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			ImageView dealImageView = new ImageView(getActivity());

			dealImageView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			dealImageView.setScaleType(ScaleType.FIT_XY);
			dealImageView.setBackgroundResource(dealImageArray[position]);
			((ViewPager) collection).addView(dealImageView, 0);
			return dealImageView;
		}

	}

}
