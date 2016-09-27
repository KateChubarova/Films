package com.ekaterinachubarova.films1.ui.activity;


import android.support.v4.app.Fragment;

import com.ekaterinachubarova.films1.ui.SingleBaseFragmentActivity;
import com.ekaterinachubarova.films1.ui.fragment.FilmsListFragment;

/**
 * Created by ekaterinachubarova on 08.09.16.
 */
public class FilmsListActivity extends SingleBaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new FilmsListFragment();
    }
}
