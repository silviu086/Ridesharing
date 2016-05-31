package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Silviu086 on 25.05.2016.
 */
public class CautaCalatoriiTask extends AsyncTask<String, Integer, String> {

    private String mPunctPlecare;
    private String mPunctSosire;
    private ProgressDialog mProgressDialog;
    private TaskCompleted mTaskCompleted;

    public CautaCalatoriiTask(String mPunctPlecare, String mPunctSosire, ProgressDialog mProgressDialog, TaskCompleted mTaskCompleted) {
        this.mPunctPlecare = mPunctPlecare;
        this.mPunctSosire = mPunctSosire;
        this.mProgressDialog = mProgressDialog;
        this.mTaskCompleted = mTaskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Se cauta calatorii...");
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgressDialog.dismiss();
        mTaskCompleted.onTaskCompleted(s);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_CAUTA_CALATORII);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("punct_plecare", mPunctPlecare)
                    .appendQueryParameter("punct_sosire", mPunctSosire)
                    .build().getEncodedQuery();

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while( (line = reader.readLine())!=null){
                sb.append(line);
            }
            reader.close();
            is.close();
            connection.disconnect();

            return sb.toString();
        }catch (Exception ex){
            Log.e("exception", ex.getMessage());
        }
        return "null";
    }
}
