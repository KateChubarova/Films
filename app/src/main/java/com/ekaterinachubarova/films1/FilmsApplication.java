package com.ekaterinachubarova.films1;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.config.ApplicationModule;
import com.ekaterinachubarova.films1.config.DaggerAppComponent;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

public class FilmsApplication extends Application{
    private AppComponent appComponent;

    public static AppComponent getAppComponent(Fragment context) {
        return ((FilmsApplication) context.getActivity().getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
