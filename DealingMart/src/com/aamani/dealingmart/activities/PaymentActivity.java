package com.aamani.dealingmart.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DataBaseHelper;
import com.aamani.dealingmart.common.DealingMartConstatns;

/**
 * Activity for payment
 * 
 * @author Vasu
 * 
 */
public class PaymentActivity extends Activity {

	private String sessionId;
	private int shipid;

	private WebView webView;

	private ProgressBar webViewProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		webView = (WebView) findViewById(R.id.payment_webview);

		sessionId = getIntent().getStringExtra(DealingMartConstatns.SESSION_ID);
		shipid = getIntent().getIntExtra(DealingMartConstatns.SHIP_ID, 0);

		webViewProgressBar = (ProgressBar) findViewById(R.id.webview_progressbars);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				if (url.contains("index")) {

					finish();
					DataBaseHelper.truncateOrderTable(getApplicationContext());
				}
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				webViewProgressBar.setVisibility(View.GONE);
			}

		});

		webView.getSettings().setJavaScriptEnabled(true);

		// Session_ID query string parameter
		webView.loadUrl("http://www.dealingmart.com/checkout_step_3.aspx?ship_id="
				+ shipid + "&Session_ID=" + sessionId);

	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
			finish();
		}
	}

}
