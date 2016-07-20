/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.silviu086.licenta;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        try {
            String id = data.getString("id_account");
            String message = data.getString("message");
            String idCalatorie = data.getString("id_calatorie");
            String type = data.getString("type");
            sendNotification(id, message, idCalatorie, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(String idAccount, String message, String idCalatorie, String type) throws IOException {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap bitmap = null;
        File mypath=new File(getFilesDir(),"photo_" + idAccount + ".png");
        if (mypath.exists()) {
            bitmap = BitmapFactory.decodeFile(mypath.getAbsolutePath());
        }
        Notification.Builder notificationBuilder;
        if(bitmap != null){
            notificationBuilder = new Notification.Builder(this)
                    .setContentTitle("Ridesharing - Calatoria " + idCalatorie)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.ridesharing)
                    .setTicker("Notificare de la Ridesharing")
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[]{500, 500})
                    .setLights(Color.YELLOW, 1500, 1500)
                    .setContentIntent(pendingIntent);
        }else{
            notificationBuilder = new Notification.Builder(this)
                    .setContentTitle("Ridesharing - Calatoria " + idCalatorie)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ridesharing)
                    .setTicker("Notificare de la Ridesharing")
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[]{500, 500})
                    .setLights(Color.YELLOW, 1500, 1500)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Integer.valueOf(type), notificationBuilder.build());

    }
}
