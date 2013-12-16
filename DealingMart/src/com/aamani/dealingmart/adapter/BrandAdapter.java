package com.aamani.dealingmart.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aamani.dealingmart.R;

public class BrandAdapter extends BaseAdapter {

	private int[] brandImageResources = new int[] { R.drawable.brand_1,
			R.drawable.brand_2, R.drawable.brand_3, R.drawable.brand_4,
			R.drawable.brand_5, R.drawable.brand_6, R.drawable.brand_7,
			R.drawable.brand_8, R.drawable.brand_9, R.drawable.brand_10,
			R.drawable.brand_11, R.drawable.brand_12 };

	private Activity activity;

	public BrandAdapter(Activity activity) {

		this.activity = activity;
	}

	@Override
	public int getCount() {
		return brandImageResources.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView brandImage=new ImageView(activity);
		brandImage.setImageResource(brandImageResources[position]);
		return brandImage;
	}

}
