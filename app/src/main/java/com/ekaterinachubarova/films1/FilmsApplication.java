package com.ekaterinachubarova.films1;

import android.content.Context;

import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.config.ApplicationModule;
import com.ekaterinachubarova.films1.config.DaggerAppComponent;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

public class FilmsApplication extends SugarApp {
    private AppComponent appComponent;

    public static AppComponent getAppComponent(Context context) {
        return ((FilmsApplication) context.getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Film.findById(Film.class, (long) 1);
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        SugarContext.init(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
