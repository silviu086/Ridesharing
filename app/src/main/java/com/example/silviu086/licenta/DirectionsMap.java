package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Silviu086 on 12.05.2016.
 */
public class DirectionsMap{

    private GoogleMap mMap;
    private String mDurata;
    private String mDistanta;
    private Context mContext;

    public DirectionsMap(GoogleMap map, Context context) {
        this.mMap = map;
        this.mContext = context;
    }

    public String getDurata(){
        return this.mDurata;
    }

    public String getDistanta(){
        return this.mDistanta;
    }

    class CalculatePolylineTask extends AsyncTask<String, Integer, PolylineOptions>{

        private String mPunctPlecare;
        private String mPunctSosire;
        LatLng plecare;
        LatLng sosire;
        ArrayList<LatLng> points = null;
        PolylineOptions polylineOptions = null;
        JSONObject jsonObject;
        String result;
        DirectionJSONParser directionJSONParser;
        ProgressDialog progressDialog;

        public CalculatePolylineTask(String punctPlecare, String punctSosire){
            this.mPunctPlecare = punctPlecare;
            this.mPunctSosire = punctSosire;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Asteptati...");
            progressDialog.setMessage("Se incarca harta!");
            progressDialog.show();
            DirectionsMapTask directionsMapTask = new DirectionsMapTask(mPunctPlecare, mPunctSosire);
            try {
                result = directionsMapTask.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

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
        }

        @Override
        protected PolylineOptions doInBackground(String... params) {
            List<List<HashMap<String, String>>> directionRoutes = new ArrayList<>();
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            directionJSONParser = new DirectionJSONParser();
            directionRoutes = directionJSONParser.parse(jsonObject);
            mDistanta = directionJSONParser.getDistanta();
            mDurata = directionJSONParser.getDurata();
            for(int i=0;i<directionRoutes.size();i++){
                points = new ArrayList<LatLng>();
                polylineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = directionRoutes.get(i);
                for(int j=0;j<path.size();j++){
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
            if(points != null){
                plecare = points.get(0);
                sosire = points.get(points.size() - 1);
            }
            return polylineOptions;
        }
    }

    public void drawRoute(String punctPlecare, String punctSosire){
        CalculatePolylineTask calculatePolylineTask = new CalculatePolylineTask(punctPlecare, punctSosire);
        calculatePolylineTask.execute();
        /*try {
            ArrayList<LatLng> points = null;
            PolylineOptions polylineOptions = null;
            JSONObject jsonObject;
            DirectionJSONParser directionJSONParser;
            DirectionsMapTask directionsMapTask = new DirectionsMapTask(punctPlecare, punctSosire);
            String result = directionsMapTask.execute().get();
            List<List<HashMap<String, String>>> directionRoutes = new ArrayList<>();
            LatLng plecare;
            LatLng sosire;
            jsonObject = new JSONObject(result);
            directionJSONParser = new DirectionJSONParser();
            directionRoutes = directionJSONParser.parse(jsonObject);
            mDistanta = directionJSONParser.getDistanta();
            mDurata = directionJSONParser.getDurata();
            for(int i=0;i<directionRoutes.size();i++){
                points = new ArrayList<LatLng>();
                polylineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = directionRoutes.get(i);
                for(int j=0;j<path.size();j++){
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
            plecare = points.get(0);
            sosire = points.get(points.size() - 1);
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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }

   /* @Override
    public void onTaskCompleted(String result) {
        try {
            List<List<HashMap<String, String>>> directionRoutes = new ArrayList<>();
            LatLng punctPlecare;
            LatLng punctSosire;

            markerOptions = new MarkerOptions();
            jsonObject = new JSONObject(result);
            directionJSONParser = new DirectionJSONParser();
            directionRoutes = directionJSONParser.parse(jsonObject);
            durata.concat(directionJSONParser.getDurata());
            distanta = directionJSONParser.getDistanta();
            bundle.putString("distanta", directionJSONParser.getDistanta());
            distanta.concat(directionJSONParser.getDistanta());
            for(int i=0;i<directionRoutes.size();i++){
                points = new ArrayList<LatLng>();
                polylineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = directionRoutes.get(i);
                for(int j=0;j<path.size();j++){
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
            punctPlecare = points.get(0);
            punctSosire = points.get(points.size() - 1);
            mMap.clear();
            mMap.addPolyline(polylineOptions);
            MarkerOptions markerOptions1 = new MarkerOptions();
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions1.position(punctPlecare);
            markerOptions2.position(punctSosire);
            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions1);
            mMap.addMarker(markerOptions2);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(punctPlecare);
            builder.include(punctSosire);
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 80);
            mMap.animateCamera(cameraUpdate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
}
