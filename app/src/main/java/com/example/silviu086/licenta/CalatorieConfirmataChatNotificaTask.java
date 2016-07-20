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

public class CalatorieConfirmataChatNotificaTask extends AsyncTask<String, Integer, String> {
    private int idAccount;
    private int idCalatorie;
    private TaskCompleted taskCompleted;

    public CalatorieConfirmataChatNotificaTask(int idAccount, int idCalatorie, TaskCompleted taskCompleted) {
        this.idAccount = idAccount;
        this.idCalatorie = idCalatorie;
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
                    .appendQueryParameter("id_destinatar", String.valueOf(idAccount))
                    .appendQueryParameter("id_expeditor", String.valueOf(NavigationActivity.account.getId()))
                    .appendQueryParameter("id_calatorie", String.valueOf(idCalatorie))
                    .appendQueryParameter("type", "4").build().getEncodedQuery();

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
        return resultSend;
    }
}
