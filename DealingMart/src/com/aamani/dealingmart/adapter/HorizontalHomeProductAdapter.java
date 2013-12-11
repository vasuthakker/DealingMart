package com.aamani.dealingmart.adapter;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aamani.dealingmart.R;

public class HorizontalHomeProductAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;

	public HorizontalHomeProductAdapter(Activity activity) {
		this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 20;
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
	public View getView(int arg0, View view, ViewGroup arg2) {
		if (view == null) {
			view = inflater.inflate(R.layout.product_horizontal_item, null);
		}

		ImageView productImageView = (ImageView) view
				.findViewById(R.id.hor_product_image);
		ImageView hotProductImageView = (ImageView) view
				.findViewById(R.id.hor_product_star_image);
		TextView offerTextView = (TextView) view
				.findViewById(R.id.hor_offer_textview);
		TextView prizeTextView = (TextView) view
				.findViewById(R.id.hor_porduct_prize_textview);
		TextView productNameTextView = (TextView) view
				.findViewById(R.id.hor_porduct_name_textview);

		prizeTextView.setText("Rs:" + new Random().nextInt(1000));
		productNameTextView.setText("Note 3");
		return view;
	}

}
