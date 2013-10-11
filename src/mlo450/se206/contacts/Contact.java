package mlo450.se206.contacts;

import java.util.Comparator;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
/*
 * A Contact object. Has the fields specified in the project requirements and implements Parcelable
 * so it can be passed from one activity to another.
 */
public class Contact implements Parcelable {

	private String _id;
	private String _firstName;
	private String _lastName;
	private String _mobilePhone;
	private String _homePhone;
	private String _workPhone;
	private String _email;
	private String _address;
	private String _dateOfBirth;
	private Drawable _image;
	
	Contact() {
	}	
	
	Contact(Parcel parcel) {
		_id = parcel.readString();
		_firstName = parcel.readString();
		_lastName = parcel.readString();
		_mobilePhone = parcel.readString();
		_homePhone = parcel.readString();
		_workPhone = parcel.readString();
		_email = parcel.readString();
		_address = parcel.readString();
		_dateOfBirth = parcel.readString();
	}
	
	public String getId() {
		return _id;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public String getLastName() {
		return _lastName;
	}
	
	public String getMobilePhone() {
		return _mobilePhone;
	}
	
	public String getHomePhone() {
		return _homePhone;
	}
	
	public String getWorkPhone() {
		return _workPhone;
	}
	
	public String getEmail() {
		return _email;
	}
	
	public String getAddress() {
		return _address;
	}
	
	public String getDateOfBirth() {
		return _dateOfBirth;
	}
	
	public Drawable getImage() {
		return _image;
	}
	
	class ContactFirstNameComparator implements Comparator<Contact> {
	    public int compare(Contact contact1, Contact contact2) {
	        return contact1.getFirstName().compareTo(contact2.getFirstName());
	    }
	}
	
	class ContactLastNameComparator implements Comparator<Contact> {
	    public int compare(Contact contact1, Contact contact2) {
	        return contact1.getLastName().compareTo(contact2.getLastName());
	    }
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(_id);
		parcel.writeString(_firstName);
		parcel.writeString(_lastName);
		parcel.writeString(_mobilePhone);
		parcel.writeString(_homePhone);
		parcel.writeString(_workPhone);
		parcel.writeString(_email);
		parcel.writeString(_address);
		parcel.writeString(_dateOfBirth);
	}
	
	public static final Parcelable.Creator<Contact> CREATOR = new Creator<Contact>() {

	    public Contact createFromParcel(Parcel source) {
	    	
	        return new Contact(source);
	        
	    }

		@Override
		public Contact[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Contact[size];
		}
	};
}
