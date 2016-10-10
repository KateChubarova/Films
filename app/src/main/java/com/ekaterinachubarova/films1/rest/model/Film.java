package com.ekaterinachubarova.films1.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
@Getter
@Setter
@DatabaseTable(tableName = "films")
public class Film implements Parcelable {
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
    @Expose
    @DatabaseField
    String description;
    @DatabaseField(generatedId = true)
    private Long id;
    @Expose
    @DatabaseField
    private String name;

    @Expose
    @DatabaseField
    private String image;

    @Expose
    @DatabaseField
    private String nameEng;

    @Expose
    @DatabaseField
    private String premiere;

    protected Film(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
        nameEng = in.readString();
        premiere = in.readString();
        description = in.readString();
    }


    private Film() {

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

}

