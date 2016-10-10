package com.ekaterinachubarova.films1.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ekaterinachubarova.films1.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class WiFiReceiver extends BroadcastReceiver {
    public WiFiReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Films wi-fi")
                .setContentText("Wi-fi status was changed.")
                .setFullScreenIntent(pIntent, true)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSmallIcon(R.drawable.videocamera);

        Notification notification = new Notification.InboxStyle(builder)
                .setSummaryText("Wi-fi status.").build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(3, notification);


    }
}
