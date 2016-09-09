package com.ekaterinachubarova.films1.models;

import android.content.Context;


import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */

public class Film{

    @SerializedName("name")
    private String nameRus;

    @SerializedName("image")
    private String image;

    @SerializedName("name_eng")
    private String nameEng;

    @SerializedName("premiere")
    private String premiere;

    @SerializedName("description")
    String description;


    public List<Film> results;


    //private Date date;

    public Film(String nameRus, String nameEng, String dateString, String description, String imageUrl) {
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.description = description;
        this.image = imageUrl;

    }


    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }
}

