package com.example.silviu086.licenta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
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
public class ContUploadPhotoTask extends AsyncTask<String, Integer, Bitmap> {
    private Context context;
    private String filePath;
    private TaskCompletedUpload taskCompletedUpload;

    public ContUploadPhotoTask(Context context, String filePath, TaskCompletedUpload taskCompletedUpload) {
        this.context = context;
        this.filePath = filePath;
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
            URL url = new URL(UrlLinks.URL_UPLOAD_PHOTO);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            /*
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath,
                    options);
            */

            Bitmap bitmap = Picasso.with(context).load(new File(filePath)).centerCrop().resize(100, 100).get();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            String encodedString = Base64.encodeToString(byte_arr, 0);

            String query = new Uri.Builder()
                    .appendQueryParameter("id", String.valueOf(NavigationActivity.account.getId()))
                    .appendQueryParameter("image", encodedString)
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
                return bitmap;
            }else{
                return null;
            }
        }catch (Exception ex){
            Log.e("ContUploadPhoto", ex.getMessage());
        }
        return null;
    }
}
