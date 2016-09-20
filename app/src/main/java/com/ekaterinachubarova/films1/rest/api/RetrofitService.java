package com.ekaterinachubarova.films1.rest.api;

import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import dagger.Module;
import retrofit2.Call;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */

@Module
public class RetrofitService  {
    private FilmsLab filmsLab;
    private final static String TAG = "FilmService";

    public RetrofitService () {
        filmsLab = new FilmsLab();

    }
    public Call<FilmsLab> getFilms() {
        final FilmsApi filmsApi = FilmsApi.retrofit.create(FilmsApi.class);
        Call<FilmsLab> call = filmsApi.getFilmsList();
        return call;
    }


}
