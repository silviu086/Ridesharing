package com.example.silviu086.licenta;

import android.net.Uri;
import android.os.AsyncTask;
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
import java.net.URI;
import java.net.URL;

/**
 * Created by Silviu086 on 25.05.2016.
 */
public class AdaugaCalatorieTask extends AsyncTask<String, Integer, String> {
    private int idAccount;
    private Calatorie calatorie;
    private ProgressBar progressBar;
    private TaskCompleted taskCompleted;

    public AdaugaCalatorieTask(int idAccount, Calatorie calatorie, ProgressBar progressBar, TaskCompleted taskCompleted) {
        this.idAccount = idAccount;
        this.calatorie = calatorie;
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
            URL url = new URL(UrlLinks.URL_ADAUGA_CALATORIE);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            Uri.Builder builder = new Uri.Builder();
            String query = builder
                    .appendQueryParameter("punct_plecare", calatorie.getPunctPlecare())
                    .appendQueryParameter("punct_sosire", calatorie.getPunctSosire())
                    .appendQueryParameter("pret", String.valueOf(calatorie.getPret()))
                    .appendQueryParameter("data_plecare", calatorie.getDataPlecare())
                    .appendQueryParameter("ora_plecare", calatorie.getOraPlecare())
                    .appendQueryParameter("locuri_disponibile", String.valueOf(calatorie.getLocuriDisponibile()))
                    .appendQueryParameter("confort", calatorie.getNivelConfort())
                    .appendQueryParameter("bagaj", calatorie.getMarimeBagaj())
                    .appendQueryParameter("durata", calatorie.getDurataCalatorie())
                    .appendQueryParameter("distanta", calatorie.getDistantaCalatorie())
                    .appendQueryParameter("id_account", String.valueOf(idAccount))
                    .build().getEncodedQuery();
            if(calatorie.getMarcaMasina()!=null){
                query = builder
                        .appendQueryParameter("marca_masina", calatorie.getMarcaMasina())
                        .appendQueryParameter("model_masina", calatorie.getModelMasina())
                        .appendQueryParameter("an_fabricatie", String.valueOf(calatorie.getAnFabricatie()))
                        .appendQueryParameter("experienta_auto", calatorie.getExperientaAuto())
                        .build().getEncodedQuery();
            }
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
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
            reader.close();
            is.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(sb.toString());
            int success = jsonObject.getInt("success");
            if(success == 1){
                return "added";
            }else{
                return "failed";
            }

        }catch (Exception ex){
            Log.e("exception", ex.getMessage().toString());
        }
        return "null";
    }

}
