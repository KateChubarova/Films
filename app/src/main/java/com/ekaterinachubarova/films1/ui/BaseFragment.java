package com.ekaterinachubarova.films1.ui;

import android.app.Fragment;
import android.os.Bundle;

import com.ekaterinachubarova.films1.config.AppComponent;

import org.greenrobot.eventbus.EventBus;

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
}
