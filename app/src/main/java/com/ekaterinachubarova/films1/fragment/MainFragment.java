package com.ekaterinachubarova.films1.fragment;

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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public class MainFragment extends BaseFragment{
    @BindView(R.id.rv) RecyclerView rv;
    private RVAdapter rvAdapter;
    private List<Film> films;

    @Inject
    protected RetrofitService filmService;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.films_fragment, parent, false);
        ButterKnife.bind(this, v);
        setUpComponent(FilmsApplication.getAppComponent(this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        filmService.getFilms();


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

    @Subscribe
    public void setAdapter (FilmsLab message) {
        films = message.getFilms();
        rvAdapter = new RVAdapter(films, getActivity());
        rv.setAdapter(rvAdapter);
    }


    public void setUpComponent(AppComponent appComponent) {
        appComponent.inject(this);
    }
}
