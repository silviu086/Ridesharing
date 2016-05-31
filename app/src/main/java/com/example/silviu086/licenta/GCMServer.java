package com.example.silviu086.licenta;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.ContextThemeWrapper;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Silviu086 on 29.05.2016.
 */
public class GCMServer {
    public static void register(String gcm_regid, String account_id){
        String serverUrl = UrlLinks.URL_GCM_REGISTER;
        Map<String, String> params = new HashMap<>();
        params.put("gcm_regid", gcm_regid);
        params.put("account_id", account_id);

        try{
            post(serverUrl, params);
        }catch (IOException ex){
        }
    }

    public static void unregister(String gcm_regid){
        //String serverUrl = UrlLinks.URL_GCM_UNREGISTER;
        //Map<String, String> params = new HashMap<>();
        //params.put("gcm_regid", gcm_regid);
        try{
            //post(serverUrl, params);
        }catch (Exception ex){
        }
    }

    private static void post(String serverUrl, Map<String, String> params) throws IOException{
        URL url;
        HttpURLConnection con = null;
        try{
            url = new URL(serverUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.connect();

            String query = new Uri.Builder()
                    .appendQueryParameter("gcm_regid", params.get("gcm_regid"))
                    .appendQueryParameter("account_id", params.get("account_id"))
                    .build().getEncodedQuery();
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.close();
            os.close();
            int status = con.getResponseCode();
            if(status != 200){
                throw new IOException("Post failed with error code " + status);
            }

            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line=reader.readLine()) !=null){
                sb.append(line);
            }
            Log.i("Result", sb.toString());
        }finally {
            if(con != null){
                con.disconnect();
            }
        }
    }
}
