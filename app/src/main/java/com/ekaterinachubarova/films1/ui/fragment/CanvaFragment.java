package com.ekaterinachubarova.films1.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekaterinachubarova.films1.R;

/**
 * Created by ekaterinachubarova on 03.10.16.
 */

public class CanvaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.canva_fragment, parent, false);
    }
}
