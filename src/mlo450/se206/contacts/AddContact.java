package mlo450.se206.contacts;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
/*
 * An activity for creating a single new Contact and adding it to the list.
 * Editable text fields and the ability to load an image from either the phone's gallery, or the camera.
 */
public class AddContact extends Activity {

	private Contact theContact = new Contact();
	private ImageView displayImage;
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
		
//		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(camera, 0);
//		
//		Intent gallery = new Intent(Intent.ACTION_PICK,
//				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		startActivityForResult(gallery , 1);
		
		displayImage.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {

						AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
						
						dialogBuilder.setTitle("Select image source:");
						
						dialogBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
							
						dialogBuilder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
							
					}
						
				});	
	}
	
//	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
//		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
//		
//		switch(requestCode) {
//			case 0:
//			    if(resultCode == RESULT_OK){  
//			        Uri selectedImage = imageReturnedIntent.getData();
//			        displayImage.setImageURI(selectedImage);
//			    }
//	
//			    break; 
//			    
//			case 1:
//			    if(resultCode == RESULT_OK){  
//			        Uri selectedImage = imageReturnedIntent.getData();
//			        displayImage.setImageURI(selectedImage);
//			    }
//			    
//			    break;
//			    
//			}
//		
//		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

}
