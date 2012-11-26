package org.exlnt.exrssreader;

import org.exlnt.exrssreader.data.LikedTracksProvider;
import org.exlnt.exrssreader.data.SQLiteHelper;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class LikedListFragment extends SherlockListFragment implements LoaderCallbacks<Cursor> {
	
	private OnTrackSelectedListener trackSelectedListener;
    private static final int LIKED_LIST_LOADER = 0x01;

    private SimpleCursorAdapter adapter;

    public interface OnTrackSelectedListener {
        public void onTrackSelected(String artistName, String trackName, String artistUrl, String trackUrl);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.liked_list_fragment, null);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] uiBindFrom = { SQLiteHelper.COLUMN_ARTIST_NAME, SQLiteHelper.COLUMN_TRACK_NAME};
        int[] uiBindTo = { R.id.artist_name, R.id.track_name };

        getLoaderManager().initLoader(LIKED_LIST_LOADER, null, this);
        
        adapter = new SimpleCursorAdapter(
                getActivity().getApplicationContext(), R.layout.mylistelement,
                null, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        setListAdapter(adapter);
		
 	}
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		String[] projection = { SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_ARTIST_NAME, SQLiteHelper.COLUMN_TRACK_NAME,
        		SQLiteHelper.COLUMN_ARTIST_URL, SQLiteHelper.COLUMN_TRACK_URL};
		Cursor cursor = getActivity().getContentResolver()
				.query(Uri.withAppendedPath(LikedTracksProvider.CONTENT_URI, String.valueOf(id)), projection, null, null, null);
		if (cursor.moveToFirst()) {
			String artistName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_ARTIST_NAME));
			String trackName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TRACK_NAME));
			String artistUrl = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_ARTIST_URL));
			String trackUrl = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TRACK_URL));
			
			trackSelectedListener.onTrackSelected(artistName, trackName, artistUrl, trackUrl);
		}
		cursor.close();
    }

	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String[] projection = { SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_ARTIST_NAME, SQLiteHelper.COLUMN_TRACK_NAME,
        		SQLiteHelper.COLUMN_ARTIST_URL, SQLiteHelper.COLUMN_TRACK_URL};

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                LikedTracksProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);	
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
            trackSelectedListener = (OnTrackSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTrackSelectedListener");
        }
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
		
	}

	
	
}
