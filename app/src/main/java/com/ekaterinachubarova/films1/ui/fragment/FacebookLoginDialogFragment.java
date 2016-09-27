package com.ekaterinachubarova.films1.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.activity.SplashActivity;

/**
 * Created by ekaterinachubarova on 27.09.16.
 */

public class FacebookLoginDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.facebook_login_fragment, container, false);

        Button loginViaFB = (Button)v.findViewById(R.id.login_via_facebook);
        Button cancel = (Button)v.findViewById(R.id.cancel);

        loginViaFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SplashActivity)getActivity()).login();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

}

