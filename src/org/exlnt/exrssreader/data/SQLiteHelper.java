package org.exlnt.exrssreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	//public fields
	public static final String COLUMN_ARTIST_NAME = "artist_name";
	public static final String COLUMN_TRACK_NAME = "track_name";
	public static final String COLUMN_ARTIST_URL = "artist_url";
	public static final String COLUMN_TRACK_URL = "track_url";
	public static final String TABLE_TRACKS = "tracks";
	public static final String COLUMN_ID = "_id";
	//private fields
	private static final String DATABASE_NAME = "feeds.db";

	private static final String CREATE_DATABASE = "create table " + TABLE_TRACKS + 
			"(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_ARTIST_NAME +
			" text not null, " + COLUMN_TRACK_NAME + " text not null, " + 
			COLUMN_ARTIST_URL + " text not null, " + COLUMN_TRACK_URL + " text not null);";
	private static final String SCHEMA = CREATE_DATABASE;
	private static final int DATABASE_VERSION = 1;
	private static final String DBG_TAG = "SQLiteHelper";
	

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKS);
        onCreate(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SCHEMA);
		inputData(db);
	}
	
	private void inputData(SQLiteDatabase db) {
		db.execSQL("insert into tracks (artist_name, track_name, artist_url, track_url) values " +
				"('Haste the day', 'fallen', 'some url', 'some url')");
	}

}
