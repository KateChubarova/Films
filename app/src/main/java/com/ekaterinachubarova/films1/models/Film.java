package com.ekaterinachubarova.films1.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
@Table(name = "film")
public class Film extends SugarRecord implements Parcelable{


    private Long id;

    @SerializedName("name")
    @Expose
    private String nameRus;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("name_eng")
    @Expose
    private String nameEng;

    @SerializedName("premiere")
    @Expose
    private String premiere;

    @SerializedName("description")
    @Expose
    String description;


    public List<Film> results;


    //private Date date;

    public Film(String nameRus, String nameEng, String dateString, String description, String imageUrl) {
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.description = description;
        this.image = imageUrl;

    }


    protected Film(Parcel in) {
        nameRus = in.readString();
        image = in.readString();
        nameEng = in.readString();
        premiere = in.readString();
        description = in.readString();
        results = in.createTypedArrayList(Film.CREATOR);
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

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

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nameRus);
        parcel.writeString(image);
        parcel.writeString(nameEng);
        parcel.writeString(premiere);
        parcel.writeString(description);
        parcel.writeTypedList(results);
    }
}

