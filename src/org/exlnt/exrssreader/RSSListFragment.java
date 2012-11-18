package org.exlnt.exrssreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RSSListFragment extends ListFragment {
	
	private ArrayList<HashMap<String, String>> trackList = null;
	private TrackListAdapter mAdapter;
	private Runnable viewTracks;
	private ProgressDialog m_ProgressDialog = null; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    trackList = new ArrayList<HashMap<String, String>>();
	    mAdapter = new TrackListAdapter(getActivity(), R.layout.list_element, trackList);
	    setListAdapter(mAdapter);
	    
	    viewTracks = new Runnable(){

			public void run() {
				getFavoriteTracks();	
			}
	    };
	    
	    Thread thread = new Thread(null, viewTracks, "Background");
	    thread.start();
        m_ProgressDialog = ProgressDialog.show(getActivity(),    
                "Please wait...", "Retrieving data ...", true);
	    
	}
	
	private void getFavoriteTracks(){
		try {
			trackList = new JSONTracksParser().getTracks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getActivity().runOnUiThread(returnRes);
	}
	

/*	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myListFragmentView = inflater.inflate(R.id.list_view_fragment, container, false);
		return myListFragmentView;
	}*/

	private Runnable returnRes = new Runnable() {

		public void run() {
			if(trackList != null && trackList.size() > 0){
				mAdapter.notifyDataSetChanged();
				for(int i = 0; i < trackList.size(); i++) {
					mAdapter.add(trackList.get(i));
				}
		           m_ProgressDialog.dismiss();
		           mAdapter.notifyDataSetChanged();
			}
			
		}
		
	};
	
	private class TrackListAdapter extends ArrayAdapter<HashMap<String, String>> {
		
		private ArrayList<HashMap<String, String>> items = null;

		public TrackListAdapter(Context context, int textViewResourceId,
				List<HashMap<String, String>> objects) {
			super(context, textViewResourceId, objects);
			this.items = (ArrayList<HashMap<String, String>>) objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			LayoutInflater li = getActivity().getLayoutInflater();
			view = li.inflate(R.layout.list_element, null);
			HashMap<String, String> map = items.get(position);
			if (map != null){
				TextView artist = (TextView) view.findViewById(R.id.artname);
				TextView song = (TextView) view.findViewById(R.id.songname);
				if (artist != null) {
					artist.setText(map.get("artistName"));
				}
				if (song != null) {
					song.setText(map.get("songName"));
				}
			}
			return view;
		}
		
		
		
	} 
}
