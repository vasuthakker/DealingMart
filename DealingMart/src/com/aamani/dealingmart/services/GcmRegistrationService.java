package com.aamani.dealingmart.services;

import java.io.IOException;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.aamani.dealingmart.utility.Utils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Service for gcm registration service
 * @author Vasu
 *
 */
public class GcmRegistrationService extends IntentService {

	private static final String TAG = "GcmRegistrationService";

	public GcmRegistrationService() {
		super("GcmRegistrationService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			GoogleCloudMessaging gcm = GoogleCloudMessaging
					.getInstance(getApplicationContext());
			String gcmId = gcm.register(DealingMartConstatns.GCM_SENDER_ID);
			String googleAccount = Utils
					.getGoogleAccount(getApplicationContext());
			
			Log.i(TAG,"gcmid--------------------------->"+gcmId);

			if (googleAccount != null && !googleAccount.isEmpty()
					&& gcmId != null && !gcmId.isEmpty()) {
				int responseId = WebServiceHelper
						.registerDeviceForPushNotification(
								getApplicationContext(), googleAccount, gcmId);
				
				Log.i(TAG,"responseId------------------->"+responseId);
				if (responseId > 0) {
					Utils.setPreference(getApplicationContext(),
							DealingMartConstatns.GCM_ID, gcmId);
				}
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		}

	}

}
