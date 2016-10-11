package com.ekaterinachubarova.films1.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class WiFiReceiver extends BroadcastReceiver {
    NotificationManager notificationManager;

    public WiFiReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!wifi.isConnectedOrConnecting()) {
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class)
                    , PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context)
                    .setContentTitle(context.getString(R.string.notification_title))
                    .setStyle(new Notification.BigTextStyle()
                            .bigText("Wi-fi status was changed."))
                    .addAction(R.mipmap.videocamera, "Open the app", pendingIntent)
                    .setSmallIcon(R.drawable.videocamera)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            notificationManager.notify(3, notification);
        } else {
            notificationManager.cancel(3);
        }
    }
}
