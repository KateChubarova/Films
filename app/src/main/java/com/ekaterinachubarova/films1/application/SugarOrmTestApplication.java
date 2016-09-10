package com.ekaterinachubarova.films1.application;

import android.content.res.Configuration;

import com.ekaterinachubarova.films1.models.Film;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.io.File;

/**
 * Created by ekaterinachubarova on 09.09.16.
 */
public class SugarOrmTestApplication extends SugarApp {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        SugarDb sugarDb = new SugarDb(getApplicationContext());
        new File(sugarDb.getDB().getPath()).delete();
        Film.findById(Film.class, (long) 1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

