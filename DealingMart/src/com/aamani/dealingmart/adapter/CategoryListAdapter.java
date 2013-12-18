package com.aamani.dealingmart.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.interfaces.OnCategoryItemSelected;
import com.aamani.dealingmart.utility.Utils;

public class CategoryListAdapter extends BaseAdapter {

	private Activity activity;
	private List<CategoryEntity> categories;
	private List<List<SubCategoryEntity>> subcategories;
	private LayoutInflater inflater;
	private OnCategoryItemSelected onCategorySelectedListener;;

	public CategoryListAdapter(Activity activity,
			List<CategoryEntity> categories,
			List<List<SubCategoryEntity>> subcategories) {

		onCategorySelectedListener = (OnCategoryItemSelected) activity;
		this.activity = activity;
		this.categories = categories;
		this.subcategories = subcategories;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return categories.size();
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
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.category_list_item, null);
		}

		TextView categoryTitleTextView = (TextView) convertView
				.findViewById(R.id.category_item_blue_textview);
		ListView subCategoryListView = (ListView) convertView
				.findViewById(R.id.category_item_sub_category_listview);

		final List<SubCategoryEntity> subCategory = subcategories.get(position);
		final CategoryEntity category = categories.get(position);

		if (category != null && subCategory != null) {
			categoryTitleTextView.setText(category.getCategory());
			subCategoryListView.setAdapter(new ArrayAdapter<SubCategoryEntity>(
					activity, R.layout.subcategory_textview_item, subCategory));
			subCategoryListView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, Utils.dpToPx(activity,
							40 * subCategory.size())));

			subCategoryListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View view,
								int position, long id) {
							onCategorySelectedListener.onCategorySelected(
									category.getCategory(),
									subCategory.get(position).getSubCategory(),null);

						}
					});

		}
		return convertView;
	}

}
