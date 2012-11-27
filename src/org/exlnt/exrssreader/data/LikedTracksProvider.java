package org.exlnt.exrssreader.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class LikedTracksProvider extends ContentProvider {
	//private fields
	private SQLiteHelper dataBase;
	private static final String AUTHORITY = "org.exlnt.exrssreader.data.LikedTracksProvider";
	
	private static final String LIKED_TRACKS_BASE_PATH = "likedTracks";
    public static final int LIKED_TRACKS = 100;
    public static final int LIKED_TRACK_ID = 110;
	
	//public fields
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LIKED_TRACKS_BASE_PATH);
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/ex-lovedtracks";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/ex-lovedtracks";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		sURIMatcher.addURI(AUTHORITY, LIKED_TRACKS_BASE_PATH, LIKED_TRACKS);
        sURIMatcher.addURI(AUTHORITY, LIKED_TRACKS_BASE_PATH + "/#", LIKED_TRACK_ID);
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case LIKED_TRACKS:
            return CONTENT_TYPE;
        case LIKED_TRACK_ID:
            return CONTENT_ITEM_TYPE;
        default:
            return null;
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
        if (uriType != LIKED_TRACKS) {
            throw new IllegalArgumentException("Invalid URI for insert");
        }
        SQLiteDatabase db = dataBase.getWritableDatabase();
        long newID = db.insert(SQLiteHelper.TABLE_TRACKS, null, values);
        if (newID > 0) {
        	Uri newURI = ContentUris.withAppendedId(uri, newID);
        	 getContext().getContentResolver().notifyChange(uri, null);
             return newURI;
         } else {
             throw new SQLException("Failed to insert row into " + uri);
         }
	}

	@Override
	public boolean onCreate() {
		dataBase = new SQLiteHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SQLiteHelper.TABLE_TRACKS);
		
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case LIKED_TRACK_ID: 
        	queryBuilder.appendWhere(SQLiteHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
        	break;
        case LIKED_TRACKS:
        	break;
        default:
        	throw new IllegalArgumentException("Unknown URI");
        }
        
        Cursor cursor = queryBuilder.query(dataBase.getReadableDatabase(), projection, 
        		selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
    
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
