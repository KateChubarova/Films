package com.ekaterinachubarova.films1.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.adapters.RVAdapter;

import com.ekaterinachubarova.films1.adapters.RecyclerItemClickListener;
import com.ekaterinachubarova.films1.fragments.FilmFragment;
import com.ekaterinachubarova.films1.fragments.MainFragment;
import com.ekaterinachubarova.films1.models.FilmsLab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;


/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
