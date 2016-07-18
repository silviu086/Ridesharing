package com.example.silviu086.licenta;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Silviu086 on 11.07.2016.
 */
public class UtilizatorProfilTask extends AsyncTask<String, Integer, Account> {
    private Context context;
    private int idAccount;
    private TaskCompletedProfil taskCompletedProfil;
    private Account account;

    public UtilizatorProfilTask(Context context, int idAccount, TaskCompletedProfil taskCompletedProfil) {
        this.context = context;
        this.idAccount = idAccount;
        this.taskCompletedProfil = taskCompletedProfil;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        account = null;
    }

    @Override
    protected void onPostExecute(Account account) {
        super.onPostExecute(account);
        taskCompletedProfil.onTaskCompleted(account);
    }

    @Override
    protected Account doInBackground(String... params) {
        try {
            URL url = new URL(UrlLinks.URL_PROFIL_UTILIZATOR);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            String query;

            //SETARE PARAMETRII
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("id", String.valueOf(idAccount));
            query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            InputStream in = conn.getInputStream();

            StringBuilder sb = new StringBuilder();
            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String nextLine = "";
                while ((nextLine = reader.readLine()) != null) {
                    sb.append(nextLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject json = new JSONObject(sb.toString());
            int success = json.getInt("success");
            if(success == 1) {
                JSONObject jsonAccount = json.getJSONObject("account");
                account = new AccountBuilder()
                        .setId(Integer.valueOf(jsonAccount.getString("id")))
                        .setEmail(jsonAccount.getString("email"))
                        .setParola(null)
                        .setDateCreated(jsonAccount.getString("date_created"))
                        .setNume(jsonAccount.getString("nume"))
                        .setTelefon(jsonAccount.getString("telefon"))
                        .setVarsta(Integer.valueOf(jsonAccount.getString("varsta")))
                        .setMarcaMasina(jsonAccount.getString("marca_masina").equals("null") ? "" : jsonAccount.getString("marca_masina"))
                        .setModelMasina(jsonAccount.getString("model_masina").equals("null") ? "" : jsonAccount.getString("model_masina"))
                        .setAnFabricatie(Integer.valueOf(jsonAccount.getString("an_fabricatie").equals("null") ? "0" : jsonAccount.getString("an_fabricatie")))
                        .setExperientaAuto(jsonAccount.getString("experienta_auto").equals("null") ? "" : jsonAccount.getString("experienta_auto"))
                        .build();
                JSONArray jsonRecenzii = jsonAccount.getJSONArray("recenzii");
                for(int i=0;i<jsonRecenzii.length();i++){
                    JSONObject jsonRecenzie = jsonRecenzii.getJSONObject(i);
                    Recenzie recenzie = new Recenzie(
                            Integer.valueOf(jsonRecenzie.getString("id")),
                            jsonRecenzie.getString("nume"),
                            jsonRecenzie.getString("data"),
                            Float.valueOf(jsonRecenzie.getString("scor")),
                            jsonRecenzie.getString("descriere")
                    );
                    account.addRecenzie(recenzie);
                }
                if(account.getListaRecenzii().size() > 0) {
                    Collections.sort(account.getListaRecenzii(), new Comparator<Recenzie>() {
                        @Override
                        public int compare(Recenzie lhs, Recenzie rhs) {
                            if ((lhs.getData()).compareTo((rhs.getData())) > 0) {
                                return -1;
                            } else if (((lhs.getData()).compareTo((rhs.getData())) < 0)) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                int profilePhoto = jsonAccount.getInt("profile_photo");
                if(profilePhoto == 1) {
                    account.setHaveProfilPhoto(true);
                }else{
                    account.setHaveProfilPhoto(false);
                }
            }
        }catch (Exception ex) {
            Log.e("UtilizatorProfil", ex.getMessage());
        }
        return account;
    }
}
