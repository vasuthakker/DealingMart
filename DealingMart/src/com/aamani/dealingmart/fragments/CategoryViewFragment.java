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
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.adapter.CategoryProductListAdapter;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.entities.ProductEntity;
import com.aamani.dealingmart.helper.WebServiceHelper;
import com.aamani.dealingmart.utility.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
	private ImageLoader imageLoader;

	private String productName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_category_view, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

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
			if (subCategory != null) {
				titleTextView.setText(subCategory);
			}
			
			productName = bundle.getString(DealingMartConstatns.PRODUCT_NAME);
			if(productName!=null && subCategory==null)
			{
				titleTextView.setText("Search:"+productName);
			}
		}

		// List<ProductEntity> products = Utils.getProducts("Electronics",
		// "Mobile");
		// categoryListView.setAdapter(new CategoryProductListAdapter(
		// getActivity(), products));

		if (products == null || products.isEmpty()) {
			new LoadProductTask().execute(category, subCategory, productName);
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
			String productName = params[2];

			products = WebServiceHelper.getProducts(category, subCategory,
					productName,0);

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
						getActivity(), products, imageLoader));

			} else {
				Toast.makeText(getActivity(),
						getString(R.string.no_products_found),
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
