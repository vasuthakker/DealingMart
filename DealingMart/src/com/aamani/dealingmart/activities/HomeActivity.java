package com.aamani.dealingmart.activities;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.fragments.CategoryViewFragment;
import com.aamani.dealingmart.fragments.HomeDetailFragment;
import com.aamani.dealingmart.fragments.HomeLeftFragment;
import com.aamani.dealingmart.fragments.HomeMiddleFragment;
import com.aamani.dealingmart.interfaces.OnCategoryItemSelected;
import com.aamani.dealingmart.utility.Utils;
import com.google.android.gms.internal.di;

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
	
	private static final String SHARE_PRODUCT_TITLE = "Share Product";
	private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
	private static final String WHATSAPP_PACKAGE = "com.whatsapp";
	private static final String TWITTER_PACKAGE = "com.twitter.android";
	
	private static FragmentManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		manager = getSupportFragmentManager();
		
		homeViewPager = (ViewPager) findViewById(R.id.home_viewpager);
		
		homeViewPager.setAdapter(new HomePager(getSupportFragmentManager()));
		
		homeViewPager.setCurrentItem(MIDDLE_PAGE);
		
		homeBaseLayout = (RelativeLayout) findViewById(R.id.home_base_layout);
		
		fragmentManager = getSupportFragmentManager();
		String isAppShared = Utils.getPreference(getApplicationContext(),
				DealingMartConstatns.APP_SHARED);
		if (isAppShared == null || isAppShared.isEmpty()) {
			MessageDialog popup = new MessageDialog();
			popup.show(manager, "popup");
		}
		
	}
	
	public static void showAppInfoShareDialog() {
		MessageDialog popup = new HomeActivity().new MessageDialog();
		popup.show(manager, "popup");
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
		}
		else {
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
					null, null, null);
			
		}
		else {
			finish();
		}
	}
	
	private class MessageDialog extends android.support.v4.app.DialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = super.onCreateDialog(savedInstanceState);
			
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.message_dialog);
			
			Button btnOK = (Button) dialog.findViewById(R.id.button_ok);
			Button btnCancel = (Button) dialog.findViewById(R.id.button_cancel);
			
			btnOK.setText("Send Message");
			btnCancel.setText("Send Mail");
			
			LinearLayout socialLayout = (LinearLayout) dialog
					.findViewById(R.id.dialog_social_layout);
			
			socialLayout.setVisibility(View.VISIBLE);
			
			RelativeLayout facebookLayout = (RelativeLayout) dialog
					.findViewById(R.id.dialog_facebook_layout);
			RelativeLayout twitterLayout = (RelativeLayout) dialog
					.findViewById(R.id.dialog_twitter_layout);
			RelativeLayout whatsappLayout = (RelativeLayout) dialog
					.findViewById(R.id.dialog_whatsapp_layout);
			
			btnOK.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent intent = new Intent(getActivity(),
							DisplayContactActivity.class);
					intent.putExtra(DealingMartConstatns.ONLY_PHONE_NUMBER,
							true);
					startActivity(intent);
				}
			});
			
			btnCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent intent = new Intent(getActivity(),
							DisplayContactActivity.class);
					intent.putExtra(DealingMartConstatns.ONLY_EMAIL, true);
					startActivity(intent);
				}
			});
			
			facebookLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent facebookIntent = getPackageManager()
							.getLaunchIntentForPackage(FACEBOOK_PACKAGE);
					if (facebookIntent != null) {
						facebookIntent = new Intent(Intent.ACTION_SEND);
						facebookIntent.setType("text/plain");
						facebookIntent.setPackage(FACEBOOK_PACKAGE);
						if (facebookIntent != null) {
							facebookIntent.putExtra(Intent.EXTRA_TEXT,
									getString(R.string.share_message));//
							startActivity(Intent.createChooser(facebookIntent,
									SHARE_PRODUCT_TITLE));
						}
					}
					else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.facebook_not_installed),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			twitterLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent twitterIntent = getPackageManager()
							.getLaunchIntentForPackage(TWITTER_PACKAGE);
					if (twitterIntent != null) {
						twitterIntent = new Intent(Intent.ACTION_SEND);
						twitterIntent.setType("text/plain");
						twitterIntent.setPackage(TWITTER_PACKAGE);
						if (twitterIntent != null) {
							twitterIntent.putExtra(Intent.EXTRA_TEXT,
									getString(R.string.share_message));//
							startActivity(Intent.createChooser(twitterIntent,
									SHARE_PRODUCT_TITLE));
						}
					}
					else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.twitter_not_installed),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			whatsappLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent whatsappIntent = getPackageManager()
							.getLaunchIntentForPackage(WHATSAPP_PACKAGE);
					if (whatsappIntent != null) {
						whatsappIntent = new Intent(Intent.ACTION_SEND);
						whatsappIntent.setType("text/plain");
						whatsappIntent.setPackage(WHATSAPP_PACKAGE);
						
						whatsappIntent.putExtra(Intent.EXTRA_TEXT,
								getString(R.string.share_message));//
						startActivity(Intent.createChooser(whatsappIntent,
								SHARE_PRODUCT_TITLE));
					}
					else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.whatsapp_not_installed),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			return dialog;
			
		}
	}
	
}
