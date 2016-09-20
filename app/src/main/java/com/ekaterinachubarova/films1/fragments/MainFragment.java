package com.ekaterinachubarova.films1.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.activities.FilmActivity;
import com.ekaterinachubarova.films1.adapters.RVAdapter;
import com.ekaterinachubarova.films1.adapters.RecyclerItemClickListener;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.rv) RecyclerView rv;
    private RVAdapter rvAdapter;
    private FilmsLab filmsLab ;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), FilmActivity.class);
                        intent.putExtra(FilmFragment.FILM_PARS, filmsLab.getFilms().get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        return v;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetrofitService retrofitService = new RetrofitService();
        FilmsLab filmsLab = retrofitService.getFilms();

        //RVAdapter rvAdapter = new RVAdapter(filmsLab.getFilms(), getActivity());
        //rv.setAdapter(rvAdapter);

        setHasOptionsMenu(true);

    }


}
