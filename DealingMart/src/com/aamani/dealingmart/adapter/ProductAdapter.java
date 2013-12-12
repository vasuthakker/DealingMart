package com.aamani.dealingmart.adapter;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.ProductDetailActivity;

public class ProductAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private int itemCount;

	public ProductAdapter(Activity activity, int itemCount) {
		this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.itemCount = itemCount;
	}

	@Override
	public int getCount() {
		return itemCount;
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

		LinearLayout productLayout = (LinearLayout) view
				.findViewById(R.id.product_item_layout);
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

		productLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity,
						ProductDetailActivity.class);
				activity.startActivity(intent);
			}
		});
		return view;
	}

}
