package org.exlnt.exrssreader;

import java.util.HashMap;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends SherlockFragmentActivity implements LikedListFragment.OnTrackSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.my_fragment) == null) {
        	ft.add(R.id.main_fragment_place, new RSSListFragment());
        }
        ft.commit();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.show_liked) {
			LikedListFragment  likedListFragment= new LikedListFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.main_fragment_place, likedListFragment);
			transaction.addToBackStack(null);
			transaction.commit();		
		}
		return true;
	}

	public void onTrackSelected(String artistName, String trackName,
			String artistUrl, String trackUrl) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("artistName", artistName);
		map.put("trackName", trackName);
		map.put("artistInfoURL", artistUrl);
		map.put("trackInfoURL", trackUrl);
		
		Intent showDetails = new Intent(getApplicationContext(), DetailsActivity.class);
		showDetails.putExtra("lovedTrack", map);
		startActivity(showDetails);
		
	}

    

    
}
