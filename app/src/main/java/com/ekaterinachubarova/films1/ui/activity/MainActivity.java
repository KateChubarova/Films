package com.ekaterinachubarova.films1.ui.activity;

import android.app.Fragment;

import com.ekaterinachubarova.films1.ui.SingleBaseFragmentActivity;
import com.ekaterinachubarova.films1.ui.fragment.MainFragment;


/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class MainActivity extends SingleBaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
