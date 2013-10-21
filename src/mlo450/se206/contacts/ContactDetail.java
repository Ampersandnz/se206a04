package mlo450.se206.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/*
 * An activity displaying the details of one specific Contact.
 */
public class ContactDetail extends Activity {
	
	private Contact theContact;
	private Spinner optionsSpinner;
	private ImageView image;
	private TextView firstName;
	private TextView lastName;
	private TextView mobilePhone;
	private TextView homePhone;
	private TextView workPhone;
	private TextView email;
	private TextView address;
	private TextView dateOfBirth;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);
		Intent intent = getIntent();
		theContact = (Contact)intent.getParcelableExtra("clickedContact");
		
		optionsSpinner = (Spinner)findViewById(R.id.contactDetail_optionsSpinner);
		
		setupTextViews();
		setupOptionsSpinner();
		setupImageView();
	}
	
	private void setupTextViews() {
		firstName = (TextView)findViewById(R.id.contactDetail_firstName);
		lastName = (TextView)findViewById(R.id.contactDetail_lastName);
		mobilePhone = (TextView)findViewById(R.id.contactDetail_mobilePhone);
		homePhone = (TextView)findViewById(R.id.contactDetail_homePhone);
		workPhone = (TextView)findViewById(R.id.contactDetail_workPhone);
		email = (TextView)findViewById(R.id.contactDetail_email);
		address = (TextView)findViewById(R.id.contactDetail_address);
		dateOfBirth = (TextView)findViewById(R.id.contactDetail_dateOfBirth);
		
		firstName.setText(theContact.getFirstName());
		lastName.setText(theContact.getLastName());
		mobilePhone.setText(theContact.getMobilePhone());
		homePhone.setText(theContact.getHomePhone());
		workPhone.setText(theContact.getWorkPhone());
		email.setText(theContact.getEmail());
		address.setText(theContact.getAddress());
		dateOfBirth.setText(theContact.getDateOfBirth());
	}
	
	private void setupOptionsSpinner() {
		optionsSpinner = (Spinner)findViewById(R.id.contactDetail_optionsSpinner);
		
		optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	
            	if (arg2 == 1) {
            		//Remove contact
            		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ContactDetail.this);
            		
            		dialogBuilder.setTitle("Are you sure you want to remove this contact?");
            		dialogBuilder.setMessage("This cannot be undone!");
            		
            		dialogBuilder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            			
            			@Override
            			public void onClick(DialogInterface arg0, int arg1) {
            				 Intent returnIntent = new Intent();
            				 returnIntent.putExtra("id", theContact.getId());
            				 setResult(RESULT_OK, returnIntent);     
            				 finish();
            			}
            			
            		});

            		dialogBuilder.setPositiveButton("Cancel", null);
            		
            		dialogBuilder.setCancelable(true);
            		
            		dialogBuilder.create().show();
            	}
            	
            	if (arg2 == 2) {
            		//Edit contact
            		//Bring up edit activity
            	}
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

	}
	
	private void setupImageView() {
		try {
			String imagePath = theContact.getImagePath();
			Bitmap bm = BitmapFactory.decodeFile(imagePath);
			if (bm.equals(null)) {
				bm = BitmapFactory.decodeResource(getResources(), R.drawable.defaultimage);
			}
			image.setImageBitmap(bm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_detail, menu);
		return true;
	}
}