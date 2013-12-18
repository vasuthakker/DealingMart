package com.aamani.dealingmart.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.CategoryListAdapter;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.helper.CategoryHelper;
import com.aamani.dealingmart.helper.SubCategoryHelper;

public class HomeLeftFragment extends Fragment {


	private ListView categoryListView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_home_left_layout, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();
		// UI initialization
		categoryListView = (ListView) getActivity().findViewById(
				R.id.category_listview);

		List<CategoryEntity> categories = CategoryHelper.fetchCategories(
				getActivity(), null, null, null, null, null);

		List<List<SubCategoryEntity>> subCategortyChildList = new ArrayList<List<SubCategoryEntity>>();

		for (CategoryEntity category : categories) {
			List<SubCategoryEntity> subCategories = SubCategoryHelper
					.fetchSubCategories(getActivity(),
							SubCategoryHelper.KEY_CATEGORY_ID + "=?",
							new String[] { String.valueOf(category.getId()) },
							null, null, null);
			Log.i("left", "sub category" + subCategories.size());
			subCategortyChildList.add(subCategories);

		}

		categoryListView.setAdapter(new CategoryListAdapter(getActivity(),
				categories, subCategortyChildList));

	}

}
