package com.aamani.dealingmart.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.Contact;

public class ContactListAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<Integer> selectedItemPos;
	private List<Contact> contactList;
	
	public ContactListAdapter(Context context, List<Contact> contactlist,
			List<Integer> selectedItemPos) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.contactList = contactlist;
		this.selectedItemPos = selectedItemPos;
		
	}
	
	@Override
	public int getCount() {
		return contactList.size();
	}
	
	@Override
	public Object getItem(int position) {
		return position;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertview, ViewGroup parent) {
		if (convertview == null) {
			convertview = inflater.inflate(R.layout.contact_list_item, null);
		}
		
		TextView contactTextView = (TextView) convertview
				.findViewById(R.id.textcontact_item_name_textview);
		
		CheckBox contactCheckBox = (CheckBox) convertview
				.findViewById(R.id.contact_item_checkbox);
		
		TextView detailTextView = (TextView) convertview
				.findViewById(R.id.contact_detail_textview);
		
		contactCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							if (!selectedItemPos.contains(position)) {
								selectedItemPos.add(position);
							}
						}
						else {
							if (selectedItemPos.contains(position)) {
								selectedItemPos.remove((Integer) position);
							}
						}
					}
				});
		
		if (selectedItemPos.contains(position)) {
			contactCheckBox.setChecked(true);
		}
		else {
			contactCheckBox.setChecked(false);
		}
		
		Contact contact = contactList.get(position);
		if (contact != null) {
			
			contactTextView.setText(contact.getName());
			
			String detailToDisplay = null;
			if (contact.getNumber() != null && !contact.getNumber().isEmpty()) {
				detailToDisplay = contact.getNumber();
			}
			else {
				detailToDisplay = contact.getEmail();
			}
			detailTextView.setText(detailToDisplay);
		}
		
		return convertview;
	}
	
}
