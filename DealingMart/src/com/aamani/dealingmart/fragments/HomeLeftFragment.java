package com.aamani.dealingmart.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.activities.ContactUsActivity;
import com.aamani.dealingmart.activities.DisplayContactActivity;
import com.aamani.dealingmart.activities.HomeActivity;
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
	private RelativeLayout contactUsLayout;
	private RelativeLayout shareAppInfoLayout;
	
	private static final String SHARE_PRODUCT_TITLE = "Share Product";
	private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
	private static final String WHATSAPP_PACKAGE = "com.whatsapp";
	private static final String TWITTER_PACKAGE = "com.twitter.android";
	
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
		
		shareAppInfoLayout = (RelativeLayout) getActivity().findViewById(
				R.id.share_application_info_layout);
		
		contactUsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.contact_us_layout);
		
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
		
		shareAppInfoLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment dialog = new MessageDialog();
				dialog.show(getChildFragmentManager(), "pop");
			}
		});
		
		contactUsLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						ContactUsActivity.class);
				startActivity(intent);
				
			}
		});
		
	}
	
	private class MessageDialog extends android.support.v4.app.DialogFragment {
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = super.onCreateDialog(savedInstanceState);
			
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getActivity().getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.setContentView(R.layout.message_dialog);
			
			Button btnOK = (Button) dialog.findViewById(R.id.button_ok);
			Button btnCancel = (Button) dialog.findViewById(R.id.button_cancel);
			
			btnOK.setText("Send Messages");
			btnCancel.setText("Send Mails");
			
			LinearLayout socialLayout = (LinearLayout) dialog
					.findViewById(R.id.dialog_social_layout);
			
			socialLayout.setVisibility(View.VISIBLE);
			
			RelativeLayout facebookLayout = (RelativeLayout) dialog
					.findViewById(R.id.dialog_facebook_layout);
			RelativeLayout twitterLayout = (RelativeLayout) dialog
					.findViewById(R.id.dialog_twitter_layout);
			RelativeLayout whatsappLayout = (RelativeLayout) dialog
					.findViewById(R.id.dialog_whatsapp_layout);
			
			btnOK.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent intent = new Intent(getActivity(),
							DisplayContactActivity.class);
					intent.putExtra(DealingMartConstatns.ONLY_PHONE_NUMBER,
							true);
					startActivity(intent);
				}
			});
			
			btnCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					Intent intent = new Intent(getActivity(),
							DisplayContactActivity.class);
					intent.putExtra(DealingMartConstatns.ONLY_EMAIL, true);
					startActivity(intent);
				}
			});
			
			facebookLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent facebookIntent = getActivity().getPackageManager()
							.getLaunchIntentForPackage(FACEBOOK_PACKAGE);
					if (facebookIntent != null) {
						facebookIntent = new Intent(Intent.ACTION_SEND);
						facebookIntent.setType("text/plain");
						facebookIntent.setPackage(FACEBOOK_PACKAGE);
						if (facebookIntent != null) {
							facebookIntent.putExtra(Intent.EXTRA_TEXT,
									getString(R.string.share_message));//
							startActivity(Intent.createChooser(facebookIntent,
									SHARE_PRODUCT_TITLE));
						}
					}
					else {
						Toast.makeText(getActivity(),
								getString(R.string.facebook_not_installed),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			twitterLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent twitterIntent = getActivity().getPackageManager()
							.getLaunchIntentForPackage(TWITTER_PACKAGE);
					if (twitterIntent != null) {
						twitterIntent = new Intent(Intent.ACTION_SEND);
						twitterIntent.setType("text/plain");
						twitterIntent.setPackage(TWITTER_PACKAGE);
						if (twitterIntent != null) {
							twitterIntent.putExtra(Intent.EXTRA_TEXT,
									getString(R.string.share_message));//
							startActivity(Intent.createChooser(twitterIntent,
									SHARE_PRODUCT_TITLE));
						}
					}
					else {
						Toast.makeText(getActivity(),
								getString(R.string.twitter_not_installed),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			whatsappLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent whatsappIntent = getActivity().getPackageManager()
							.getLaunchIntentForPackage(WHATSAPP_PACKAGE);
					if (whatsappIntent != null) {
						whatsappIntent = new Intent(Intent.ACTION_SEND);
						whatsappIntent.setType("text/plain");
						whatsappIntent.setPackage(WHATSAPP_PACKAGE);
						
						whatsappIntent.putExtra(Intent.EXTRA_TEXT,
								getString(R.string.share_message));//
						startActivity(Intent.createChooser(whatsappIntent,
								SHARE_PRODUCT_TITLE));
					}
					else {
						Toast.makeText(getActivity(),
								getString(R.string.whatsapp_not_installed),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			return dialog;
			
		}
	}
	
}
