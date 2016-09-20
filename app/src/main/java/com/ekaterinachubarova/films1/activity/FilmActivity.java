package com.ekaterinachubarova.films1.activity;

import android.app.Fragment;

import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.fragment.FilmFragment;

public class FilmActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Film film = getIntent().getParcelableExtra(FilmFragment.FILM_PARS);
        return FilmFragment.newInstance(film);
    }

}
