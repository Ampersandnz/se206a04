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

/**
 * @author Michael Lo
 * This class saves and loads Bitmaps and files for use in my activities.
 */
public class ImageManager {
	private Context _c;

	public ImageManager(Context c) {
		_c = c;
	}

	/**
	 * @param image (Bitmap)
	 * @return absolute path to image (String)
	 * Save a Bitmap to a file
	 */
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

	/**
	 * @param image (Bitmap)
	 * @return file representing the Bitmap (File)
	 */
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

	/**
	 * @param BitmapFactory Options (BitmapFactory.Options)
	 * @param Bitmap width (int)
	 * @param Bitmap height (int)
	 * @return Factor by which to scale the Bitmap (int)
	 * Calculates the factor by which to scale the bitmap, to ensure it fits in the required dimensions.
	 */
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

	/**
	 * @param Image path (String)
	 * @param Bitmap width (int)
	 * @param Bitmap height (int)
	 * @return Scaled Bitmap (Bitmap)
	 * Creates a Bitmap of the specified dimensions from the file at the given path.
	 */
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

	/**
	 * @param Resources file (Resource)
	 * @param id of the resource from which to create the Bitmap (int)
	 * @param Bitmap width (int)
	 * @param Bitmap height (int)
	 * @return Scaled Bitmap (Bitmap)
	 * Creates a Bitmap of the specified dimensions from the given resource, the default image.
	 */
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
