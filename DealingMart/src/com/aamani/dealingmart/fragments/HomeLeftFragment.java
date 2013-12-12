package com.aamani.dealingmart.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.interfaces.OnCategoryItemSelected;

public class HomeLeftFragment extends Fragment {

	private OnCategoryItemSelected onCategorySelectedListener;

	private RelativeLayout beautyAndCosmeticsLayout;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onCategorySelectedListener = (OnCategoryItemSelected) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_home_left_layout, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();
		// UI intialization
		beautyAndCosmeticsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_beauty_and_cosmetics_layout);

		beautyAndCosmeticsLayout.setOnClickListener(new OnCategoryClick(
				"Mobiles"));
	}

	private class OnCategoryClick implements OnClickListener {

		private String category;

		OnCategoryClick(String category) {
			this.category = category;
		}

		@Override
		public void onClick(View v) {
			onCategorySelectedListener.onCategorySelected(category);
		}

	}
}
