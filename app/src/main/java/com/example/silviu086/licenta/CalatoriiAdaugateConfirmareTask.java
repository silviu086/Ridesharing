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

/**
 * Created by Silviu086 on 24.06.2016.
 */
public class CalatoriiAdaugateConfirmareTask extends AsyncTask<String, Integer, String> {
    private String calatorieId;
    private String pasagerId;
    //private ProgressDialog progressDialog;
    private TaskCompleted taskCompleted;

    public CalatoriiAdaugateConfirmareTask(String calatorieId, String pasagerId, TaskCompleted taskCompleted) {
        this.calatorieId = calatorieId;
        this.pasagerId = pasagerId;
        //this.progressDialog = progressDialog;
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
            URL url = new URL(UrlLinks.URL_GCM_MESSAGE);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_destinatar", String.valueOf(pasagerId))
                    .appendQueryParameter("id_expeditor", String.valueOf(NavigationActivity.account.getId()))
                    .appendQueryParameter("id_calatorie", String.valueOf(calatorieId))
                    .appendQueryParameter("type", "1").build().getEncodedQuery();

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while( (line = reader.readLine()) != null){
                sb.append(line);
            }
            Log.i("CalatoriiAdaugateConfir", sb.toString());
            reader.close();
            is.close();
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            URL url = new URL(UrlLinks.URL_CONFIRMA_CERERE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_calatorie", calatorieId)
                    .appendQueryParameter("id_pasager", pasagerId)
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
