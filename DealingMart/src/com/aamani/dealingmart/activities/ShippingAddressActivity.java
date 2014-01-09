package com.aamani.dealingmart.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ShippingAddressEntity;
import com.aamani.dealingmart.helper.WebServiceHelper;

/**
 * Activity for saving the shipping detail
 * 
 * @author Vasu
 * 
 */
public class ShippingAddressActivity extends Activity {
	
	private EditText nameEditText;
	private EditText phoneEditText;
	private EditText emailEditText;
	private EditText addressEditText;
	private EditText cityEditText;
	private EditText stateEdtiText;
	private EditText pincodeEdtiText;
	private Spinner countrySpinner;
	private Button submitButton;
	private String emailId;
	private String[] countryList;
	private String sessionId;
	private int shipId;
	
	private LinearLayout loadingLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shipping_address);
		
		countrySpinner = (Spinner) findViewById(R.id.country_list_spinnner);
		
		nameEditText = (EditText) findViewById(R.id.shipping_name_edittext);
		phoneEditText = (EditText) findViewById(R.id.shipping_phone_edittext);
		emailEditText = (EditText) findViewById(R.id.shipping_email_edittext);
		addressEditText = (EditText) findViewById(R.id.shipping_address_edittext);
		cityEditText = (EditText) findViewById(R.id.shipping_city_edittext);
		stateEdtiText = (EditText) findViewById(R.id.shipping_state_edittext);
		pincodeEdtiText = (EditText) findViewById(R.id.shipping_pincode_edittext);
		submitButton = (Button) findViewById(R.id.shipping_submit_buton);
		loadingLayout = (LinearLayout) findViewById(R.id.shipping_activity_loading_layout);
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				insertShippingDetail();
			}
			
		});
		
		countryList = getResources().getStringArray(R.array.country_list);
		
		// intent data
		emailId = getIntent().getStringExtra(DealingMartConstatns.EMAIL_ID);
		sessionId = getIntent().getStringExtra(DealingMartConstatns.SESSION_ID);
		
		if (emailId != null) {
			new CheckShippingAddressAsync().execute(emailId);
		}
		
	}
	
	private class CheckShippingAddressAsync extends
			AsyncTask<String, Void, ShippingAddressEntity> {
		
		
		@Override
		public void onPreExecute() {
			loadingLayout.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected ShippingAddressEntity doInBackground(String... params) {
			ShippingAddressEntity address = WebServiceHelper
					.checkShippingAddress(getApplicationContext(), params[0]);
			return address;
		}
		
		@Override
		public void onPostExecute(ShippingAddressEntity address) {
			
			loadingLayout.setVisibility(View.GONE);
			if (address == null) {
				Toast.makeText(getApplicationContext(),
						getString(R.string.no_shipping_detail_found),
						Toast.LENGTH_SHORT).show();
			}
			else {
				if (address != null) {
					shipId = address.getId();
					nameEditText.setText(address.getName());
					phoneEditText.setText(address.getPhone());
					emailEditText.setText(address.getEmail());
					addressEditText.setText(address.getAddress());
					stateEdtiText.setText(address.getState());
					cityEditText.setText(address.getCity());
					pincodeEdtiText.setText(address.getPincode());
					int countryPos = 0;
					for (int i = 0; i < countryList.length; i++) {
						if (countryList[i].equalsIgnoreCase(address
								.getCountry())) {
							countryPos = i;
						}
					}
					countrySpinner.setSelection(countryPos);
				}
			}
		}
	}
	
	private boolean checkDetail() {
		String name = nameEditText.getText().toString();
		if (name == null || name.trim().length() == 0) {
			nameEditText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.name_is_null) + "</font>"));
			return false;
		}
		String phone = phoneEditText.getText().toString();
		if (phone == null || phone.trim().length() < 10) {
			phoneEditText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.phone_is_null) + "</font>"));
			return false;
		}
		String email = emailEditText.getText().toString();
		if (email == null || email.trim().length() == 0
				|| !isValidEmailAddress(email)) {
			emailEditText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.email_is_null) + "</font>"));
			return false;
		}
		String address = addressEditText.getText().toString();
		if (address == null || address.trim().length() == 0) {
			addressEditText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.address_is_null) + "</font>"));
			return false;
		}
		String city = cityEditText.getText().toString();
		if (city == null || city.trim().length() == 0) {
			cityEditText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.city_is_null) + "</font>"));
			return false;
		}
		String state = stateEdtiText.getText().toString();
		if (state == null || state.trim().length() == 0) {
			stateEdtiText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.state_is_null) + "</font>"));
			return false;
		}
		
		String pincode = pincodeEdtiText.getText().toString();
		if (pincode == null || pincode.trim().length() == 0) {
			pincodeEdtiText.setError(Html.fromHtml("<font name=white>"
					+ getString(R.string.pincode_is_null) + "</font>"));
			return false;
		}
		return true;
		
	}
	
	private void insertShippingDetail() {
		if (checkDetail()) {
			String name = nameEditText.getText().toString();
			String phone = phoneEditText.getText().toString();
			String address = addressEditText.getText().toString();
			String city = cityEditText.getText().toString();
			String state = stateEdtiText.getText().toString();
			String pincode = pincodeEdtiText.getText().toString();
			String country = countryList[countrySpinner
					.getSelectedItemPosition()];
			String email = emailEditText.getText().toString();
			
			ShippingAddressEntity addressEntity = new ShippingAddressEntity();
			
			addressEntity.setId(shipId);
			addressEntity.setAddress(address);
			addressEntity.setName(name);
			addressEntity.setEmail(email);
			addressEntity.setCity(city);
			addressEntity.setState(state);
			addressEntity.setPincode(pincode);
			addressEntity.setCountry(country);
			addressEntity.setPhone(phone);
			
			new AsyncPushShippingDetail().execute(addressEntity);
			
		}
		else {
			Toast.makeText(getApplicationContext(), "Please check your inputs",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	private class AsyncPushShippingDetail extends
			AsyncTask<ShippingAddressEntity, Void, Integer> {
		
		@Override
		public void onPreExecute() {
		}
		
		@Override
		protected Integer doInBackground(ShippingAddressEntity... params) {
			return WebServiceHelper.insertShippingAddress(
					getApplicationContext(), params[0]);
		}
		
		@Override
		public void onPostExecute(Integer result) {
			Intent intent = new Intent(getApplicationContext(),
					OrderSummaryActivity.class);
			intent.putExtra(DealingMartConstatns.EMAIL_ID, emailId);
			intent.putExtra(DealingMartConstatns.SHIP_ID, result);
			intent.putExtra(DealingMartConstatns.SESSION_ID, sessionId);
			startActivity(intent);
		}
	}
	
	private boolean isValidEmailAddress(String email) {
		Pattern p = java.util.regex.Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
