package com.aamani.dealingmart.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.utility.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * An adpater for showing the category
 * 
 * @author Vasu
 * 
 */
public class CategoryProductListAdapter extends BaseAdapter {

	private static final int LAYOUT_HEIGHT = 150;
	private Activity activity;
	private LayoutInflater inflater;
	private List<Integer> expandedPosList;
	private List<ProductEntity> productList;
	private ImageLoader imageLoader;

	public CategoryProductListAdapter(Activity activity,
			List<ProductEntity> productList, ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		this.activity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		expandedPosList = new ArrayList<Integer>();
		this.productList = productList;
	}

	@Override
	public int getCount() {
		return 1;
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

		// if (position == 0) {
		productGridView.setVisibility(View.VISIBLE);
		productGridView.setAdapter(new ProductAdapter(activity, productList,
				imageLoader));

		int gridViewHeight = (int) productList.size() / 3;

		if (productList.size() % 3 > 0) {
			gridViewHeight++;
		}

		productGridView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, Utils.dpToPx(activity,
						(LAYOUT_HEIGHT * gridViewHeight) + 10)));
		plusTextView.setText("-");
		if (!expandedPosList.contains(position)) {
			expandedPosList.add(position);
		}

		if (expandedPosList.contains(position)) {
			productGridView.setVisibility(View.VISIBLE);
		} else {
			productGridView.setVisibility(View.GONE);
		}

		return view;
	}
}
