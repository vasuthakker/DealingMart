package com.aamani.dealingmart.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.ContactListAdapter;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.Contact;
import com.aamani.dealingmart.helper.OSContactHelper;
import com.aamani.dealingmart.utility.Utils;

public class DisplayContactActivity extends Activity {
	
	private static final CharSequence SEND_EMAIL = "Send Mail";
	private static final String TAG = "DisplayContactActivity";
	private ListView contactListView;
	private boolean isNumberOnly;
	private boolean isEmailOnly;
	private Button sendButton;
	
	private LinearLayout loadingLayout;
	
	private List<Integer> selectedContactList = new ArrayList<Integer>();
	
	private List<Contact> osContactList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact);
		
		contactListView = (ListView) findViewById(R.id.contact_listview);
		sendButton = (Button) findViewById(R.id.contacts_send_button);
		
		loadingLayout = (LinearLayout) findViewById(R.id.contact_loading_layout);
		
		// getting values from an intent
		isNumberOnly = getIntent().getBooleanExtra(
				DealingMartConstatns.ONLY_PHONE_NUMBER, false);
		isEmailOnly = getIntent().getBooleanExtra(
				DealingMartConstatns.ONLY_EMAIL, false);
		if (isNumberOnly || isEmailOnly) {
			new ContactLoaderAsyncTask().execute(isNumberOnly, isEmailOnly);
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	// Method to send message
	private void sendMessage(List<Contact> selectedContactList) {
		
		StringBuilder numbers;
		if (selectedContactList != null && !selectedContactList.isEmpty()) {
			numbers = new StringBuilder();
			numbers.append(selectedContactList.get(0).getNumber());
			for (int i = 1; i < selectedContactList.size(); i++) {
				numbers.append(";" + selectedContactList.get(i).getNumber());
			}
			
			Uri sendSmsTo = Uri.parse("smsto:" + numbers.toString());
			Intent intent = new Intent(android.content.Intent.ACTION_SENDTO,
					sendSmsTo);
			intent.putExtra("sms_body", "hello");
			startActivity(intent);
		}
	}
	
	// method to send mail
	private void sendMail(List<Contact> selectedContactList) {
		try {
			if (selectedContactList != null && !selectedContactList.isEmpty()) {
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						getString(R.string.email_subject));
				
				int size = selectedContactList.size();
				
				String[] nameArray = new String[size];
				
				for (int i = 0; i < size; i++) {
					nameArray[i] = selectedContactList.get(i).getEmail();
				}
				
				shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
						nameArray);
				
				shareIntent
						.putExtra(android.content.Intent.EXTRA_TEXT, "hello");
				
				shareIntent.setType("message/rfc822");
				
				startActivity(Intent.createChooser(shareIntent, SEND_EMAIL));
			}
		}
		catch (ArrayStoreException e) {
			Log.e(TAG, "ArrayStoreException", e);
		}
	}
	
	private class ContactLoaderAsyncTask extends AsyncTask<Boolean, Void, Void> {
		private List<Contact> osContactList;
		
		@Override
		public void onPreExecute() {
			loadingLayout.setVisibility(View.VISIBLE);
			sendButton.setEnabled(false);
		}
		
		@Override
		protected Void doInBackground(Boolean... params) {
			boolean isNumberOnly = params[0];
			boolean isEmailOnly = params[1];
			osContactList = OSContactHelper.getContacts(
					getApplicationContext(), isNumberOnly, isEmailOnly);
			return null;
		}
		
		@Override
		public void onPostExecute(Void param) {
			loadingLayout.setVisibility(View.GONE);
			sendButton.setEnabled(true);
			if (osContactList != null && !osContactList.isEmpty()) {
				contactListView.setAdapter(new ContactListAdapter(
						getApplicationContext(), osContactList,
						selectedContactList));
			}
			
			sendButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					List<Contact> sendList;
					if (selectedContactList != null
							&& !selectedContactList.isEmpty()
							&& osContactList != null
							&& !osContactList.isEmpty()) {
						
						sendList = new ArrayList<Contact>();
						
						for (int i = 0; i < selectedContactList.size(); i++) {
							sendList.add(osContactList.get(selectedContactList
									.get(i)));
						}
						
						if (isNumberOnly) {
							sendMessage(sendList);
						}
						else {
							sendMail(sendList);
						}
						
						Utils.setPreference(getApplicationContext(), DealingMartConstatns.APP_SHARED, "T");
					}
					else {
						Toast.makeText(getApplicationContext(),
								"Please select atleast one contact",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		
	}
	
}
