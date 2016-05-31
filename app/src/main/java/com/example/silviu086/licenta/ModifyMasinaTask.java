package com.example.silviu086.licenta;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Silviu086 on 25.04.2016.
 */
public class ModifyMasinaTask extends AsyncTask<String, Integer, String> {

    private Bundle bundle;
    private ProgressBar progressBar;
    private TaskCompleted taskCompleted;

    public ModifyMasinaTask(Bundle bundle, ProgressBar progressBar, TaskCompleted taskCompleted) {
        this.bundle = bundle;
        this.progressBar = progressBar;
        this.taskCompleted = taskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.INVISIBLE);
        taskCompleted.onTaskCompleted(s);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_MODIFY_MASINA);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("email", bundle.getString("email"))
                    .appendQueryParameter("marca", bundle.getString("marca"))
                    .appendQueryParameter("model", bundle.getString("model"))
                    .appendQueryParameter("an", bundle.getString("an"))
                    .appendQueryParameter("experienta", bundle.getString("experienta")).build().getEncodedQuery();

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
            if(success == 1){
                return "updated";
            } else{
                return "failed";
            }
        }catch (Exception ex){
            Log.e("ModifiMasinaTask: ", ex.toString());
        }
        return "null";
    }
}
