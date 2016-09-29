package com.ekaterinachubarova.films1.rest.api;

import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;
import com.ekaterinachubarova.films1.serializer.FilmSerializer;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

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

    private FilmsApi filmsApi;

    public RetrofitService() {
        filmsApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()))
                .build()
                .create(FilmsApi.class);
    }

    public void getFilms() {
        filmsApi.getFilmsList().enqueue(new Callback<FilmsLab>() {

            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                EventBus.getDefault().post(new ReadingEvent(ReadingEvent.INFORMATION_FROM_NETWORK, response.body().getFilms()));
                FilmSerializer.deleteAllFilms();
                //FilmSerializer.saveFilms(response.body().getFilms());
            }

            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {
                EventBus.getDefault().post(new ReadingEvent(!ReadingEvent.INFORMATION_FROM_NETWORK, FilmSerializer.loadFilms()));
            }
        });
    }
}
