package org.exlnt.exrssreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataProvider{
	
	private static final String DATABASE_NAME = "feeds.db";
	private static final String TABLE_TRACKS = "tracks";
	private static final String COLUMN_ID = "_id";
	//public fields
	public static final String COLUMN_ARTIST_NAME = "artist_name";
	public static final String COLUMN_TRACK_NAME = "track_name";
	public static final String COLUMN_ARTIST_URL = "artist_url";
	public static final String COLUMN_TRACK_URL = "track_url";
	
	private static final String CREATE_DATABASE = "create table " + TABLE_TRACKS + 
			"(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_ARTIST_NAME +
			" text not null, " + COLUMN_TRACK_NAME + " text not null, " + 
			COLUMN_ARTIST_URL + " text not null, " + COLUMN_TRACK_URL + " text not null);";
	private static final int DATABASE_VERSION = 1;


	private final Context mContext;
	private DBHelper mHelper;
	private SQLiteDatabase mDataBase;
	
	public SQLiteDataProvider(Context context) {
		mContext = context;
	}
	
	public void open(){
		mHelper = new DBHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
		mDataBase = mHelper.getWritableDatabase();
	}
	
	public void close(){
		if (mHelper != null) 
			mHelper.close();
	}
	
	public Cursor getData(){
		return mDataBase.query(TABLE_TRACKS, null, null, null, null, null, null);
	}
	
	public void addRecord(String artistName, String trackName, String artistURL, String trackURL){
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ARTIST_NAME, artistName);
		cv.put(COLUMN_TRACK_NAME, trackName);
		cv.put(COLUMN_ARTIST_URL, artistURL);
		cv.put(COLUMN_TRACK_URL, trackURL);		
		mDataBase.insert(TABLE_TRACKS, null, cv);
	}
	
	
	private class DBHelper extends SQLiteOpenHelper {
		public DBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_DATABASE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}

}
