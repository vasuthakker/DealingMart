package com.aamani.dealingmart.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aamani.dealingmart.utility.Utils;

/**
 * Async task for loading images asynchronasly 
 * @author Vasu
 *
 */
public class AsycImageLoaderTask extends AsyncTask<String, Void, Void> {

	private ImageView productImageView;
	private Bitmap imageBitmap;
	private ProgressBar loadProgressBar;

	
	public AsycImageLoaderTask(ImageView productImageView, ProgressBar loadProgressBar) {
		this.productImageView = productImageView;
		this.loadProgressBar = loadProgressBar;
	}

	@Override
	protected Void doInBackground(String... params) {
		imageBitmap = Utils.getBitmapFromURL(params[0]);
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
		if (imageBitmap != null && productImageView != null) {
			loadProgressBar.setVisibility(View.GONE);
			productImageView.setImageBitmap(imageBitmap);
		}
	}

}
