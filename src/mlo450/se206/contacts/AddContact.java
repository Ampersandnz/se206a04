package mlo450.se206.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
/*
 * An activity for creating a single new Contact and adding it to the list.
 * Editable text fields and the ability to load an image from either the phone's gallery, or the camera.
 */
public class AddContact extends Activity {

	private ImageView displayImage;
	private Button saveButton;
	private Button cancelButton;
	private EditText firstName;
	private EditText lastName;
	private EditText mobilePhone;
	private EditText homePhone;
	private EditText workPhone;
	private EditText email;
	private EditText address;
	private EditText dateOfBirth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		setupEditTexts();
		setupImageView();
		setupButtons();
	}
	
	private void setupEditTexts() {
		firstName = (EditText)findViewById(R.id.addContact_firstName);
		lastName = (EditText)findViewById(R.id.addContact_lastName);
		mobilePhone = (EditText)findViewById(R.id.addContact_mobilePhone);
		homePhone = (EditText)findViewById(R.id.addContact_homePhone);
		workPhone = (EditText)findViewById(R.id.addContact_workPhone);
		email = (EditText)findViewById(R.id.addContact_email);
		address = (EditText)findViewById(R.id.addContact_address);
		dateOfBirth = (EditText)findViewById(R.id.addContact_dateOfBirth);
	}
	
	private void setupImageView() {
		displayImage = (ImageView)findViewById(R.id.addContact_image);
		displayImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
				
				dialogBuilder.setTitle("Select image source:");
				
				dialogBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(camera, 0);
					}
				});
					
				dialogBuilder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(gallery , 1);
					}
				});
				
				dialogBuilder.show();
					
			}
				
		});	
	}
	
	private void setupButtons() {
		saveButton = (Button)findViewById(R.id.addContact_button_save);
		cancelButton = (Button)findViewById(R.id.addContact_button_cancel);
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				//TODO: Save image to a file, get the path to it. Need to get the highest ID so far, increment by one to automatically name?
				//Or pass back a path to a temp file and rename it?
				String iP = " ";
				
				returnIntent.putExtra("firstName", firstName.getText().toString());
				returnIntent.putExtra("lastName", lastName.getText().toString());
				returnIntent.putExtra("mobilePhone", mobilePhone.getText().toString());
				returnIntent.putExtra("homePhone", homePhone.getText().toString());
				returnIntent.putExtra("workPhone", workPhone.getText().toString());
				returnIntent.putExtra("email", email.getText().toString());
				returnIntent.putExtra("address", address.getText().toString());
				returnIntent.putExtra("dateOfBirth", dateOfBirth.getText().toString());
				returnIntent.putExtra("imagePath", iP);
				setResult(RESULT_OK,returnIntent);     
				finish();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);        
				finish();
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		
		switch(requestCode) {
			case 0:
			    if(resultCode == RESULT_OK){  
			        Uri selectedImage = imageReturnedIntent.getData();
			        displayImage.setImageURI(selectedImage);
			    }
	
			    break; 
			    
			case 1:
			    if(resultCode == RESULT_OK){  
			        Uri selectedImage = imageReturnedIntent.getData();
			        displayImage.setImageURI(selectedImage);
			    }
			    
			    break;
			    
			}
		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

}
