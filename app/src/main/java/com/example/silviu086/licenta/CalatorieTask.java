package com.example.silviu086.licenta;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Silviu086 on 31.05.2016.
 */
public class CalatorieTask extends AsyncTask<String, Integer, String> {
    private Calatorie calatorie;
    private TaskCompleted taskCompleted;

    public CalatorieTask(Calatorie calatorie, TaskCompleted taskCompleted) {
        this.calatorie = calatorie;
        this.taskCompleted = taskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        taskCompleted.onTaskCompleted(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String resultSend = "";
        String resultPasager = "";
        try{
            URL url = new URL(UrlLinks.URL_GCM_MESSAGE);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_calatorie", String.valueOf(calatorie.getId()))
                    .appendQueryParameter("message", "Cerere noua primita!")
                    .appendQueryParameter("type", "0").build().getEncodedQuery();

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
            Log.i("CalatorieTask", sb.toString());
            reader.close();
            is.close();
            con.disconnect();
            resultSend = "sent";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            URL url = new URL(UrlLinks.URL_ADAUGA_PASAGER_IN_ASTEPTARE);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.connect();

            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm");
            Date date = new Date();
            String data = dateFormat.format(date);

            String query = new Uri.Builder()
                    .appendQueryParameter("id_calatorie", String.valueOf(calatorie.getId()))
                    .appendQueryParameter("id_account", String.valueOf(NavigationActivity.account.getId()))
                    .appendQueryParameter("data", data)
                    .build().getEncodedQuery();

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
            Log.i("CalatorieTask", sb.toString());
            reader.close();
            is.close();
            con.disconnect();
            resultPasager = "adaugat";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSend + "|" + resultPasager;
    }
}
