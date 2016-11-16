package grp2.estimoteapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by t4pita00 on 16.11.2016.
 */

public class DataGetter extends AsyncTask<String, String, JSONArray> {
    private ProgressDialog pDialog;

    //Url for songs lunch list
    private static String url;

    //Json nodes
    private static final String TAG_Name = "name";
    private static final String TAG_Artist = "artist";

    JSONArray songs = null;
    MainActivity activity;
    String textToPrint = "";
    DataGetter(MainActivity activity,String url){
        this.activity = activity;
        this.url = url;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

    }

    @Override
    protected JSONArray doInBackground(String... args) {
        JSONParser jParser = new JSONParser();
        Log.d("", "penis");
        // Getting JSON from URL
        JSONArray json = jParser.getJSONFromUrl(url);
        return json;
    }

    @Override
    protected void onPostExecute(JSONArray json) {
        pDialog.dismiss();

        try {
            //Getting the array
            songs = json;

            textToPrint = "";
            //Getting song names and artists
            for (int i = 0; i < songs.length(); i++) {
                JSONObject c = songs.getJSONObject(i);
                String name = c.getString(TAG_Name);
                String artist = c.getString(TAG_Artist);
                textToPrint += "Song name: " + name + " Artist: " + artist + " \n";
            }

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    activity.songText.setText(textToPrint);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
