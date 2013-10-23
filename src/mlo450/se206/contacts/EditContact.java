package mlo450.se206.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
/*
 * An activity for editing a single existing Contact.
 * Editable text fields and the ability to load an image from either the phone's gallery, or the camera.
 */
public class EditContact extends Activity {
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
	private Contact currentValues;
	private Bitmap saveOnTurn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		Intent intent = getIntent();
		currentValues = (Contact)intent.getParcelableExtra("theContact");

		setupEditTexts();
		setupImageView();
		setupButtons();
	}

	private void setupEditTexts() {
		firstName = (EditText)findViewById(R.id.editContact_firstName);
		lastName = (EditText)findViewById(R.id.editContact_lastName);
		mobilePhone = (EditText)findViewById(R.id.editContact_mobilePhone);
		homePhone = (EditText)findViewById(R.id.editContact_homePhone);
		workPhone = (EditText)findViewById(R.id.editContact_workPhone);
		email = (EditText)findViewById(R.id.editContact_email);
		address = (EditText)findViewById(R.id.editContact_address);
		dateOfBirth = (EditText)findViewById(R.id.editContact_dateOfBirth);

		String fN = currentValues.getFirstName();
		String lN = currentValues.getLastName();
		String mP = currentValues.getMobilePhone();
		String hP = currentValues.getHomePhone();
		String wP = currentValues.getWorkPhone();
		String e = currentValues.getEmail();
		String a = currentValues.getAddress();
		String dOB = currentValues.getDateOfBirth();

		if (!fN.equals("")) { firstName.setText(fN); }
		if (!lN.equals("")) { lastName.setText(lN); }
		if (!mP.equals("")) { mobilePhone.setText(mP); }
		if (!hP.equals("")) { homePhone.setText(hP); }
		if (!wP.equals("")) { workPhone.setText(wP); }
		if (!e.equals("")) { email.setText(e); }
		if (!a.equals("")) { address.setText(a); }
		if (!dOB.equals("")) { dateOfBirth.setText(dOB); }
	}

	private void setupImageView() {
		displayImage = (ImageView)findViewById(R.id.editContact_image);
		ImageManager imageManager = new ImageManager(EditContact.this);
		String imagePath = currentValues.getImagePath();
		Bitmap bm = imageManager.decodeSampledBitmapFromFile(imagePath, 150, 150);
		
		if (imagePath.equals("default")) {
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.defaultimage);
		}
		
		displayImage.setImageBitmap(bm);
		displayImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);

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
		saveButton = (Button)findViewById(R.id.editContact_button_save);
		cancelButton = (Button)findViewById(R.id.editContact_button_cancel);

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				displayImage.buildDrawingCache();
				ImageManager imageManager = new ImageManager(EditContact.this);
				String iP = imageManager.storeImage(displayImage.getDrawingCache());

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	protected void onResume() {
		displayImage.setImageBitmap(saveOnTurn);
		super.onResume();
	}

	@Override
	protected void onPause() {
		saveOnTurn = displayImage.getDrawingCache();
		super.onPause();
	}
}
