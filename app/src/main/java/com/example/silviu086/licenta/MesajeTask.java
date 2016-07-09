package com.example.silviu086.licenta;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsic;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Silviu086 on 08.07.2016.
 */
public class MesajeTask extends AsyncTask<String, Integer, List<MesajeTopic>> {
    private ProgressDialog mProgressDialog;
    private TaskCompletedMesaje mTaskCompleted;
    private List<MesajeTopic> listaMesajeTopics;

    public MesajeTask(ProgressDialog progressDialog, TaskCompletedMesaje mTaskCompleted) {
        this.mProgressDialog = progressDialog;
        this.mTaskCompleted = mTaskCompleted;
        listaMesajeTopics = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(List<MesajeTopic> s) {
        super.onPostExecute(s);
        mTaskCompleted.onTaskCompleted(s);
    }

    @Override
    protected List<MesajeTopic> doInBackground(String... params) {
        try {
            URL url = new URL(UrlLinks.URL_MESAJE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id_account", String.valueOf(NavigationActivity.account.getId())).build().getEncodedQuery();

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

            JSONObject json = new JSONObject(sb.toString());
            int success = json.getInt("success");
            if (success == 1) {
                JSONArray jsonTopics = json.getJSONArray("topics");
                for (int i = 0; i < jsonTopics.length(); i++) {
                    JSONObject ob = jsonTopics.getJSONObject(i);
                    int id = Integer.valueOf(ob.getString("id"));
                    int idCalatorie = Integer.valueOf(ob.getString("id_calatorie"));
                    int idExpeditor = Integer.valueOf(ob.getString("id_expeditor"));
                    int idDestinatar = Integer.valueOf(ob.getString("id_destinatar"));
                    String numeExpeditor = ob.getString("nume_expeditor");
                    String numeDestinatar = ob.getString("nume_destinatar");
                    MesajeTopic topic = new MesajeTopic(id, idCalatorie, idExpeditor, idDestinatar, numeExpeditor, numeDestinatar);
                    JSONArray jsonMesaje = ob.getJSONArray("mesaje");
                    for (int j = 0; j < jsonMesaje.length(); j++) {
                        JSONObject mes = jsonMesaje.getJSONObject(j);
                        int idMes = Integer.valueOf(mes.getString("id"));
                        String mesaj = mes.getString("mesaj");
                        int idAccount = Integer.valueOf(mes.getString("id_account"));
                        String nume = mes.getString("nume");
                        String data = mes.getString("data");
                        int citit = Integer.valueOf(mes.getString("citit"));
                        Mesaj m = new Mesaj(idMes, mesaj, idAccount, nume, data, citit);
                        topic.addMesaj(m);
                    }
                    listaMesajeTopics.add(topic);
                }
            }
            if(listaMesajeTopics.size()>1){
                Collections.sort(listaMesajeTopics, new Comparator<MesajeTopic>() {
                    @Override
                    public int compare(MesajeTopic lhs, MesajeTopic rhs) {
                        if (lhs.getListaMesaje().get(lhs.getListaMesaje().size()-1).getData().compareTo(rhs.getListaMesaje().get(rhs.getListaMesaje().size()-1).getData()) > 0) {
                            return -1;
                        } else if (lhs.getListaMesaje().get(lhs.getListaMesaje().size()-1).getData().compareTo(rhs.getListaMesaje().get(rhs.getListaMesaje().size()-1).getData()) < 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }
            return listaMesajeTopics;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
