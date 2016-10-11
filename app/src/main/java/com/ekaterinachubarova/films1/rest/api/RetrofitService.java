package com.ekaterinachubarova.films1.rest.api;

import android.content.Context;

import com.ekaterinachubarova.films1.eventbus.Event;
import com.ekaterinachubarova.films1.eventbus.ReadingEvent;
import com.ekaterinachubarova.films1.eventbus.RefreshEvent;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;
import com.ekaterinachubarova.films1.serializer.FilmSerializer;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */

public class RetrofitService {
    private static final String TAG = RetrofitService.class.getSimpleName();

    private FilmsApi filmsApi;
    private FilmSerializer filmSerializer;

    public RetrofitService(Context context) {
        filmSerializer = FilmSerializer.newInstance(context);
    }

    public void setFilmsApi(FilmsApi filmsApi){
        this.filmsApi = filmsApi;
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
