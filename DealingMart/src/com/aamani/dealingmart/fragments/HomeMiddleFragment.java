package com.aamani.dealingmart.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.HomeActivity;
import com.aamani.dealingmart.adapter.HorizontalHomeProductAdapter;
import com.aamani.dealingmart.view.HorizontalListView;

/**
 * Home Middle Fragment to show deal,news
 * 
 * @author Vasu
 * 
 */
public class HomeMiddleFragment extends Fragment {

	private ViewPager dealViewPager;
	private int[] dealImageArray = new int[] { R.drawable.banner_1,
			R.drawable.banner_2, R.drawable.banner_3, R.drawable.banner_4 };
	private static final int ROTATE_TIME = 3000;
	private HorizontalListView productHorizontalListView;
	private ImageView leftNavigationImageview;

	private static final int LEFT_PAGE = 0;
	private static final int MIDDLE_PAGE = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater
				.inflate(R.layout.fragment_home_middle, container, false);

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

		leftNavigationImageview = (ImageView) getActivity().findViewById(
				R.id.home_navigation_imageview);

		productHorizontalListView.setAdapter(new HorizontalHomeProductAdapter(
				getActivity()));

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

		leftNavigationImageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentItem = HomeActivity.ChangePage();
				if (currentItem == MIDDLE_PAGE) {
					leftNavigationImageview.setRotation(180f);
				} else {
					leftNavigationImageview.setRotation(0f);
				}
			}
		});
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
