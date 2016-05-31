package com.example.silviu086.licenta;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
 * Created by Silviu086 on 21.04.2016.
 */
public class ModifyDateTask extends AsyncTask<String, Integer, String> {
    private Bundle mBundle;
    private ProgressBar mProgressBar;
    private TaskCompleted mTaskCompleted;

    public ModifyDateTask(Bundle mBundle, ProgressBar mProgressBar, TaskCompleted mTaskCompleted) {
        this.mBundle = mBundle;
        this.mProgressBar = mProgressBar;
        this.mTaskCompleted = mTaskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgressBar.setVisibility(View.INVISIBLE);
        mTaskCompleted.onTaskCompleted(s);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_MODIFY_DATE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("email", mBundle.getString("email").toString())
                    .appendQueryParameter("nume", mBundle.getString("nume").toString())
                    .appendQueryParameter("telefon", mBundle.getString("telefon").toString())
                    .appendQueryParameter("varsta", mBundle.getString("varsta").toString()).build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while( (line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            is.close();
            conn.disconnect();

            JSONObject json = new JSONObject(sb.toString());
            int success = json.getInt("success");
            if(success == 1) {
                return "updated";
            }else{
                return "failed";
            }


        }catch (Exception ex){
            Log.e("ModifyDateTask: ", ex.toString());
        }

        return "null";
    }
}
