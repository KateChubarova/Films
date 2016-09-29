package com.ekaterinachubarova.films1.ui.activity;


import android.support.v4.app.Fragment;

import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.ui.SingleBaseFragmentActivity;
import com.ekaterinachubarova.films1.ui.fragment.FilmFragment;

public class FilmActivity extends SingleBaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Film film = getIntent().getParcelableExtra(FilmFragment.FILM_PARS);
        return FilmFragment.newInstance(film);
    }

}
