package com.aamani.dealingmart.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.aamani.dealingmart.entities.Contact;

/**
 * Helper class to get OS contacts
 * 
 * @author Vasu
 * 
 */
public final class OSContactHelper {
	
	private static final String TAG = "OSContactHelper";
	
	public static List<Contact> getContacts(Context context,
			boolean isNumberRequired, boolean isEmailRequired) {
		
		List<Contact> OSContactList = new ArrayList<Contact>();
		
		Contact contact = null;
		
		ContentResolver cr = context.getContentResolver();
		Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, ContactsContract.Contacts.DISPLAY_NAME
						+ " ASC");
		
		boolean hasPhoneNumber;
		boolean hasEmailAddress;
		if (contactCursor != null && contactCursor.getCount() > 0) {
			while (contactCursor.moveToNext()) {
				
				hasPhoneNumber = false;
				hasEmailAddress = false;
				
				contact = new Contact();
				
				long id = Long.parseLong(contactCursor.getString(contactCursor
						.getColumnIndex(ContactsContract.Contacts._ID)));
				
				// Contact Name
				String name = contactCursor
						.getString(contactCursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				
				contact.setId(id);
				contact.setName(name);
				
				Log.d(TAG, "Name: " + name);
				
				// Phone Number
				if (isNumberRequired
						&& Integer
								.parseInt(contactCursor.getString(contactCursor
										.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					
					Cursor phoneCursor = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?",
							new String[] { String.valueOf(id) }, null);
					while (phoneCursor.moveToNext()) {
						String number = phoneCursor
								.getString(phoneCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						
						if (number != null && !number.isEmpty()
								&& contact.getNumber() == null) {
							contact.setNumber(number);
							hasPhoneNumber = true;
							
							Log.d(TAG, "Number: " + number);
						}
					}
					phoneCursor.close();
				}
				
				// email
				if (isEmailRequired) {
					Cursor emailCur = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?",
							new String[] { String.valueOf(id) }, null);
					while (emailCur.moveToNext()) {
						String email = emailCur
								.getString(emailCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
						int isPrimaryEmail = Integer
								.parseInt(emailCur.getString(emailCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.IS_PRIMARY)));
						
						if (email != null && !email.isEmpty()
								&& contact.getEmail() == null
								&& isPrimaryEmail > 0) {
							contact.setEmail(email);
							hasEmailAddress = true;
							
							Log.d(TAG, "email: " + email);
						}
					}
					emailCur.close();
				}
				
				if (hasEmailAddress || hasPhoneNumber) {
					OSContactList.add(contact);
				}
			}
		}
		
		return OSContactList;
		
	}
	
}
