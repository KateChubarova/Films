package com.ekaterinachubarova.films1.rest.model;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
@Getter
@Setter
public class FilmsLab extends SugarRecord {
    //public static int countOfFilms;

    @Expose
    private List<Film> list = new ArrayList<>();



    public FilmsLab(List<Film> films) {
        this.list = films;
    }

}
