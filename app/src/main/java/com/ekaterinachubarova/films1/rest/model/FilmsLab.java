package com.ekaterinachubarova.films1.rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
public class FilmsLab extends SugarRecord{
    @SerializedName("list")
    @Expose
    private List<Film> films = new ArrayList<Film>();

    public FilmsLab (List <Film> films) {
        this.films = films;
    }
}
