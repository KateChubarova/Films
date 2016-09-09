package com.ekaterinachubarova.films1.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.fragments.FilmFragment;

public class FilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_activity);


        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        int id = Integer.parseInt(getIntent().getStringExtra(FilmFragment.POSITION));


        if (fragment == null) {
            //fragment = new FilmFragment();
            fragmentManager.beginTransaction().add(R.id.container, FilmFragment.newInstance(id)).commit();
        }
    }
}
