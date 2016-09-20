package com.ekaterinachubarova.films1;

import android.app.Application;
import android.content.Context;

import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.config.ApplicationModule;
import com.ekaterinachubarova.films1.config.DaggerAppComponent;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

public class FilmsApplication extends Application{
    private AppComponent appComponent;

    public static AppComponent getAppComponent(Context context) {
        return ((FilmsApplication) context.getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
