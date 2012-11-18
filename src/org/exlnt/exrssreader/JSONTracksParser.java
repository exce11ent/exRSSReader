package org.exlnt.exrssreader;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTracksParser {

	private static String reqURL = "http://ws.audioscrobbler.com/2.0/?method=user.getlovedtracks&user=sxe_deathcore&api_key=6d7403baf4120f299266f1fa0da3f2ef&format=json";

	private static final String TRACKS = "track";
	private static final String TRACK_NAME = "name";
	private static final String ARTIST = "artist";
	private static final String TRACK_INFO_URL = "url";
	private static final String ARTIST_NAME = "name";
	private static final String ARTIST_INFO_URL = "url";


	public ArrayList<HashMap<String, String>> getTracks() {
		
		JSONArray tracks = null;
		
		ArrayList<HashMap<String, String>> trackList = new ArrayList<HashMap<String, String>>();

		JSONParser parser = new JSONParser();
		JSONObject json = parser.getJSONFromUrl(reqURL);
		JSONObject loved = null;
		
		try {
			loved = json.getJSONObject("lovedtracks");
			
			tracks = loved.getJSONArray(TRACKS);

			for (int i = 0; i < tracks.length(); i++) {
				JSONObject track = tracks.getJSONObject(i);
				String trackName = track.getString(TRACK_NAME);
				String trackInfoURL = track.getString(TRACK_INFO_URL);
				
				JSONObject artist = track.getJSONObject(ARTIST);
				String artistName = artist.getString(ARTIST_NAME);
				String artistInfoURL = artist.getString(ARTIST_INFO_URL);
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("trackName", trackName);
				map.put("trackInfoURL", trackInfoURL);
				map.put("artistName", artistName);
				map.put("artistInfoURL", artistInfoURL);
				
				trackList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.err.append(trackList.toString());
		return trackList;
	}
	

}
