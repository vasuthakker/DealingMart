package com.aamani.dealingmart.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.HomeActivity;
import com.aamani.dealingmart.activities.ShoppingCartActivity;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.helper.CartHelper;
import com.aamani.dealingmart.interfaces.OnCategoryItemSelected;

/**
 * Home Middle Fragment to show deal,news
 * 
 * @author Vasu
 * 
 */
public class HomeMiddleFragment extends Fragment {

	private ImageView leftNavigationImageview;

	private static final int LEFT_PAGE = 0;
	private static final int MIDDLE_PAGE = 1;

	private static final String TAG = "HomeMiddleFragment";
	private static FragmentManager fragmentManager;

	private static RelativeLayout cartLayout;
	private static TextView cartNumberTextView;

	private RelativeLayout shoppingCartViewLayout;

	private AutoCompleteTextView searchTextView;

	// private static boolean firstTime = true;

	private OnCategoryItemSelected onCategorySelected;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onCategorySelected = (OnCategoryItemSelected) activity;
		} catch (ClassCastException e) {
			Log.e(TAG, "ClassCastException", e);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater
				.inflate(R.layout.fragment_home_middle, container, false);

	}

	@Override
	public void onStart() {
		super.onStart();

		leftNavigationImageview = (ImageView) getActivity().findViewById(
				R.id.home_navigation_imageview);

		cartLayout = (RelativeLayout) getActivity().findViewById(
				R.id.cart_layout);
		cartNumberTextView = (TextView) getActivity().findViewById(
				R.id.cart_number_textview);
		shoppingCartViewLayout = (RelativeLayout) getActivity().findViewById(
				R.id.shopping_cart_view_layout);
		searchTextView = (AutoCompleteTextView) getActivity().findViewById(
				R.id.search_autocomplete_textview);

		leftNavigationImageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentItem = HomeActivity.ChangePage();
				if (currentItem == MIDDLE_PAGE) {
					leftNavigationImageview.setRotation(180f);
				} else {
					leftNavigationImageview.setRotation(0f);
				}
			}
		});

		fragmentManager = getChildFragmentManager();

		Fragment fragment = fragmentManager
				.findFragmentById(R.id.category_fragment_base_layout);
		if (fragment == null) {
			changeChildFragment(new HomeDetailFragment(), null, null, null);
		}

		shoppingCartViewLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(),
						ShoppingCartActivity.class);
				startActivity(intent);
			}
		});

		int cartProductNumber = CartHelper.fetchProdcutsCount(getActivity());
		updateProductNumber(cartProductNumber);

		searchTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 2) {
					Handler handler = new Handler();
					handler.postDelayed(new SearchProduct(s.toString(), s),
							3000);
				}
			}
		});

	}

	private class SearchProduct implements Runnable {

		String searchString;
		Editable searchEditable;

		public SearchProduct(String searchString, Editable searchEditable) {
			this.searchString = searchString;
			this.searchEditable = searchEditable;
		}

		@Override
		public void run() {
			if (searchEditable.toString().equalsIgnoreCase(searchString)) {
				InputMethodManager manager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(
						searchTextView.getWindowToken(),
						InputMethodManager.RESULT_UNCHANGED_SHOWN);
				onCategorySelected.onCategorySelected(null, null, searchString);
			}
		}

	}

	public static void changeChildFragment(Fragment fragment, String category,
			String subCategory, String productName) {
		Bundle bundle = null;
		if (category != null || productName != null) {
			bundle = new Bundle();
			bundle.putString(DealingMartConstatns.CATEGORY_TITLE, category);
			bundle.putString(DealingMartConstatns.CATEGORY_SUBCATEGORY,
					subCategory);
			bundle.putString(DealingMartConstatns.PRODUCT_NAME, productName);
			fragment.setArguments(bundle);
		}
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.category_fragment_base_layout, fragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	public static void updateProductNumber(int number) {
		if (number > 0) {
			cartLayout.setVisibility(View.VISIBLE);
			cartNumberTextView.setText(String.valueOf(number));
		} else {
			cartLayout.setVisibility(View.GONE);
		}
	}

}
