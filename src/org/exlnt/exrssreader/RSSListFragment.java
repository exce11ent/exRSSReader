package org.exlnt.exrssreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RSSListFragment extends SherlockListFragment {
	
	private ArrayList<HashMap<String, String>> trackList = null;
	private TrackListAdapter mAdapter;
//	private Runnable viewTracks;
	private ProgressDialog m_ProgressDialog = null; 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    trackList = new ArrayList<HashMap<String, String>>();
	    
/*	    viewTracks = new Runnable(){
	    	
			public void run() {
				getFavoriteTracks();	
			}
	    };
	    
	    Thread thread = new Thread(null, viewTracks, "Background");
	    thread.start();*/
	    
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataDownloadTask task = new DataDownloadTask();
		task.execute();
	}



	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		HashMap<String, String> map = trackList.get(position);
		
		Intent intent = new Intent(getActivity(), DetailsActivity.class);
		intent.putExtra("lovedTrack", map);
		startActivity(intent);
    }
	
	
	
	private void getFavoriteTracks(){
		try {
			trackList = new JSONTracksParser().getTracks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.e("TRACK LIST: ", trackList.toString());
/*        getActivity().runOnUiThread(returnRes);*/
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.rsslistfrag, null);
	}

/*	private Runnable returnRes = new Runnable() {

		public void run() {
		   		
		}
		
	};*/
	

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
			if (view == null) {
				LayoutInflater li = getActivity().getLayoutInflater();
				view = li.inflate(R.layout.mylistelement, null);
			}
			HashMap<String, String> map = items.get(position);
			if (map != null){
				TextView artist = (TextView) view.findViewWithTag("artistName");
				TextView song = (TextView) view.findViewWithTag("trackName");
				if (artist != null) {
					artist.setText(map.get("artistName"));
				}
				if (song != null) {
					song.setText(map.get("trackName"));
				}
			}
			return view;
		}
		
	}	
	
	private class DataDownloadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			getFavoriteTracks();
			return null;
		}


		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			 mAdapter = new TrackListAdapter(getActivity(), R.layout.mylistelement, trackList);
			    setListAdapter(mAdapter);
				m_ProgressDialog.dismiss();
		        mAdapter.notifyDataSetChanged();	
		}


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			m_ProgressDialog = ProgressDialog.show(getActivity(),    
	                "Please wait...", "Retrieving data ...", true);   
		}
		
	}
}
