package com.ekaterinachubarova.films1.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.models.Film;
import com.ekaterinachubarova.films1.models.FilmsLab;
import com.squareup.picasso.Picasso;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class FilmFragment extends Fragment {
    private Film film;
    public static final String POSITION = "position";

    public static FilmFragment newInstance (int id) {
        Bundle args = new Bundle();
        args.putSerializable(POSITION, id);
        FilmFragment filmFragment = new FilmFragment();
        filmFragment.setArguments(args);
        return filmFragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = (int)getArguments().getSerializable(POSITION);
        System.out.println(id);
        //film = FilmsLab.get().getFilm(id);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.film_fragment, parent, false);

        final Typeface face = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.roboto_head));

        TextView enName = (TextView)v.findViewById(R.id.en_name_film);
        enName.setTypeface(face);
        TextView rusName = (TextView)v.findViewById(R.id.rus_name_film);
        rusName.setTypeface(face);
        ImageView filmImage = (ImageView)v.findViewById(R.id.film_image);
        TextView date = (TextView)v.findViewById(R.id.date_film);
        date.setTypeface(face);
        TextView description = (TextView)v.findViewById(R.id.description_film);
        description.setTypeface(face);


        enName.setText(film.getNameEng());
        rusName.setText(film.getNameRus());
        date.setText(film.getPremiere());
        description.setText(film.getDescription());


        Picasso.with(getActivity())
                .load(film.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(filmImage);;

        return v;
    }
}
