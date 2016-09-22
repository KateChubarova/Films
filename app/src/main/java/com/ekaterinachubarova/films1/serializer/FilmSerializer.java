package com.ekaterinachubarova.films1.serializer;

import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by ekaterinachubarova on 21.09.16.
 */

public class FilmSerializer {
    public static void saveFilms (List<Film> films) {
        System.out.println("save films function");
        /**
         * TODO : заменить на Android RX
         */
        for (int i=0; i<films.size(); i++) {
            films.get(i).setId(films.get(i).save());
        }
    }

    public static List<Film> loadFilms () {
        List<Film> films = Film.listAll(Film.class);
        return films;
    }


    public static void deleteFilm (Film film) {
        film.delete();
    }

    public static Film getFilm (Long id){
        System.out.println(id);
        return Film.findById(Film.class, id);
    }

    public static void deleteAllFilms () {
        Film.deleteAll(Film.class);
    }

    @Subscribe
    public static void eventSave (FilmsLab filmsLab) {
        System.out.println("Films saved");
        saveFilms(filmsLab.getFilms());
    }

}
