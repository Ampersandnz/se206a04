package mlo450.se206.contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class ImageManager {
	private Context _c;
	
	public ImageManager(Context c) {
		_c = c;
	}
	
	public String storeImage(Bitmap image) {
		File file = createFile(image);
		if (file == null) {
			Log.d("DEBUG", "Could not create file");// e.getMessage());
			return "default";
		}
		
		try {
			FileOutputStream outStream = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.close();
		} catch (FileNotFoundException e) {
			Log.d("DEBUG", "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d("DEBUG", "IOException: " + e.getMessage());
		} 
		
		return file.getAbsolutePath();
	}

	private File createFile(Bitmap image){
		File imageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + 
				_c.getApplicationContext().getPackageName() + "/ContactImages"); 

		if (!imageDir.exists()){
			if (!imageDir.mkdirs()){
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
		File mediaFile;
		String imageName="Contact_"+ timeStamp +".jpg";
		mediaFile = new File(imageDir.getPath() + File.separator + imageName);  
		return mediaFile;
	} 
	
	public void deleteImage(String imagePath) {
		
	}
}
