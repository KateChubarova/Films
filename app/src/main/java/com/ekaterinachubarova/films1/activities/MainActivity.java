package com.ekaterinachubarova.films1.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.adapters.RVAdapter;

import com.ekaterinachubarova.films1.adapters.RecyclerItemClickListener;
import com.ekaterinachubarova.films1.fragments.FilmFragment;
import com.ekaterinachubarova.films1.models.Film;
import com.ekaterinachubarova.films1.models.FilmsLab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.util.List;


/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RVAdapter rvAdapter;
    private FilmsLab filmsLab ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.films_activity);


        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        if (isNetworkConnected()) {
            new AsyncHttpTask().execute(getString(R.string.url));
        } else {
            Toast.makeText(this, "Please, verify your internet connection", Toast.LENGTH_LONG).show();
        }

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getApplicationContext(), FilmActivity.class);
                        intent.putExtra(FilmFragment.FILM_PARS, filmsLab.getFilms().get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
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
            rvAdapter = new RVAdapter(filmsLab.getFilms(), getApplicationContext());
            rv.setAdapter(rvAdapter);
        }
        
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }



}
