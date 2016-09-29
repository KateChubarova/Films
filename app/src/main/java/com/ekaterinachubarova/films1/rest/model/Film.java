package com.ekaterinachubarova.films1.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
@Getter
@Setter
@Table
public class Film extends SugarRecord implements Parcelable {
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
    @SerializedName("description")
    @Expose
    String description;
    private Long id;
    @Expose
    @SerializedName("name")
    private String name;
    //@SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name_eng")
    @Expose
    private String nameEng;
    @SerializedName("premiere")
    @Expose
    private String premiere;

    protected Film(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
        nameEng = in.readString();
        premiere = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(nameEng);
        parcel.writeString(premiere);
        parcel.writeString(description);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}

