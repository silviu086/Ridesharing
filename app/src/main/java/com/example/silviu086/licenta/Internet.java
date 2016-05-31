package com.example.silviu086.licenta;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Silviu086 on 01.04.2016.
 */
public class Internet {
    public static Boolean haveInternet(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
