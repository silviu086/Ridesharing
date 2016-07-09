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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Silviu086 on 09.07.2016.
 */
public class MesajeConversatieTask extends AsyncTask<String, Integer, String>{
    private String mesaj;
    private int idTopic;
    private ProgressDialog progressDialog;
    private TaskCompleted taskCompleted;

    public MesajeConversatieTask(String mesaj, int idTopic, ProgressDialog progressDialog, TaskCompleted taskCompleted) {
        this.mesaj = mesaj;
        this.idTopic = idTopic;
        this.progressDialog = progressDialog;
        this.taskCompleted = taskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        taskCompleted.onTaskCompleted(s);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(UrlLinks.URL_CONVERSATIE_TRIMITE_MESAJ);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.connect();

            Date data = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm");

            String query = new Uri.Builder()
                    .appendQueryParameter("mesaj", mesaj)
                    .appendQueryParameter("id_account", String.valueOf(NavigationActivity.account.getId()))
                    .appendQueryParameter("id_topic", String.valueOf(idTopic))
                    .appendQueryParameter("data", dateFormat.format(data)).build().getEncodedQuery();
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
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            is.close();
            conn.disconnect();

            return sb.toString();

        } catch (Exception ex) {
            Log.e("MesajeConversatieTask", ex.getMessage());
        }
        return null;
    }
}
