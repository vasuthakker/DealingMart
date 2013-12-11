package com.aamani.dealingmart.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.fragments.HomeLeftFragment;
import com.aamani.dealingmart.fragments.HomeMiddleFragment;

/**
 * Home activity
 * 
 * @author Vasu
 * 
 */
public class HomeActivity extends FragmentActivity {

	private static ViewPager homeViewPager;
	private static final int FRAGMENT_NUMBERS = 2;

	private static final int LEFT_PAGE = 0;
	private static final int MIDDLE_PAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		homeViewPager = (ViewPager) findViewById(R.id.home_viewpager);

		homeViewPager.setAdapter(new HomePager(getSupportFragmentManager()));

		homeViewPager.setCurrentItem(MIDDLE_PAGE);

	}

	// An adapter class for home viewpager
	private class HomePager extends FragmentPagerAdapter {

		public HomePager(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case LEFT_PAGE:
				fragment = new HomeLeftFragment();
				break;
			case MIDDLE_PAGE:
				fragment = new HomeMiddleFragment();
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return FRAGMENT_NUMBERS;
		}

		@Override
		public float getPageWidth(int position) {
			float pageWidth = 1f;
			if (position == LEFT_PAGE) {
				pageWidth = 0.8f;
			}
			return pageWidth;
		}
	}

	public static int ChangePage() {
		int currentPage = MIDDLE_PAGE;
		if (homeViewPager.getCurrentItem() != LEFT_PAGE) {
			homeViewPager.setCurrentItem(LEFT_PAGE);
			currentPage = LEFT_PAGE;
		} else {
			homeViewPager.setCurrentItem(MIDDLE_PAGE);
			currentPage = MIDDLE_PAGE;
		}
		return currentPage;

	}
}
