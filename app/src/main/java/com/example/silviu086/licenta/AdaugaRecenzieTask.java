package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Silviu086 on 18.07.2016.
 */
public class AdaugaRecenzieTask extends AsyncTask<String, Integer, Integer> {
    private Bundle bundle;
    private Context context;
    private TaskCompletedInteger taskCompleted;
    private ProgressDialog progressDialog;

    public AdaugaRecenzieTask(Bundle bundle, Context context, TaskCompletedInteger taskCompleted) {
        this.bundle = bundle;
        this.context = context;
        this.taskCompleted = taskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Se adauga recenzia!");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        taskCompleted.onTaskCompleted(integer);
    }

    @Override
    protected Integer doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_ADAUGA_RECENZIE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
            String data = simpleDateFormat.format(date);

            String query = new Uri.Builder()
                    .appendQueryParameter("id_calatorie", bundle.getString("id_calatorie").toString())
                    .appendQueryParameter("id_utilizator", bundle.getString("id_utilizator").toString())
                    .appendQueryParameter("id_utilizator_vizat", bundle.getString("id_utilizator_vizat").toString())
                    .appendQueryParameter("scor", bundle.getString("scor"))
                    .appendQueryParameter("descriere", bundle.getString("descriere").toString())
                    .appendQueryParameter("data", data).build().getEncodedQuery();

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
                url = new URL(UrlLinks.URL_GCM_MESSAGE);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.connect();

                query = new Uri.Builder()
                        .appendQueryParameter("id_calatorie", bundle.getString("id_calatorie").toString())
                        .appendQueryParameter("id_expeditor", bundle.getString("id_utilizator").toString())
                        .appendQueryParameter("id_destinatar", bundle.getString("id_utilizator_vizat").toString())
                        .appendQueryParameter("type", "2").build().getEncodedQuery();

                os = con.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                is = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("CalatoriiAdaugateConfir", sb.toString());
                reader.close();
                is.close();
                con.disconnect();
            }
            return success;
        }catch (Exception ex){
            Log.e("AdaugaRecenzieTask: ", ex.toString());
        }
        return null;
    }
}
