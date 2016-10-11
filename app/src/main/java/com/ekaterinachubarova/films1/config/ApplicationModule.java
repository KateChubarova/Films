package com.ekaterinachubarova.films1.config;

import android.app.Application;
import android.content.Context;

import com.ekaterinachubarova.films1.rest.api.FilmsApi;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ekaterinachubarova.films1.rest.api.FilmsApi.BASE_URL;

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
    public RetrofitService provideFilmsApi() {
        RetrofitService retrofitService = new RetrofitService(application.getApplicationContext());

        OkHttpClient client = new OkHttpClient.Builder()
                        .connectionPool(new ConnectionPool(5, FilmsApi.TIMEOUT, TimeUnit.SECONDS))
                        .connectTimeout(FilmsApi.TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(FilmsApi.TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(FilmsApi.TIMEOUT, TimeUnit.SECONDS)
                        .build();

        retrofitService.setFilmsApi(new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()))
                .client(client)
                .build()
                .create(FilmsApi.class));
        return retrofitService;
    }
}

