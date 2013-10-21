package mlo450.se206.contacts;

import java.util.ArrayList;
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
import android.widget.BaseAdapter;
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
	private List<Contact> displayList = new ArrayList<Contact>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listview = (ListView)findViewById(R.id.list_listview);
		addButton = (Button)findViewById(R.id.list_newcontactbutton);
		sortSpinner = (Spinner)findViewById(R.id.list_sortSpinner);
		
		setupListView();
		setupAddButton();
		setupSpinner();
	}

	private void setupListView() {

		displayList.add(new Contact("1"));
		displayList.add(new Contact("2"));
		displayList.add(new Contact("3"));
		displayList.add(new Contact("4"));
		displayList.add(new Contact("5"));
		
		ListAdapter listAdapter = new CustomListAdapter();
		listview.setAdapter(listAdapter);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {
				
				Contact selectedContact = displayList.get(clickedViewPosition);
				
				//Create a ContactDetail activity for the clicked Contact, pass it in so its fields can be displayed.
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
				//Update display
				((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
			}
			
		});
		
	}
	
	private void setupSpinner() {
		
		sortSpinner = (Spinner)findViewById(R.id.list_sortSpinner);
		sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	if (arg2 == 1) {
            		//Sort by first name
            		((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
            	}
            	
            	if (arg2 == 2) {
            		//Sort by last name
            		((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
            	}
            	
            	if (arg2 == 3) {
            		//Sort by phone number
            		((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
            	}
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
			
			// Access textview elements inside the view (note we must specify the parent view to look in)
			ImageView image = (ImageView)listItemView.findViewById(R.id.list_item_image);
			TextView name = (TextView)listItemView.findViewById(R.id.list_item_text_name);
			TextView mobilePhone = (TextView)listItemView.findViewById(R.id.list_item_text_mobilePhone);
			
			// Set the text for each textview (use the position argument to find the appropriate element in the list)
			Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.defaultimage);
			image.setImageBitmap(bm);
			name.setText(displayList.get(position).getFirstName() + " " + displayList.get(position).getLastName());
			mobilePhone.setText(displayList.get(position).getMobilePhone());
			
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
		if (requestCode == 0) {
			if(resultCode == RESULT_OK){      
				Contact newContact = new Contact();
				newContact.setFirstName(data.getStringExtra("firstName"));
				//TODO Delete the Contact with the specified id
				((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
			}
		}
		
		if (requestCode == 1) {
			if(resultCode == RESULT_OK){      
				String idToDelete=data.getStringExtra("id");
				//TODO Delete the Contact with the specified id
				((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
			}
		}
	}
	
}