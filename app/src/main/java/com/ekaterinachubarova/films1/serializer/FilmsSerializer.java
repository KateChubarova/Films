package com.ekaterinachubarova.films1.serializer;

import com.ekaterinachubarova.films1.rest.model.Film;
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

}
