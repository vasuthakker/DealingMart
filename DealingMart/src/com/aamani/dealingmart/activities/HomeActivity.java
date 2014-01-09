package com.aamani.dealingmart.activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.fragments.CategoryViewFragment;
import com.aamani.dealingmart.fragments.HomeDetailFragment;
import com.aamani.dealingmart.fragments.HomeLeftFragment;
import com.aamani.dealingmart.fragments.HomeMiddleFragment;
import com.aamani.dealingmart.interfaces.OnCategoryItemSelected;
import com.aamani.dealingmart.utility.Utils;

/**
 * Home activity
 * 
 * @author Vasu
 * 
 */
public class HomeActivity extends FragmentActivity implements
		OnCategoryItemSelected {

	private static ViewPager homeViewPager;
	private static final int FRAGMENT_NUMBERS = 2;

	private static final int LEFT_PAGE = 0;
	private static final int MIDDLE_PAGE = 1;
	private static final String TAG = "HomeActivity";

	private RelativeLayout homeBaseLayout;
	private static FragmentManager fragmentManager;
	private static boolean isCategoryViewSelected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		homeViewPager = (ViewPager) findViewById(R.id.home_viewpager);

		homeViewPager.setAdapter(new HomePager(getSupportFragmentManager()));

		homeViewPager.setCurrentItem(MIDDLE_PAGE);

		homeBaseLayout = (RelativeLayout) findViewById(R.id.home_base_layout);

		fragmentManager = getSupportFragmentManager();
	String isAppShared = Utils.getPreference(getApplicationContext(),
				DealingMartConstatns.APP_SHARED);
		if (isAppShared == null || isAppShared.isEmpty()) {
			MessageDialog popup=new MessageDialog();
			popup.show(getSupportFragmentManager(),"popup");
		}
		

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

	@Override
	public void onCategorySelected(String category, String subCategory,
			String productName) {
		Log.i(TAG, "category name is " + category);

		HomeMiddleFragment.changeChildFragment(new CategoryViewFragment(),
				category, subCategory, productName);

		homeViewPager.setCurrentItem(MIDDLE_PAGE);

		isCategoryViewSelected = true;
	}

	@Override
	public void onBackPressed() {
		if (isCategoryViewSelected) {
			isCategoryViewSelected = false;
			HomeMiddleFragment.changeChildFragment(new HomeDetailFragment(),
					null,null,null);
			

		} else {
			finish();
		}
	}


	
	private class MessageDialog extends android.support.v4.app.DialogFragment
	{
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			final Dialog dialog=super.onCreateDialog(savedInstanceState);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.message_dialog);
			
			Button btnOK=(Button) dialog.findViewById(R.id.button_ok);
			btnOK.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					homeViewPager.setCurrentItem(LEFT_PAGE);
				}
			});
			
			
			return dialog;
			
		}
	}

}
