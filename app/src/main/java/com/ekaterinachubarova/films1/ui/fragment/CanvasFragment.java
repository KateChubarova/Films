package com.ekaterinachubarova.films1.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.view.CursorClock;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 03.10.16.
 */

public class CanvasFragment extends Fragment {

    @BindView(R.id.cursor_clock)
    protected CursorClock cursorClock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.canva_fragment, parent, false);
        ButterKnife.bind(this,v);
        return v;
    }

    public void startAnim () {
        cursorClock.startAnim(randomize());
    }

    private double randomize(){
        return (Math.random()*1000);
    }
}
