package com.example.silviu086.licenta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Silviu086 on 09.07.2016.
 */
public class ContDownloadPhotoTask extends AsyncTask<String, Integer, Bitmap> {
    private Context context;
    private int idAccount;
    private TaskCompletedUpload taskCompletedUpload;

    public ContDownloadPhotoTask(Context context, int idAccount, TaskCompletedUpload taskCompletedUpload) {
        this.context = context;
        this.idAccount = idAccount;
        this.taskCompletedUpload = taskCompletedUpload;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        taskCompletedUpload.onTaskCompleted(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try{
            URL url = new URL(UrlLinks.URL_DOWNLOAD_PHOTO);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("id", String.valueOf(idAccount))
                    .build().getEncodedQuery();

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
            while( (line = reader.readLine())!=null){
                sb.append(line);
            }
            reader.close();
            is.close();
            connection.disconnect();

            JSONObject json = new JSONObject(sb.toString());
            int success = json.getInt("success");
            if(success == 1) {
                String bitmapString = json.getString("image");
                byte [] encodeByte=Base64.decode(bitmapString,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            }else{
                return null;
            }
        }catch (Exception ex){
            Log.e("ContDownloadPhoto", ex.getMessage());
        }
        return null;
    }
}
