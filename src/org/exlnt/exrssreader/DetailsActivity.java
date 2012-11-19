package org.exlnt.exrssreader;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

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
}
