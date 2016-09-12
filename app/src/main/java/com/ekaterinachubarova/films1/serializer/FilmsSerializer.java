package com.ekaterinachubarova.films1.serializer;

import android.os.AsyncTask;

import com.ekaterinachubarova.films1.adapters.RVAdapter;
import com.ekaterinachubarova.films1.models.Film;
import com.ekaterinachubarova.films1.models.FilmsLab;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orm.SugarRecord;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by ekaterinachubarova on 12.09.16.
 */
public class FilmsSerializer{
    public void saveFilms (List <Film> films){
        for (int i=0; i<films.size(); i++) {
            films.get(i).save();
        }
    }

    public List <Film> getFilms (){
        List<Film> films = Film.listAll(Film.class);
        return films;
    }

    public Film getFilm (long id) {
        Film film = Film.findById(Film.class, id);
        return film;
    }

    public void deleteFilm (long id) {
        Film film =  Film.findById(Film.class, 1);
        film.delete();
    }

    public void deleteFilms () {
        Film.deleteAll(Film.class);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {
            //FilmsLab filmsLab = FilmsLab.getFilms();
            OkHttpClient client = new OkHttpClient();
            Reader answer = null;

            Request request = new Request.Builder()
                    .url(params[0])
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                answer =  response.body().charStream();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                //filmsLab = gson.fromJson(answer, FilmsLab.class);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //rvAdapter = new RVAdapter(films, getActivity());
            //rv.setAdapter(rvAdapter);

        }
    }
}
