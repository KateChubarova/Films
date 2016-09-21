package com.ekaterinachubarova.films1.config;

import android.app.Application;
import android.content.Context;

import com.ekaterinachubarova.films1.rest.api.RetrofitService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public RetrofitService provideWiseApi() {
        return new RetrofitService();
    }
}

