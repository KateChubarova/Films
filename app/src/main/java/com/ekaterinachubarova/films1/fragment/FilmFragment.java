package com.ekaterinachubarova.films1.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class FilmFragment extends Fragment {
    private Film film;
    public static final String FILM_PARS = "FILM";

    @BindView(R.id.name) TextView name;
    @BindView(R.id.big_cover) ImageView filmImage;
    @BindView(R.id.premire) TextView date;
    @BindView(R.id.description) TextView description;


    public static FilmFragment newInstance (Film film) {
        Bundle args = new Bundle();
        args.putParcelable(FILM_PARS, film);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(args);
        return filmFragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        film = getArguments().getParcelable(FILM_PARS);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.film_fragment, parent, false);
        ButterKnife.bind(this, v);

        final Typeface face = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.roboto_head));

        name.setTypeface(face);
        date.setTypeface(face);
        description.setTypeface(face);
        name.setText(film.getNameEng() + " - " + film.getNameRus());
        date.setText(film.getPremiere());
        description.setText(film.getDescription());


        Picasso.with(getActivity())
                .load(film.getImageUrl())
                .placeholder(R.drawable.videocamera)
                .error(R.drawable.videocamera)
                .into(filmImage);;

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
        }
        return false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!getResources().getBoolean(R.bool.is_tablet)) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
            }
        }
    }
}
