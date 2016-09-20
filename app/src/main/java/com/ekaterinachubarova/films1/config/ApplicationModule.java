package com.ekaterinachubarova.films1.config;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.ekaterinachubarova.films1.rest.api.FilmsApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

@Module
@SuppressWarnings("unused")
public class ApplicationModule {

    private static final int TIMEOUT = 30;
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

//    @Provides
//    @Singleton
//    public ApplicationPreferences provideApplicationPreferences(Gson gson) {
//        return new ApplicationPreferences(PreferenceManager.getDefaultSharedPreferences(application));
//    }

//    @Provides
//    @Singleton
//    public VoterService provideWiseService(Context context, VoterApi api, ApplicationPreferences preferences,
//                                           Repository repository) {
//        return new VoterService(context, api, preferences, repository);
//    }

    @Provides
    @Singleton
    public FilmsApi provideWiseApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(FilmsApi.BASE_URL)
                .client(builder.build())
                .build();
        return restAdapter.create(FilmsApi.class);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

//    @Provides
//    @Singleton
//    public Repository provideRepository(ApplicationPreferences preferences) {
//        return new Repository(preferences);
//    }
}

