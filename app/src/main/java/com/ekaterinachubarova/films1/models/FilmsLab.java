package com.ekaterinachubarova.films1.models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class FilmsLab extends SugarRecord{
    @SerializedName("list")
    @Expose
    private List<Film> films = new ArrayList<Film>();

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public void saveFilms () {
        for (int i=0; i<films.size(); i++) {
            films.get(i).setId(films.get(i).save());
        }
    }

//    public void coutAllFilms (){
//
//        List<Film> books = Film.listAll(Film.class);
//
//        for (int i=0; i<books.size(); i++) {
//            System.out.println(books.get(i).getNameRus());
//        }
//    }
}
