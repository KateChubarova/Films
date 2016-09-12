package com.ekaterinachubarova.films1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ekaterinachubarova on 12.09.16.
 */
public class FilmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntent = new Intent(context, FilmsUpdateService.class);
        context.startActivity(startIntent);
    }
}
