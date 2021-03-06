package com.example.silviu086.licenta;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Silviu086 on 01.04.2016.
 */
public class LoginTask extends AsyncTask<String, Integer, String> {
    private Context mContext;
    private Button mButton;
    private ProgressBar mProgress;
    private Account mAccount;
    private TaskCompleted mTaskCompleted;
    String messageServer;

    public LoginTask(Context context, Button button, ProgressBar progress, Account account, TaskCompleted taskCompleted) {
        this.mContext = context;
        this.mButton = button;
        this.mProgress = progress;
        this.mAccount = account;
        mTaskCompleted = taskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mButton.setText("Se logheaza...");
        mButton.setBackgroundColor(mContext.getResources().getColor(R.color.colorAzure));
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            URL url = new URL(UrlLinks.URL_LOGIN);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            String query;

            //SETARE PARAMETRII
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", mAccount.getEmail().toString())
                    .appendQueryParameter("password", mAccount.getParola().toString());
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
                return json.toString();

            }else{
                messageServer = json.getString("message");
                new Handler(mContext.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(" ");
                        builder.setSpan(new ImageSpan(mContext, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                        builder.append(" " + messageServer);
                        Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                    }
                });
                return "failed";
            }
        }catch (Exception ex) {
            new Handler(mContext.getMainLooper()).post(new Runnable() {
                public void run() {
                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    builder.append(" ");
                    builder.setSpan(new ImageSpan(mContext, R.drawable.snackbar_fail), builder.length() - 1, builder.length(), 0);
                    builder.append(" Nu ma pot conecta la server!");
                    Snackbar.make(MainActivity.parentView, builder, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
        return "null";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mTaskCompleted.onTaskCompleted(s);
    }

}
