package org.exlnt.exrssreader;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	HashMap<String, String> map = null;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Intent intent = getActivity().getIntent(); 
		map = (HashMap<String, String>) intent.getSerializableExtra("lovedTrack");
		
		View view = inflater.inflate(R.layout.fragment_details, null);
		TextView artistName = (TextView) view.findViewById(R.id.details_artist_name);
		TextView trackName = (TextView) view.findViewById(R.id.details_track_name);
		TextView artistURL = (TextView) view.findViewById(R.id.details_artist_url);
		TextView trackURL = (TextView) view.findViewById(R.id.details_track_url);
		
		if (artistName != null) {
			artistName.setText(map.get("artistName"));
		}
		if (trackName != null) {
			trackName.setText(map.get("trackName"));
		}
		if (artistURL != null) {
			artistURL.setText("Artist last.fm page: " + map.get("artistInfoURL"));
		}
		if (trackURL != null) {
			trackURL.setText("Track last.fm page: " + map.get("trackInfoURL"));
		}
		
		return view;
	}
	
	
}
