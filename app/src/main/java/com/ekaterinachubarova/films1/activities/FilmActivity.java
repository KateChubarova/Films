package com.ekaterinachubarova.films1.activities;

import android.app.Fragment;
import android.view.Menu;

import com.ekaterinachubarova.films1.fragments.FilmFragment;
import com.ekaterinachubarova.films1.rest.model.Film;

public class FilmActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Film film = getIntent().getParcelableExtra(FilmFragment.FILM_PARS);
        return FilmFragment.newInstance(film);
    }

}
