package com.aamani.dealingmart.activities;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.helper.CategoryHelper;
import com.aamani.dealingmart.helper.OSContactHelper;
import com.aamani.dealingmart.helper.SubCategoryHelper;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.aamani.dealingmart.services.GcmRegistrationService;
import com.aamani.dealingmart.utility.Utils;
import com.crashlytics.android.Crashlytics;

/**
 * Splash Activity
 * 
 * @author Vasu
 * 
 */
public class SplashActivity extends Activity {
	
	private static final int SPLASH_TIMER = 2000;
	private static final String TAG = "SplashActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Crashlytics.start(this);
		setContentView(R.layout.activity_splash);
		
		String gcmId = Utils.getPreference(getApplicationContext(),
				DealingMartConstatns.GCM_ID);
		if (gcmId == null || gcmId.isEmpty()) {
			Intent gcmIntent = new Intent(getApplicationContext(),
					GcmRegistrationService.class);
			startService(gcmIntent);
		}
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				List<CategoryEntity> categories = CategoryHelper
						.fetchCategories(getApplicationContext(), null, null,
								null, null, null);
				if (categories == null || categories.isEmpty()) {
					new GetCategoryFromServerAsyncTask().execute();
					
				}
				else {
					Intent homeIntent = new Intent(getApplicationContext(),
							HomeActivity.class);
					startActivity(homeIntent);
					finish();
				}
			}
		}, SPLASH_TIMER);
	}
	
	private class GetCategoryFromServerAsyncTask extends
			AsyncTask<Void, Void, Void> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SplashActivity.this);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setMessage(getString(R.string.loading_cateogry_progress_message));
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			List<CategoryEntity> categories = WebServiceHelper
					.getCategory(getApplicationContext());
			
			Iterator<CategoryEntity> categoryIterator = categories.iterator();
			
			while (categoryIterator.hasNext()) {
				CategoryEntity category = categoryIterator.next();
				List<SubCategoryEntity> subCategories = WebServiceHelper
						.getSubCategory(getApplicationContext(), category);
				if (subCategories == null || subCategories.isEmpty()) {
					categoryIterator.remove();
				}
				else {
					long categoryId = CategoryHelper.insertCategory(
							getApplicationContext(), category, null);
					SubCategoryHelper.insertSubCategories(
							getApplicationContext(), subCategories, categoryId);
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void param) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			
			Intent homeIntent = new Intent(getApplicationContext(),
					HomeActivity.class);
			homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(homeIntent);
			finish();
			
		}
		
	}
	
}
