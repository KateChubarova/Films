package com.ekaterinachubarova.films1;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.config.ApplicationModule;
import com.ekaterinachubarova.films1.config.DaggerAppComponent;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

public class FilmsApplication extends SugarApp{
    private AppComponent appComponent;
    private static Context instance;

    public static AppComponent getAppComponent(Fragment context) {
        return ((FilmsApplication) context.getActivity().getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Film.findById(Film.class, (long) 1);
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static Context get() {
        return instance;
    }

}
