package com.aamani.dealingmart.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;

public class CategoryProductListAdapter extends BaseAdapter {

	private Activity activity;
	private LayoutInflater inflater;
	private List<Integer> expandedPosList;

	public CategoryProductListAdapter(Activity activity) {
		this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		expandedPosList = new ArrayList<Integer>();
	}

	@Override
	public int getCount() {
		return 5;
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
	public View getView(final int position, View view, ViewGroup arg2) {
		if (view == null) {
			view = inflater.inflate(R.layout.category_product_item, null);
		}

		RelativeLayout categoryTitleLayout = (RelativeLayout) view
				.findViewById(R.id.category_product_item_title_layout);
		TextView categoryTitleTextView = (TextView) view
				.findViewById(R.id.category_product_item_title_textview);
		final TextView plusTextView = (TextView) view
				.findViewById(R.id.category_product_item_plus_textview);
		final GridView productGridView = (GridView) view
				.findViewById(R.id.category_product_item_gridview);

		categoryTitleTextView.setText("Mobile");
		if (position == 0 || position == 1) {
			productGridView.setVisibility(View.VISIBLE);
			productGridView.setAdapter(new ProductAdapter(
					activity, 3));
			plusTextView.setText("-");
			if (!expandedPosList.contains(position)) {
				expandedPosList.add(position);
			}
		} else {
			productGridView.setVisibility(View.GONE);
		}

		categoryTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (productGridView.getVisibility() == View.GONE) {
					productGridView.setVisibility(View.VISIBLE);
					plusTextView.setText("-");
					productGridView
							.setAdapter(new ProductAdapter(
									activity, 3));
					if (!expandedPosList.contains(position)) {
						expandedPosList.add(position);
					}
				} else {
					productGridView.setVisibility(View.GONE);
					plusTextView.setText("+");
					if (expandedPosList.contains(position)) {
						expandedPosList.remove((Integer) position);
					}
				}
			}
		});

		if (expandedPosList.contains(position)) {
			productGridView.setVisibility(View.VISIBLE);
		} else {
			productGridView.setVisibility(View.GONE);
		}
		return view;
	}
}
