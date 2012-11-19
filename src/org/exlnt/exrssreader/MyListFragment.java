package org.exlnt.exrssreader;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListFragment extends ListFragment {
	
	private ArrayList<String> list = new ArrayList<String>();
	MyArrayAdapter myAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		list.add("first");
		list.add("second");
		
		MyArrayAdapter myAdapter = new MyArrayAdapter(getActivity(), R.layout.mylistelement, list);
		setListAdapter(myAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private class MyArrayAdapter extends ArrayAdapter<String> {

		ArrayList<String> items = new ArrayList<String>();
		
		public MyArrayAdapter(Context context, int textViewResourceId,
				ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View v = convertView;
			if (v == null) {
				LayoutInflater li = getActivity().getLayoutInflater();
				v = li.inflate(R.layout.mylistelement, null);
			}
			String str = items.get(position);
			if (str != null) {
				TextView tv = (TextView) v.findViewWithTag("artistName");
				if (tv != null){
					tv.setText(str);
				}
				TextView tvTrack = (TextView) v.findViewWithTag("trackName");
				if (tvTrack != null){
					tvTrack.setText(str+"-1");
				}
			}
			return v;			/*the dandy warhols - we used to be friends*/
		}
		
		
		
	}
	
	
}
