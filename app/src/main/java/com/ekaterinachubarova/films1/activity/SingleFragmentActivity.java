package com.ekaterinachubarova.films1.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.ekaterinachubarova.films1.R;

/**
 * Created by ekaterinachubarova on 10.09.16.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
