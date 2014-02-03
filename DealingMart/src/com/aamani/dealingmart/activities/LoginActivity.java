package com.aamani.dealingmart.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.aamani.dealingmart.utility.Utils;

/**
 * Activity for guest / user login
 * 
 * @author Vasu
 * 
 */
public class LoginActivity extends Activity {
	
	private Button guestLoginButton;
	private Button customerLoginButton;
	private EditText customerEmailEditText;
	private EditText customerPasswordEditText;
	private LinearLayout customerLayout;
	private TextView titleTextView;
	private String sessionId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final LinearLayout guestLayout = (LinearLayout) findViewById(R.id.guest_layout);
		
		guestLoginButton = (Button) findViewById(R.id.customer_guest_button);
		customerLoginButton = (Button) findViewById(R.id.customer_login_button);
		
		customerEmailEditText = (EditText) findViewById(R.id.customer_login_emailid_edittext);
		customerPasswordEditText = (EditText) findViewById(R.id.customer_login_password_edittext);
		customerLayout = (LinearLayout) findViewById(R.id.customer_layout);
		titleTextView = (TextView) findViewById(R.id.login_title_textview);
		
		customerEmailEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							guestLayout.setVisibility(View.GONE);
						}
						else {
							guestLayout.setVisibility(View.VISIBLE);
						}
					}
				});
		
		customerPasswordEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {
					
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							guestLayout.setVisibility(View.GONE);
						}
						else {
							guestLayout.setVisibility(View.VISIBLE);
						}
					}
				});
		
		customerLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				guestLayout.setVisibility(View.VISIBLE);
				titleTextView.setText(getString(R.string.customer_login));
				return true;
			}
		});
		
		customerLoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(getApplicationContext(),
				// ShippingAddressActivity.class);
				// startActivity(intent);
				String emailId = customerEmailEditText.getText().toString();
				if (emailId == null || emailId.trim().length() == 0) {
					customerEmailEditText.setError(Html
							.fromHtml("<font color=white>Email Id can't be null</font>"));
					return;
				}
				
				String password = customerPasswordEditText.getText().toString();
				if (password == null || password.trim().length() == 0) {
					customerPasswordEditText.setError(Html
							.fromHtml("<font color=white>password can't be null</font>"));
					return;
				}
				
				new AsycLoginTask(getApplicationContext()).execute(emailId,
						password);
				
			}
		});
		
		// -----------------------generating a session
		// id-------------------------------//
		sessionId = "Mobile." + Utils.getUniquId(getApplicationContext()) + "."
				+ System.currentTimeMillis();
		
		guestLoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				guestLayout.setVisibility(View.GONE);
//				titleTextView.setText("Register");
				Intent in = new Intent(getApplicationContext(),
						ShippingAddressActivity.class);
				in.putExtra(DealingMartConstatns.SESSION_ID, sessionId);
				startActivity(in);
			}
		});
		
	}
	
	private class AsycLoginTask extends AsyncTask<String, Void, Boolean> {
		
		private Context context;
		private ProgressDialog dialog;
		
		@Override
		public void onPreExecute() {
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setMessage("Vaildating credential");
			dialog.show();
		}
		
		public AsycLoginTask(Context context) {
			this.context = context;
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			boolean isValid = false;
			if (params != null && params.length > 0) {
				String userName = params[0];
				String password = params[1];
				
				isValid = WebServiceHelper.authenticate(userName, password);
			}
			return isValid;
		}
		
		@Override
		public void onPostExecute(Boolean result) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (result) {
				Intent intent = new Intent(context,
						ShippingAddressActivity.class);
				intent.putExtra(DealingMartConstatns.EMAIL_ID,
						customerEmailEditText.getText().toString());
				intent.putExtra(DealingMartConstatns.SESSION_ID, sessionId);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				finish();
			}
			else {
				Toast.makeText(context,
						context.getString(R.string.credential_invalid),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}
