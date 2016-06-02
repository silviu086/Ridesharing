package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Silviu086 on 12.05.2016.
 */
public class DirectionsMapTask extends AsyncTask<String, Integer, String> {

    private String punctPlecare;
    private String punctSosire;
    private Context context;
    private ProgressDialog progressDialog;

    public DirectionsMapTask(String punctPlecare, String punctSosire, Context context) {
        this.punctPlecare = punctPlecare;
        this.punctSosire = punctSosire;
        this.context = context;
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Asteptati...");
        progressDialog.setMessage("Se calculeaza traseul!");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        //taskCompleted.onTaskCompleted(s);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        try{
            String strUrl = buildDirectionsUrl(punctPlecare, punctSosire);
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while( (line=reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            urlConnection.disconnect();
        }catch (Exception ex){

        }
        return sb.toString();
    }
}
