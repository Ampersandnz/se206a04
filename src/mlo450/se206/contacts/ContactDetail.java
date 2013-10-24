package mlo450.se206.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * An activity displaying the details of one specific Contact.
 */
public class ContactDetail extends Activity {
	
	private Contact theContact;
	private ImageView image;
	private TextView firstName;
	private TextView lastName;
	private TextView mobilePhone;
	private TextView homePhone;
	private TextView workPhone;
	private TextView email;
	private TextView address;
	private TextView dateOfBirth;
	private Button editButton;
	private Button deleteButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);
		Intent intent = getIntent();
		theContact = (Contact)intent.getParcelableExtra("clickedContact");
		
		setupTextViews();
		setupButtons();
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
	
	private void setupButtons() {
		editButton = (Button)findViewById(R.id.contactDetail_editButton);
		deleteButton = (Button)findViewById(R.id.contactDetail_deleteContact);
		
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
        		
        		dialogBuilder.create().show();
			}
		});
		
		editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ContactDetail.this, EditContact.class);
				intent.putExtra("theContact", theContact);
				startActivityForResult(intent, 0);
			}
		});
	}
	
	private void setupImageView() {
		image = (ImageView)findViewById(R.id.contactDetail_image);
		String imagePath = theContact.getImagePath();
		Bitmap bm;
		ImageManager imageManager = new ImageManager(ContactDetail.this);

		if (imagePath.equals("default")) {
			bm = imageManager.decodeSampledBitmapFromResource(ContactDetail.this.getResources(), R.drawable.defaultimage, 150, 150);
		} else {
			bm = imageManager.decodeSampledBitmapFromFile(imagePath, 150, 150);
		}
		
		image.setImageBitmap(bm);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if(resultCode == RESULT_OK) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("id", theContact.getId());
				returnIntent.putExtra("firstName", data.getStringExtra("firstName"));
				returnIntent.putExtra("lastName", data.getStringExtra("lastName"));
				returnIntent.putExtra("mobilePhone", data.getStringExtra("mobilePhone"));
				returnIntent.putExtra("homePhone", data.getStringExtra("homePhone"));
				returnIntent.putExtra("workPhone", data.getStringExtra("workPhone"));
				returnIntent.putExtra("email", data.getStringExtra("email"));
				returnIntent.putExtra("address", data.getStringExtra("address"));
				returnIntent.putExtra("dateOfBirth", data.getStringExtra("dateOfBirth"));
				returnIntent.putExtra("imagePath", data.getStringExtra("imagePath"));
				returnIntent.putExtra("colour", data.getIntExtra("colour", 0xffffffff));
				setResult(RESULT_FIRST_USER,returnIntent);
				finish();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_detail, menu);
		return true;
	}
}