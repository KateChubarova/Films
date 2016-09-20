package com.ekaterinachubarova.films1.activity;

import android.app.Fragment;

import com.ekaterinachubarova.films1.fragment.MainFragment;


/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
