package com.ekaterinachubarova.films1.rest.api;

import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;
import com.ekaterinachubarova.films1.serializer.FilmSerializer;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */

public class RetrofitService  {
    public RetrofitService () {
    }

    public void getFilms() {
        final FilmsApi filmsApi = FilmsApi.retrofit.create(FilmsApi.class);
        Call<FilmsLab> call = filmsApi.getFilmsList();
        call.enqueue(new Callback<FilmsLab>() {
            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                EventBus.getDefault().post(new ReadingEvent(response.body().getFilms(), ReadingEvent.INFORMATION_FROM_NETWORK));
                FilmSerializer.deleteAllFilms();
                FilmSerializer.saveFilms(response.body().getFilms());
            }
            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {
                EventBus.getDefault().post(new ReadingEvent(FilmSerializer.loadFilms(), ReadingEvent.INFORMATION_FROM_DATABASE));
            }
        });
    }
}
