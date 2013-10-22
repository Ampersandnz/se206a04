package mlo450.se206.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactsDatasource {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_FIRST_NAME, 
		  DatabaseHelper.COLUMN_LAST_NAME, DatabaseHelper.COLUMN_MOBILE_PHONE, DatabaseHelper.COLUMN_HOME_PHONE, 
		  DatabaseHelper.COLUMN_WORK_PHONE, DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_ADDRESS, 
		  DatabaseHelper.COLUMN_DATE_OF_BIRTH, DatabaseHelper.COLUMN_IMAGE_PATH };

	public ContactsDatasource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Contact createContact(String firstName, String lastName, String mobilePhone, String homePhone, 
			String workPhone, String email, String address, String dateOfBirth, String imagePath) { 

		if (firstName.equals(null)) { firstName = " "; }
		if (lastName.equals(null)) { lastName = " "; }
		if (mobilePhone.equals(null)) { mobilePhone = " "; }
		if (homePhone.equals(null)) { homePhone = " "; }
		if (workPhone.equals(null)) { workPhone = " "; }
		if (email.equals(null)) { email = " "; }
		if (address.equals(null)) { address = " "; }
		if (dateOfBirth.equals(null)) { dateOfBirth = " "; }
		if (imagePath.equals(null)) { imagePath = " "; }
		
		ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COLUMN_FIRST_NAME, firstName);
	    values.put(DatabaseHelper.COLUMN_LAST_NAME, lastName);
	    values.put(DatabaseHelper.COLUMN_MOBILE_PHONE, mobilePhone);
	    values.put(DatabaseHelper.COLUMN_HOME_PHONE, homePhone);
	    values.put(DatabaseHelper.COLUMN_WORK_PHONE, workPhone);
	    values.put(DatabaseHelper.COLUMN_EMAIL, email);
	    values.put(DatabaseHelper.COLUMN_ADDRESS, address);
	    values.put(DatabaseHelper.COLUMN_DATE_OF_BIRTH, dateOfBirth);
	    values.put(DatabaseHelper.COLUMN_IMAGE_PATH, imagePath);
	    
	    long insertId = database.insert(DatabaseHelper.TABLE_COMMENTS, null, values);
	    Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS, allColumns, 
	    		DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
	    cursor.moveToFirst();
	    Contact newContact = cursorToContact(cursor);
	    cursor.close();
	    
	    return newContact;
	}

	public void deleteContact(Contact contact) {
		long id = contact.getId();
		System.out.println("Contact deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_COMMENTS, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Contact> getAllContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			Contact contact = cursorToContact(cursor);
			contacts.add(contact);
			cursor.moveToNext();
		}
		
		cursor.close();
		return contacts;
	}

	private Contact cursorToContact(Cursor cursor) {
	    Contact contact = new Contact();
	    
	    contact.setId(cursor.getLong(0));
	    contact.setFirstName(cursor.getString(1));
	    contact.setLastName(cursor.getString(2));
	    contact.setMobilePhone(cursor.getString(3));
	    contact.setHomePhone(cursor.getString(4));
	    contact.setWorkPhone(cursor.getString(5));
	    contact.setEmail(cursor.getString(6));
	    contact.setAddress(cursor.getString(7));
	    contact.setDateOfBirth(cursor.getString(8));
	    contact.setImagePath(cursor.getString(9));
	    
	    return contact;
	}
} 