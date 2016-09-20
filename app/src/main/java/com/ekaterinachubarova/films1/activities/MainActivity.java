package com.ekaterinachubarova.films1.activities;

import android.app.Fragment;

import com.ekaterinachubarova.films1.fragments.MainFragment;

import butterknife.ButterKnife;


/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class MainActivity extends SingleFragmentActivity {
    //protected OnBackPressedListener onBackPressedListener;

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

}
