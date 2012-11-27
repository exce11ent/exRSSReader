package org.exlnt.exrssreader;

import java.util.HashMap;

import org.exlnt.exrssreader.data.LikedTracksProvider;
import org.exlnt.exrssreader.data.SQLiteHelper;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

public class DetailsActivity extends SherlockFragmentActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.details_activity_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.like) {
			Intent intent = (Intent) getIntent();
			HashMap<String, String> map = new HashMap<String, String>();
			map = (HashMap<String, String>) intent.getSerializableExtra("lovedTrack");
			ContentValues values = new ContentValues();
			
			values.put(SQLiteHelper.COLUMN_ARTIST_NAME, map.get("artistName"));
			values.put(SQLiteHelper.COLUMN_TRACK_NAME, map.get("trackName"));
			values.put(SQLiteHelper.COLUMN_ARTIST_URL, map.get("artistInfoURL"));
			values.put(SQLiteHelper.COLUMN_TRACK_URL, map.get("trackInfoURL"));
			
			getContentResolver().insert(LikedTracksProvider.CONTENT_URI, values);
		}
		return true;
	}
}
