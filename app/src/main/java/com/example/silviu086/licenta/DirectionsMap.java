package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Silviu086 on 12.05.2016.
 */
public class DirectionsMap extends AsyncTask<String, Integer, PolylineOptions>{

    private GoogleMap mMap;
    public static String mDurata;
    public static String mDistanta;
    private Context mContext;
    private String mPunctPlecare;
    private String mPunctSosire;
    LatLng plecare;
    LatLng sosire;
    ArrayList<LatLng> points = null;
    PolylineOptions polylineOptions = null;
    JSONObject jsonObject;
    DirectionJSONParser directionJSONParser;
    ProgressDialog progressDialog;
    TaskCompleted taskCompleted;

    public DirectionsMap(GoogleMap map, Context context, String punctPlecare, String punctSosire, TaskCompleted taskCompleted) {
        this.mMap = map;
        this.mContext = context;
        this.mPunctPlecare = punctPlecare;
        this.mPunctSosire = punctSosire;
        this.taskCompleted = taskCompleted;
    }

    public String getDurata(){
        return this.mDurata;
    }

    public String getDistanta(){
        return this.mDistanta;
    }

    private String buildDirectionsUrl(String origin, String dest){
        String str_origin = "origin=" + origin;
        String str_dest = "destination=" + dest;
        String str_mode = "mode=driving";
        String str_language = "&language=ro";
        String sensor = "sensor=false";
        String parameters = str_origin + "&"
                + str_dest + "&"
                + str_mode + "&"
                + str_language + "&"
                + sensor + "&"
                + "key=AIzaSyANTZD0c5Qe0cLomQXeVzK1oNFwll7Atik";
        while(parameters.contains(" ")){
            parameters = parameters.replace(' ','+');
        }
        return "https://maps.googleapis.com/maps/api/directions/json?" +parameters;
    }

    private String normalizeString(String string) {
        char[] out = new char[string.length()];
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        int j = 0;
        for (int i = 0, n = string.length(); i < n; ++i) {
            char c = string.charAt(i);
            if (c <= '\u007F') out[j++] = c;
        }
        return new String(out);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Asteptati...");
        progressDialog.setMessage("Se incarca harta");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(PolylineOptions polylineOptions) {
        super.onPostExecute(polylineOptions);
        if(polylineOptions != null){
            mMap.clear();
            mMap.addPolyline(polylineOptions);
            MarkerOptions markerOptions1 = new MarkerOptions();
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions1.position(plecare);
            markerOptions2.position(sosire);
            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions1);
            mMap.addMarker(markerOptions2);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(plecare);
            builder.include(sosire);
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 80);
            mMap.animateCamera(cameraUpdate);
        }
        else{
            Toast.makeText(mContext, "Nu s-a putut realiza traseul, este necesara conexiune la Internet!", Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
        taskCompleted.onTaskCompleted(null);
    }

    @Override
    protected PolylineOptions doInBackground(String... params) {
        List<List<HashMap<String, String>>> directionRoutes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            String strUrl = buildDirectionsUrl(mPunctPlecare, mPunctSosire);
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            urlConnection.disconnect();
            jsonObject = new JSONObject(sb.toString());
        } catch (Exception e) {
            Log.e("DirectionsMap", e.getMessage());
        }
        directionJSONParser = new DirectionJSONParser();
        directionRoutes = directionJSONParser.parse(jsonObject);
        mDistanta = directionJSONParser.getDistanta();
        mDurata = directionJSONParser.getDurata();
        mDurata = normalizeString(mDurata);
        for (int i = 0; i < directionRoutes.size(); i++) {
            points = new ArrayList<LatLng>();
            polylineOptions = new PolylineOptions();
            List<HashMap<String, String>> path = directionRoutes.get(i);
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.valueOf(point.get("lat"));
                double lng = Double.valueOf(point.get("lng"));

                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            polylineOptions.addAll(points);
            polylineOptions.width(3);
            polylineOptions.color(Color.RED);
        }
        if (points != null) {
            plecare = points.get(0);
            sosire = points.get(points.size() - 1);
        }
        return polylineOptions;
    }

    public void drawRoute(String punctPlecare, String punctSosire) {
        //CalculatePolylineTask calculatePolylineTask = new CalculatePolylineTask(punctPlecare, punctSosire);
        //calculatePolylineTask.execute();
    }
}
