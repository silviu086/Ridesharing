package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Silviu086 on 24.06.2016.
 */
public class CalatorieAdaugataDetaliiStergereTask extends AsyncTask<String, Integer, String> {
    private String calatorieId;
    private ArrayList<String> pasageri;
    private boolean notificare;
    private TaskCompleted taskCompleted;

    public CalatorieAdaugataDetaliiStergereTask(String calatorieId, ArrayList<String> pasageri, boolean notificare, TaskCompleted taskCompleted) {
        this.calatorieId = calatorieId;
        this.pasageri = pasageri;
        this.notificare = notificare;
        this.taskCompleted = taskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        taskCompleted.onTaskCompleted(result);
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_STERGE_CALATORIE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_calatorie", calatorieId)
                    .build().getEncodedQuery();

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
                if(notificare){
                    for(int i=0;i<pasageri.size();i++){
                        url = new URL(UrlLinks.URL_GCM_MESSAGE);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.connect();

                        query = new Uri.Builder()
                                .appendQueryParameter("id_destinatar", String.valueOf(pasageri.get(i)))
                                .appendQueryParameter("id_expeditor", String.valueOf(NavigationActivity.account.getId()))
                                .appendQueryParameter("id_calatorie", String.valueOf(calatorieId))
                                .appendQueryParameter("type", "5").build().getEncodedQuery();

                        os = con.getOutputStream();
                        writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();

                        is = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(is));
                        sb = new StringBuilder();
                        while( (line = reader.readLine()) != null){
                            sb.append(line);
                        }
                        Log.i("CalatorieStergere", sb.toString());
                        reader.close();
                        is.close();
                        con.disconnect();
                    }
                }
                return "success";
            } else{
                return "failed";
            }
        }catch (Exception ex){
            Log.e("ConfirmareTask: ", ex.toString());
        }
        return "null";
    }
}
