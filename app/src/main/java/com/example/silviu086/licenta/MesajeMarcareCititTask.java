package com.example.silviu086.licenta;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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
public class MesajeMarcareCititTask extends AsyncTask<String, Integer, String> {
    private int idTopic;
    private int idAccount;
    private TaskCompleted taskCompleted;

    public MesajeMarcareCititTask(int idAccount, int idTopic, TaskCompleted taskCompleted) {
        this.idAccount = idAccount;
        this.idTopic = idTopic;
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
        try {
            URL url = new URL(UrlLinks.URL_MESAJE_MARCARE_CITITE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_account", String.valueOf(idAccount))
                    .appendQueryParameter("id_topic", String.valueOf(idTopic)).build().getEncodedQuery();

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
            Log.e("MesajeMarcareCitit", ex.getMessage());
        }
        return null;
    }
}
