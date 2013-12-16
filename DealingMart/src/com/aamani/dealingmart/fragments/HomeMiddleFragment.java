package com.aamani.dealingmart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.HomeActivity;
import com.aamani.dealingmart.activities.ShoppingCartActivity;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.helper.CartHelper;

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
	private static FragmentManager fragmentManager;

	private static RelativeLayout cartLayout;
	private static TextView cartNumberTextView;

	private RelativeLayout shoppingCartViewLayout;

	// private static boolean firstTime = true;

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
			changeChildFragment(new HomeDetailFragment(), null, null);
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
	}

	public static void changeChildFragment(Fragment fragment, String category,
			String subCategory) {
		Bundle bundle = null;
		if (category != null) {
			bundle = new Bundle();
			bundle.putString(DealingMartConstatns.CATEGORY_TITLE, category);
			bundle.putString(DealingMartConstatns.CATEGORY_SUBCATEGORY,
					subCategory);
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
