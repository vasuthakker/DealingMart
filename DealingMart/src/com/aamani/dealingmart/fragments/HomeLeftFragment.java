package com.aamani.dealingmart.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.DisplayContactActivity;
import com.aamani.dealingmart.adapter.CategoryListAdapter;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.CategoryEntity;
import com.aamani.dealingmart.entities.SubCategoryEntity;
import com.aamani.dealingmart.helper.CategoryHelper;
import com.aamani.dealingmart.helper.SubCategoryHelper;

/**
 * Home Left / menu fragment
 * 
 * @author Vasu
 * 
 */
public class HomeLeftFragment extends Fragment {
	
	private ListView categoryListView;
	private RelativeLayout smsLayout;
	private RelativeLayout mailLayout;
	
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
		
		smsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.left_sms_layout);
		mailLayout = (RelativeLayout) getActivity().findViewById(
				R.id.left_mail_layout);
		
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
		
		smsLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(),
						DisplayContactActivity.class);
				intent.putExtra(DealingMartConstatns.ONLY_PHONE_NUMBER, true);
				startActivity(intent);
			}
		});
		
		mailLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(),
						DisplayContactActivity.class);
				intent.putExtra(DealingMartConstatns.ONLY_EMAIL, true);
				startActivity(intent);
			}
		});
		
	}
	
}
