package com.ekaterinachubarova.films1.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.rest.model.Film;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 03.10.16.
 */

public class DescriptionFilmFragment extends Fragment{
    private static final String FILM = "film";

    @BindView(R.id.description)
    protected TextView description;
    @BindView(R.id.premire)
    protected TextView premiere;

    private Film film;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        film = getArguments().getParcelable(FILM);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.description_film_fragment, parent, false);
        final Typeface face = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.roboto_head));
        ButterKnife.bind(this, v);

        premiere.setTypeface(face);
        description.setTypeface(face);

        premiere.setText(film.getPremiere());
        description.setText(film.getDescription());

        return v;
    }


    public static DescriptionFilmFragment newInstance(Film film) {
        Bundle args = new Bundle();
        args.putParcelable(FILM, film);
        DescriptionFilmFragment descriptionFilmFragment = new DescriptionFilmFragment();
        descriptionFilmFragment.setArguments(args);
        return descriptionFilmFragment;
    }


}
