package com.ekaterinachubarova.films1.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.activities.FilmActivity;
import com.ekaterinachubarova.films1.adapters.RVAdapter;
import com.ekaterinachubarova.films1.adapters.RecyclerItemClickListener;
import com.ekaterinachubarova.films1.models.FilmsLab;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;

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

        final Typeface face = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.roboto_head));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        if (isNetworkConnected()) {
            new AsyncHttpTask().execute(getString(R.string.url));
        } else {
            Toast.makeText(getActivity(), "Please, verify your internet connection", Toast.LENGTH_LONG).show();
        }

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

    public class AsyncHttpTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            Reader answer = null;

            Request request = new Request.Builder()
                    .url(params[0])
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                answer =  response.body().charStream();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                filmsLab = gson.fromJson(answer, FilmsLab.class);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rvAdapter = new RVAdapter(filmsLab.getFilms(), getActivity());
            rv.setAdapter(rvAdapter);

            filmsLab.saveFilms();
            //filmsLab.coutAllFilms();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
