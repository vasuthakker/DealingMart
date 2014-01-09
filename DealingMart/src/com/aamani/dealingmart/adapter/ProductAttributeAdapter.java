package com.aamani.dealingmart.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.entities.ProductAttributeEntity;

/**
 * Adapter class for product attribute
 * @author Vasu
 *
 */
public class ProductAttributeAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private List<ProductAttributeEntity> attributeList;
	
	public ProductAttributeAdapter(Context context,
			List<ProductAttributeEntity> attributeList) {
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.attributeList = attributeList;
	}
	
	@Override
	public int getCount() {
		return attributeList.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.product_attribute_item,
					null);
		}
		
		TextView titleTextView = (TextView) convertView
				.findViewById(R.id.attribute_title);
		
		TextView valueTextView = (TextView) convertView
				.findViewById(R.id.attribute_value);
		
		ProductAttributeEntity attribute = attributeList.get(position);
		
		if (attribute != null) {
			titleTextView.setText(attribute.getAttributeTitle());
			valueTextView.setText(attribute.getAttributeValue());
		}
		return convertView;
	}
	
}
