package com.aamani.dealingmart.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.helper.CategoryHelper;
import com.aamani.dealingmart.services.FetchCategoriesService;

public class SplashActivity extends Activity {

	private static final int SPLASH_TIMER = 2000;
	private static final String TAG = "SplashActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		List<CategoryEntity> categories = CategoryHelper.fetchCategories(
				getApplicationContext(), null, null, null, null, null);
		if (categories == null || categories.isEmpty()) {
			Intent intent = new Intent(getApplicationContext(),
					FetchCategoriesService.class);
			startService(intent);
		}

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent homeIntent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(homeIntent);
				finish();
			}
		}, SPLASH_TIMER);
	}

}
