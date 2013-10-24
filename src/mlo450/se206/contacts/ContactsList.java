package mlo450.se206.contacts;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
/*
 * The main Contacts Manager application activity. Displays a list of Contacts, as their photo, full name and mobile phone number.
 * Can be sorted by first name, last name or mobile phone number.
 */
public class ContactsList extends Activity {
	private ListView listview;
	private Button addButton;
	private Spinner sortSpinner;
	private List<Contact> displayList;
	private ContactsDatasource datasource;
	private ArrayAdapter<Contact> adapter;
	private ImageManager imageManager;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		datasource = new ContactsDatasource(this);
		datasource.open();

		displayList = datasource.getAllContacts();

		listview = (ListView)findViewById(R.id.list_listview);
		addButton = (Button)findViewById(R.id.list_newcontactbutton);
		sortSpinner = (Spinner)findViewById(R.id.list_sortSpinner);

		setupListView();
		setupAddButton();
		setupSpinner();
		
		adapter = (ArrayAdapter<Contact>) listview.getAdapter();;
		adapter.notifyDataSetChanged();
		datasource.close();
	}

	private void setupListView() {
		ListAdapter listAdapter = new CustomListAdapter();
		listview.setAdapter(listAdapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {

				Contact selectedContact = displayList.get(clickedViewPosition);

				//Create a ContactDetail activity for the clicked Contact, and pass it in so its fields can be displayed.
				Intent intent = new Intent();
				intent.setClass(ContactsList.this, ContactDetail.class);
				intent.putExtra("clickedContact", selectedContact);
				startActivityForResult(intent, 1);
			}

		});

	}

	private void setupAddButton() {
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ContactsList.this, AddContact.class);
				startActivityForResult(intent, 0);
			}

		});

	}

	private void setupSpinner() {
		sortSpinner = (Spinner)findViewById(R.id.list_sortSpinner);
		sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 1) {
					//Sort by first name
					Collections.sort(displayList, new Contact().new ContactFirstNameComparator());
				} else if (arg2 == 2) {
					//Sort by last name
					Collections.sort(displayList, new Contact().new ContactLastNameComparator());
					adapter.notifyDataSetChanged();
				} else if (arg2 == 3) {
					//Sort by phone number
					Collections.sort(displayList, new Contact().new ContactMobilePhoneComparator());
					adapter.notifyDataSetChanged();
				}

				adapter.notifyDataSetChanged();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private class CustomListAdapter extends ArrayAdapter<Contact> {
		CustomListAdapter() {
			super(ContactsList.this, android.R.layout.simple_list_item_1, displayList);
		}

		@Override
		public View getView (int position, View convertView, ViewGroup parent) {

			// Create a layout inflater to inflate our xml layout for each item in the list.
			LayoutInflater inflater = (LayoutInflater) ContactsList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// Inflate the list item layout. Keep a reference to the inflated view. Note there is no view root specified.
			View listItemView = inflater.inflate(R.layout.custom_list_item_layout, null);

			// Access view elements inside the view (note we must specify the parent view to look in)
			ImageView image = (ImageView)listItemView.findViewById(R.id.list_item_image);
			TextView name = (TextView)listItemView.findViewById(R.id.list_item_text_name);
			TextView mobilePhone = (TextView)listItemView.findViewById(R.id.list_item_text_mobilePhone);

			// Set the content for each view (use the position argument to find the appropriate element in the list)
			String imagePath = displayList.get(position).getImagePath();
			imageManager = new ImageManager(ContactsList.this);
			Bitmap bm = imageManager.decodeSampledBitmapFromResource(getResources(), R.drawable.defaultimage, 50, 50);
			
			if (imagePath.equals("default")) {
				bm = BitmapFactory.decodeResource(getResources(), R.drawable.defaultimage);
			}
			
			image.setImageBitmap(bm);
			name.setText(displayList.get(position).getFirstName() + " " + displayList.get(position).getLastName());
			mobilePhone.setText(displayList.get(position).getMobilePhone());

			listItemView.setBackgroundColor(displayList.get(position).getColour());
			
			return listItemView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts_list, menu);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		datasource.open();
		Contact toRemove = null;
		
		if (requestCode == 0) {
			if(resultCode == RESULT_OK) {
				Contact contact = datasource.createContact(data.getStringExtra("firstName"), data.getStringExtra("lastName"), 
						data.getStringExtra("mobilePhone"), data.getStringExtra("homePhone"), 
						data.getStringExtra("workPhone"), data.getStringExtra("email"), 
						data.getStringExtra("address"), data.getStringExtra("dateOfBirth"),
						data.getStringExtra("imagePath"), data.getIntExtra("colour", 0xffffffff));
				adapter.add(contact);
			}
		}

		if (requestCode == 1) {
			if(resultCode == RESULT_OK) {      
				long id = data.getLongExtra("id", 0);
				for (Contact c: displayList) {
					if (c.getId() == (id)) {
						datasource.deleteContact(c);
						toRemove = c;
					}
				}
				adapter.remove(toRemove);
			}
			
			if(resultCode == RESULT_FIRST_USER) {      
				//Contact has been edited. Update database
				long id = data.getLongExtra("id", 0);
				for (Contact c: displayList) {
					if (c.getId() == (id)) {
						datasource.deleteContact(c);
						toRemove = c;
					}
				}
				
				adapter.remove(toRemove);
				
				Contact contact = datasource.createContact(data.getStringExtra("firstName"), data.getStringExtra("lastName"), 
						data.getStringExtra("mobilePhone"), data.getStringExtra("homePhone"), 
						data.getStringExtra("workPhone"), data.getStringExtra("email"), 
						data.getStringExtra("address"), data.getStringExtra("dateOfBirth"),
						data.getStringExtra("imagePath"), data.getIntExtra("colour", 0xffffffff));
				
				adapter.add(contact);
			}
		}

		//Update display
		adapter.notifyDataSetChanged();
		datasource.close();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}