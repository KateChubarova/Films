package com.ekaterinachubarova.films1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ekaterinachubarova.films1.serializer.FilmSerializer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ekaterinachubarova on 06.10.16.
 */

public class FilmCountService extends Service {
    public static final long NOTIFY_INTERVAL = 1000 * 60 * 5;

    private Handler mHandler = new Handler();
    private Timer mTimer = null;


    @Override
    public void onCreate() {
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new CountOfFilmDisplayTask(), 0,
                NOTIFY_INTERVAL);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class CountOfFilmDisplayTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Count of films: " + FilmSerializer.getCountOfFilms(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
