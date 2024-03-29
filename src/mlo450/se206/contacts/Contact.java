package mlo450.se206.contacts;

import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * @author Michael Lo
 * A Contact object. Has the fields specified in the project requirements and implements Parcelable
 * so it can be passed from one activity to another.
 */
public class Contact implements Parcelable {

	private long _id;
	private String _firstName;
	private String _lastName;
	private String _mobilePhone;
	private String _homePhone;
	private String _workPhone;
	private String _email;
	private String _address;
	private String _dateOfBirth;
	private String _imagePath;
	private int _colour;
	
	//Set default values so that none of the fields can ever be null.
	Contact() {
		_firstName = "";
		_lastName = "";
		_mobilePhone = "";
		_homePhone = "";
		_workPhone = "";
		_email = "";
		_address = "";
		_dateOfBirth = "";
		_imagePath = "default";
	}
	
	//Create from parcel
	Contact(Parcel parcel) {
		_id = parcel.readLong();
		_firstName = parcel.readString();
		_lastName = parcel.readString();
		_mobilePhone = parcel.readString();
		_homePhone = parcel.readString();
		_workPhone = parcel.readString();
		_email = parcel.readString();
		_address = parcel.readString();
		_dateOfBirth = parcel.readString();
		_imagePath = parcel.readString();
		_colour = parcel.readInt();
	}
	
	public long getId() {
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
	
	public String getImagePath() {
		return _imagePath;
	}
	
	public int getColour() {
		return _colour;
	}
	
	public void setId(long id) {
		_id = id;
	}
	
	public void setFirstName(String firstName) {
		_firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		_lastName = lastName;
	}
	
	public void setMobilePhone(String mobilePhone) {
		_mobilePhone = mobilePhone;
	}
	
	public void setHomePhone(String homePhone) {
		_homePhone = homePhone;
	}
	
	public void setWorkPhone(String workPhone) {
		_workPhone = workPhone;
	}
	
	public void setEmail(String email) {
		_email = email;
	}
	
	public void setAddress(String address) {
		_address = address;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		_dateOfBirth = dateOfBirth;
	}
	
	public void setImagePath(String imagePath) {
		_imagePath = imagePath;
	}
	
	public void setColour(int colour) {
		_colour = colour;
	}

	class ContactFirstNameComparator implements Comparator<Contact> {
		public int compare(Contact contact1, Contact contact2) {
	        return contact1.getFirstName().toLowerCase().compareTo(contact2.getFirstName().toLowerCase());
	    }
	}

	class ContactLastNameComparator implements Comparator<Contact> {
		public int compare(Contact contact1, Contact contact2) {
	        return contact1.getLastName().toLowerCase().compareTo(contact2.getLastName().toLowerCase());
	    }
	}
	
	class ContactMobilePhoneComparator implements Comparator<Contact> {
	    public int compare(Contact contact1, Contact contact2) {
	        return contact1.getMobilePhone().compareTo(contact2.getMobilePhone());
	    }
	}

	@Override
	public int describeContents() {
		return 0;
	}

	//Save to parcel
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeLong(_id);
		parcel.writeString(_firstName);
		parcel.writeString(_lastName);
		parcel.writeString(_mobilePhone);
		parcel.writeString(_homePhone);
		parcel.writeString(_workPhone);
		parcel.writeString(_email);
		parcel.writeString(_address);
		parcel.writeString(_dateOfBirth);
		parcel.writeString(_imagePath);
		parcel.writeInt(_colour);
	}
	
	public static final Parcelable.Creator<Contact> CREATOR = new Creator<Contact>() {
	    public Contact createFromParcel(Parcel source) {
	        return new Contact(source);
	    }
	    
		public Contact[] newArray(int size) {
			return new Contact[size];
		}
	};
	
	public String toString() {
		return _firstName + " " + _lastName + "\t" + _mobilePhone + " (ID: " + _id + ")";
	}
}
