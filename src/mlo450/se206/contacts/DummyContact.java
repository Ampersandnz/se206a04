package mlo450.se206.contacts;

import java.util.Comparator;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
/*
 * A dummy Contact, with all fields initialised to a default value. 
 * All instances in the code will be replaced by Contacts in the final project.
 */
public class DummyContact extends Contact{
	
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
	
	DummyContact() {
	}
	
	DummyContact(String id) {
		_id = id;
		_firstName = "Michael";
		_lastName = "Lo";
		_mobilePhone = "0221837240";
		_homePhone = "8772159";
		_workPhone = "None";
		_email = "mlo450@aucklanduni.ac.nz";
		_address = "729/21 Whitaker Place";
		_dateOfBirth = "03/08/1993";
	}
	
	DummyContact(Parcel parcel) {
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
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	class DummyContactFirstNameComparator implements Comparator<DummyContact> {
	    public int compare(DummyContact contact1, DummyContact contact2) {
	        return contact1.getFirstName().compareTo(contact2.getFirstName());
	    }
	}
	
	class DummyContactLastNameComparator implements Comparator<DummyContact> {
	    public int compare(DummyContact contact1, DummyContact contact2) {
	        return contact1.getLastName().compareTo(contact2.getLastName());
	    }
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

	    public DummyContact createFromParcel(Parcel source) {
	    	
	        return new DummyContact(source);
	        
	    }

		@Override
		public DummyContact[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DummyContact[size];
		}
	};
}
