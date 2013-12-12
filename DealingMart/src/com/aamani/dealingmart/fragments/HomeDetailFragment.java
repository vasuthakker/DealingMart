package com.aamani.dealingmart.fragments;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.ProductAdapter;
import com.aamani.dealingmart.view.HorizontalListView;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HomeDetailFragment extends Fragment {

	private ViewPager dealViewPager;
	private int[] dealImageArray = new int[] { R.drawable.banner_1,
			R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_4 };
	private static final int ROTATE_TIME = 3000;
	private HorizontalListView productHorizontalListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_home_detail_, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();

		// UI elements
		dealViewPager = (ViewPager) getActivity().findViewById(
				R.id.deal_viewpager);

		dealViewPager.setAdapter(new DealImageAdapter(dealImageArray));

		productHorizontalListView = (HorizontalListView) getActivity()
				.findViewById(R.id.product_horizontal_listview);

		productHorizontalListView.setAdapter(new ProductAdapter(
				getActivity(), 20));

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
