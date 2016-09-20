package com.ekaterinachubarova.films1.rest.api;

import android.widget.Toast;

import com.ekaterinachubarova.films1.adapters.RVAdapter;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */
public class RetrofitService implements FilmService {

    @Override
    public FilmsLab getFilms() {
        final FilmsLab filmsLab = new FilmsLab();

        final FilmsApi filmsApi = FilmsApi.retrofit.create(FilmsApi.class);
        Call<FilmsLab> call = filmsApi.getFilmsList();

        call.enqueue(new Callback<FilmsLab>() {
            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                filmsLab.setFilms(response.body().getFilms());
            }
            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {

            }
        });
        return filmsLab;
    }


}
