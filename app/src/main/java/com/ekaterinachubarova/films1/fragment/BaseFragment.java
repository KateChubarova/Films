package com.ekaterinachubarova.films1.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.ekaterinachubarova.films1.config.AppComponent;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

public abstract class BaseFragment extends Fragment {

    protected void setUpComponent(AppComponent appComponent) {
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
