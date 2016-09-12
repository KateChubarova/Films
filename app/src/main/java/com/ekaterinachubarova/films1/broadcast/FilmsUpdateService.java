package com.ekaterinachubarova.films1.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ekaterinachubarova.films1.models.Film;

import java.util.List;

/**
 * Created by ekaterinachubarova on 12.09.16.
 */
public class FilmsUpdateService extends Service{

    public static String TAG = "FILMS_UPDATE_SERVICE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void saveFilms (List<Film> films){
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
}
