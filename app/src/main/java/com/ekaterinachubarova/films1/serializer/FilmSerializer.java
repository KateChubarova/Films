package com.ekaterinachubarova.films1.serializer;

import android.content.Context;

import com.ekaterinachubarova.films1.rest.model.Film;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ekaterinachubarova on 21.09.16.
 */

public class FilmSerializer {
    private static Dao <Film, Long> filmsDao;
    private static FilmSerializer filmSerializer;

    private FilmSerializer (Context context){
        FilmsDatabaseOpenHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(context,
                FilmsDatabaseOpenHelper.class);
        try {
            filmsDao = todoOpenDatabaseHelper.getDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static FilmSerializer newInstance (Context context) {
        if (filmSerializer == null)
            filmSerializer = new FilmSerializer(context);
        return filmSerializer;
    }

    public static void saveFilms(List<Film> films) {
        for (int i=0; i<films.size(); i++) {
            try {
                filmsDao.create(films.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Film> loadFilms() {
        try {
            return filmsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }


    public static void deleteFilm(Film film) {

    }

    public static Film getFilm(Long id) {
        return null;
    }

    public static void deleteAllFilms() {

    }

    public static int getCountOfFilms(){
        return loadFilms().size();
    }

}
