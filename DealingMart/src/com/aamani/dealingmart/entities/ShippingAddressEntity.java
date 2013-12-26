package com.aamani.dealingmart.entities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

@SuppressWarnings("serial")
public class ShippingAddressEntity implements KvmSerializable {

	private int id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String city;
	private String state;
	private String pincode;
	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return id;
		case 1:
			return name;
		case 2:
			return phone;
		case 3:
			return email;
		case 4:
			return address;
		case 5:
			return city;
		case 6:
			return state;
		case 7:
			return pincode;
		case 8:
			return country;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 9;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "id";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "name";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "phone";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "email";
			break;
		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "address";
			break;
		case 5:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "city";
			break;
		case 6:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "state";
			break;
		case 7:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "pincode";
			break;
		case 8:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "country";
			break;

		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			id = Integer.valueOf(value.toString());
			break;
		case 1:
			name = value.toString();
			break;
		case 2:
			phone = value.toString();
			break;
		case 3:
			email = value.toString();
			break;
		case 4:
			address = value.toString();
			break;
		case 5:
			city = value.toString();
			break;
		case 6:
			state = value.toString();
			break;
		case 7:
			pincode = value.toString();
			break;
		case 8:
			country = value.toString();
			break;
		}
	}
}
