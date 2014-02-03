package com.aamani.dealingmart.activities;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aamani.dealingmart.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactUsActivity extends FragmentActivity {
	
	private GoogleMap map;
	private SupportMapFragment mapFragment;
	private ImageView callImageView;
	
	private static final LatLng ABAD_BRANCH = new LatLng(23.023502, 72.570279);
	private static final LatLng AMRELI_BRANCH = new LatLng(21.600625, 71.217021);
	private static final LatLng MEHSANA_BRANCH = new LatLng(23.579033,
			72.368185);
	private static final LatLng SURAT_BRANCH = new LatLng(21.195215, 72.804323);
	private static final LatLng BHAVNAGAR_BRANCH = new LatLng(21.763884,
			72.147278);
	private static final LatLng RAJKOT_BRANCH = new LatLng(22.292372, 70.799607);
	private static final LatLng NJ_BRANCH = new LatLng(40.697885, -74.941019);
	
	private static final String PHONE_NUMBER = "+918866333000";
	private static final String TAG = "ContactUsActivity";
	
	private static LinearLayout ahmedabadLayout;
	private static LinearLayout amreliLayout;
	private static LinearLayout mehsanaLayout;
	private static LinearLayout suratLayout;
	private static LinearLayout bhavnagarLayout;
	private static LinearLayout njLayout;
	private static LinearLayout rajkotLayout;
	private List<LinearLayout> linearLayoutList;
	
	private static int BLUE_COLOR;
	private static int WHITE_COLOR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_us);
		
		BLUE_COLOR = getResources().getColor(R.color.text_blue);
		WHITE_COLOR = Color.WHITE;
		
		callImageView = (ImageView) findViewById(R.id.call_imageview);
		ahmedabadLayout = (LinearLayout) findViewById(R.id.contact_ahmedabad_branch_layout);
		amreliLayout = (LinearLayout) findViewById(R.id.contact_amreli_branch_layout);
		mehsanaLayout = (LinearLayout) findViewById(R.id.contact_mehsana_branch_layout);
		suratLayout = (LinearLayout) findViewById(R.id.contact_surat_branch_layout);
		bhavnagarLayout = (LinearLayout) findViewById(R.id.contact_bhavnagar_branch_layout);
		njLayout = (LinearLayout) findViewById(R.id.contact_nj_branch_layout);
		rajkotLayout = (LinearLayout) findViewById(R.id.contact_rajkot_branch_layout);
		
		linearLayoutList = new ArrayList<LinearLayout>();
		
		linearLayoutList.add(ahmedabadLayout);
		linearLayoutList.add(amreliLayout);
		linearLayoutList.add(mehsanaLayout);
		linearLayoutList.add(suratLayout);
		linearLayoutList.add(bhavnagarLayout);
		linearLayoutList.add(njLayout);
		linearLayoutList.add(rajkotLayout);
		
		ahmedabadLayout.setOnClickListener(new OnLayoutClick(ABAD_BRANCH,
				ahmedabadLayout));
		amreliLayout.setOnClickListener(new OnLayoutClick(AMRELI_BRANCH,
				amreliLayout));
		mehsanaLayout.setOnClickListener(new OnLayoutClick(MEHSANA_BRANCH,
				mehsanaLayout));
		suratLayout.setOnClickListener(new OnLayoutClick(SURAT_BRANCH,
				suratLayout));
		bhavnagarLayout.setOnClickListener(new OnLayoutClick(BHAVNAGAR_BRANCH,
				bhavnagarLayout));
		njLayout.setOnClickListener(new OnLayoutClick(NJ_BRANCH, njLayout));
		rajkotLayout.setOnClickListener(new OnLayoutClick(RAJKOT_BRANCH,
				rajkotLayout));
		
		try {
			MapsInitializer.initialize(getApplicationContext());
			ViewGroup mapHost = (ViewGroup) findViewById(R.id.map);
			mapHost.requestTransparentRegion(mapHost);
			
			mapFragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			
			if (mapFragment != null) {
				map = mapFragment.getMap();
				
				map.addMarker(new MarkerOptions()
						.position(ABAD_BRANCH)
						.title(getString(R.string.abad_branch))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				map.addMarker(new MarkerOptions()
						.position(AMRELI_BRANCH)
						.title(getString(R.string.amreli_branch))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				map.addMarker(new MarkerOptions()
						.position(MEHSANA_BRANCH)
						.title(getString(R.string.mehsana_branch))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				map.addMarker(new MarkerOptions()
						.position(SURAT_BRANCH)
						.title(getString(R.string.surat_bracnh))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				map.addMarker(new MarkerOptions()
						.position(BHAVNAGAR_BRANCH)
						.title(getString(R.string.bhavnagar_branch))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				map.addMarker(new MarkerOptions()
						.position(NJ_BRANCH)
						.title(getString(R.string.nj_bracnh))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				map.addMarker(new MarkerOptions()
						.position(RAJKOT_BRANCH)
						.title(getString(R.string.rajkot_branch))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker)));
				
				changeCameraZoom(ABAD_BRANCH);
				
			}
			
			callImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));
					startActivity(callIntent);
				}
			});
			
			changeColor(ahmedabadLayout, BLUE_COLOR, WHITE_COLOR);
		}
		catch (GooglePlayServicesNotAvailableException e) {
			Toast.makeText(getApplicationContext(), "maperror",
					Toast.LENGTH_SHORT).show();
		}
		catch (Exception e) {
			Log.e("exception", "Exception", e);
			
		}
		
	}
	
	// Method to change Color
	private void changeColor(LinearLayout layout, int backgroudColor,
			int textColor) {
		layout.setBackgroundColor(backgroudColor);
		for (int i = 0; i < layout.getChildCount(); i++) {
			TextView textView;
			try {
				textView = (TextView) layout.getChildAt(i);
				textView.setTextColor(textColor);
			}
			catch (ClassCastException e) {
				Log.e(TAG, "ClassCastException", e);
			}
		}
	}
	
	// Method to change camera zoom		
	private void changeCameraZoom(LatLng posLong) {
		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(posLong, 15));
		
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
	}
	
	//inner class  to handle layout click
	private class OnLayoutClick implements OnClickListener {
		private LatLng latLong;
		private LinearLayout layout;
		
		OnLayoutClick(LatLng latLong, LinearLayout layout) {
			this.latLong = latLong;
			this.layout = layout;
		}
		
		@Override
		public void onClick(View v) {
			for (LinearLayout layout : linearLayoutList) {
				changeColor(layout, WHITE_COLOR, BLUE_COLOR);
			}
			changeColor(layout, BLUE_COLOR, WHITE_COLOR);
			changeCameraZoom(latLong);
		}
	}
	
}
