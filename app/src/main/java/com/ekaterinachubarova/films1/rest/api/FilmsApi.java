package com.ekaterinachubarova.films1.rest.api;

import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by ekaterinachubarova on 19.09.16.
 */
public interface FilmsApi {

    String BASE_URL = "http://www.mocky.io/v2/";

    @GET("57cffac8260000181e650041")
    Call<FilmsLab> getFilmsList();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
