package com.aamani.dealingmart.fragments;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.utility.Utils;

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

	private String category;
	private String subCategory;
	private List<ProductEntity> products;

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

		categoryBannerImageview.setImageResource(R.drawable.banner_1);

		// getting title from bundle
		Bundle bundle = getArguments();
		if (bundle != null) {
			category = bundle.getString(DealingMartConstatns.CATEGORY_TITLE);
			subCategory = bundle
					.getString(DealingMartConstatns.CATEGORY_SUBCATEGORY);
			if (category != null) {
				titleTextView.setText(category);
			}
		}

		// List<ProductEntity> products = Utils.getProducts("Electronics",
		// "Mobile");
		// categoryListView.setAdapter(new CategoryProductListAdapter(
		// getActivity(), products));

		if (products == null || products.isEmpty()) {
			new LoadProductTask().execute(category, subCategory);
		}

	}

	private class LoadProductTask extends AsyncTask<String, Void, Void> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getActivity());
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setMessage("Loading");
			dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			String category = params[0];
			String subCategory = params[1];
			if (category != null && subCategory != null) {
				products = Utils.getProducts(category, subCategory);
			}
			
			//products = Utils.getProducts("Electronics", "Mobile");
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (products != null && !products.isEmpty()) {
				Utils.setStrictPolicy();
				categoryListView.setAdapter(new CategoryProductListAdapter(
						getActivity(), products));
			}
		}

	}

}
