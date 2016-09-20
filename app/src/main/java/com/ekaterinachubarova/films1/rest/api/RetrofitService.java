package com.ekaterinachubarova.films1.rest.api;

import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */

@Module
public class RetrofitService  {
    private FilmsLab filmsLab;
    private final static String TAG = "FilmService";

    public RetrofitService () {
        //filmsLab = new FilmsLab();

    }
    public void getFilms() {
        final FilmsApi filmsApi = FilmsApi.retrofit.create(FilmsApi.class);
        Call<FilmsLab> call = filmsApi.getFilmsList();

        //Call<FilmsLab> call = filmService.getFilms();

        call.enqueue(new Callback<FilmsLab>() {
            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                EventBus.getDefault().post(new FilmsLab(response.body().getFilms()));
            }
            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {

            }
        });
        //EventBus.getDefault().post(new FilmsLab());

    }


}
