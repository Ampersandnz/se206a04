package mlo450.se206.contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
			return "default";
		}

		try {
			FileOutputStream outStream = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
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

		String timeStamp = "" + System.currentTimeMillis();
		File mediaFile;
		String imageName="Contact_"+ timeStamp +".jpg";
		mediaFile = new File(imageDir.getPath() + File.separator + imageName);  
		return mediaFile;
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
}
