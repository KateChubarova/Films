package com.ekaterinachubarova.films1.rest.api;

import android.content.Context;

import com.ekaterinachubarova.films1.eventbus.Event;
import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.eventbus.RefreshEvent;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;
import com.ekaterinachubarova.films1.serializer.FilmSerializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ekaterinachubarova.films1.rest.api.FilmsApi.BASE_URL;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */

public class RetrofitService {
    private static final String TAG = RetrofitService.class.getSimpleName();

    private FilmsApi filmsApi;
    private FilmSerializer filmSerializer;

    public RetrofitService(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                        .connectionPool(new ConnectionPool(5, FilmsApi.TIMEOUT, TimeUnit.SECONDS))
                        .connectTimeout(FilmsApi.TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(FilmsApi.TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(FilmsApi.TIMEOUT, TimeUnit.SECONDS)
                        .build();

        filmsApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()))
                .client(client)
                .build()
                .create(FilmsApi.class);

        filmSerializer = FilmSerializer.newInstance(context);
    }

    public void getFilms() {
        filmsApi.getFilmsList().enqueue(new Callback<FilmsLab>() {

            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                EventBus.getDefault().post(new ReadingEvent(Event.INFORMATION_FROM_NETWORK, response.body().getList()));
                filmSerializer.saveFilms(response.body().getList());
            }

            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {
                EventBus.getDefault().post(new ReadingEvent(!Event.INFORMATION_FROM_NETWORK, filmSerializer.loadFilms()));
            }
        });
    }

    public void getRefreshFilms () {
        filmsApi.getFilmsList().enqueue(new Callback<FilmsLab>() {
            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                EventBus.getDefault().post(new RefreshEvent(Event.INFORMATION_FROM_NETWORK, response.body().getList()));
            }

            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {
                EventBus.getDefault().post(new RefreshEvent(!Event.INFORMATION_FROM_NETWORK, filmSerializer.loadFilms()));
            }
        });
    }
}
