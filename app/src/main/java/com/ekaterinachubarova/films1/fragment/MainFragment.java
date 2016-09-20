package com.ekaterinachubarova.films1.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekaterinachubarova.films1.FilmsApplication;
import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.activity.FilmActivity;
import com.ekaterinachubarova.films1.adapter.RVAdapter;
import com.ekaterinachubarova.films1.adapter.RecyclerItemClickListener;
import com.ekaterinachubarova.films1.config.AppComponent;
import com.ekaterinachubarova.films1.rest.api.RetrofitService;
import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.rest.model.FilmsLab;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.rv) RecyclerView rv;
    private RVAdapter rvAdapter;
    private List<Film> films;

    @Inject
    protected RetrofitService filmService;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);
        setUpComponent(FilmsApplication.getAppComponent(this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        Call<FilmsLab> call = filmService.getFilms();

        call.enqueue(new Callback<FilmsLab>() {
            @Override
            public void onResponse(Call<FilmsLab> call, Response<FilmsLab> response) {
                films = response.body().getFilms();
                rvAdapter = new RVAdapter(films, getActivity());
                rv.setAdapter(rvAdapter);
            }
            @Override
            public void onFailure(Call<FilmsLab> call, Throwable t) {

            }
        });

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getActivity(), FilmActivity.class);
                        intent.putExtra(FilmFragment.FILM_PARS, films.get(position));
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
        setHasOptionsMenu(true);
    }

    protected void setUpComponent(AppComponent appComponent) {
        appComponent.inject(this);
    }

}
