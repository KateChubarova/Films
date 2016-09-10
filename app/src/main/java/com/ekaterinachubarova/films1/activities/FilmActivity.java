package com.ekaterinachubarova.films1.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.fragments.FilmFragment;
import com.ekaterinachubarova.films1.models.Film;

public class FilmActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Film film = getIntent().getParcelableExtra(FilmFragment.FILM_PARS);
        return new FilmFragment().newInstance(film);
    }

}
