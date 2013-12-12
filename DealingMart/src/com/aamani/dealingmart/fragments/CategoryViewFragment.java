package com.aamani.dealingmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.CategoryProductListAdapter;
import com.aamani.dealingmart.common.DealingMartConstatns;

/**
 * Fragment to display the product according to category
 * 
 * @author Vasu
 * 
 */
public class CategoryViewFragment extends Fragment {

	private ListView categoryListView;
	private ImageView categoryBannerImageview;
	private TextView titleTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_category_view, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();

		categoryBannerImageview = (ImageView) getActivity().findViewById(
				R.id.category_banner_imageview);
		categoryListView = (ListView) getActivity().findViewById(
				R.id.category_productlist_listview);

		titleTextView = (TextView) getActivity().findViewById(
				R.id.category_title_textview);

		categoryListView.setAdapter(new CategoryProductListAdapter(
				getActivity()));

		categoryBannerImageview.setImageResource(R.drawable.banner_1);

		// getting title from bundle
		Bundle bundle = getArguments();
		if (bundle != null) {
			String title = bundle
					.getString(DealingMartConstatns.CATEGORY_TITLE);
			if (title != null) {
				titleTextView.setText(title);
			}
		}

	}
}
