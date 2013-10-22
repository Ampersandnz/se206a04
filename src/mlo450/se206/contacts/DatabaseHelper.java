package mlo450.se206.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_COMMENTS = "contacts";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRST_NAME = "_firstName";
	public static final String COLUMN_LAST_NAME = "_lastName";
	public static final String COLUMN_MOBILE_PHONE = "_mobilePhone";
	public static final String COLUMN_HOME_PHONE = "_homePhone";
	public static final String COLUMN_WORK_PHONE = "_workPhone";
	public static final String COLUMN_EMAIL = "_email";
	public static final String COLUMN_ADDRESS = "_address";
	public static final String COLUMN_DATE_OF_BIRTH = "_dateOfBirth";
	public static final String COLUMN_IMAGE_PATH = "_imagePath";

	private static final String DATABASE_NAME = "contacts.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_COMMENTS + "(" + COLUMN_ID + 
			" integer primary key autoincrement, " + COLUMN_FIRST_NAME + " text not null, " + COLUMN_LAST_NAME + 
			" text not null, " + COLUMN_MOBILE_PHONE + " text not null, " + COLUMN_HOME_PHONE + " text not null, " + 
			COLUMN_WORK_PHONE + " text not null, " + COLUMN_EMAIL + " text not null, " + COLUMN_ADDRESS + 
			" text not null, " + COLUMN_DATE_OF_BIRTH + " text not null, " + COLUMN_IMAGE_PATH + " text not null);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}

}
