package com.aamani.dealingmart.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.aamani.dealingmart.R;

public class SplashActivity extends Activity {

	private static final int SPLASH_TIMER = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		//List<ProductEntity> products=Utils.getProducts("Electronics","Mobile");
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
