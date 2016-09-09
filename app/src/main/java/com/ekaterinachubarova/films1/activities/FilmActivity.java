package com.ekaterinachubarova.films1.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.fragments.FilmFragment;
import com.ekaterinachubarova.films1.models.Film;

public class FilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_activity);


        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);


        Film film = getIntent().getParcelableExtra(FilmFragment.FILM_PARS);



        if (fragment == null) {
            fragmentManager.beginTransaction().add(R.id.container, FilmFragment.newInstance(film)).commit();
        }
    }
}
