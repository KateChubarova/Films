package com.ekaterinachubarova.films1.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ekaterinachubarova.films1.ui.fragment.EmptyFragment;
import com.ekaterinachubarova.films1.ui.fragment.FilmsListFragment;

/**
 * Created by ekaterinachubarova on 24.09.16.
 */

public class NavBarPagerAdapter extends FragmentPagerAdapter {

    public static final int EMPTY_FRAGMENT = 1;
    public static final int LIST_OF_FILMS = 0;

    public NavBarPagerAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return (2);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case LIST_OF_FILMS:
                return new FilmsListFragment();
            case EMPTY_FRAGMENT:
                return new EmptyFragment();
            default:
                return new FilmsListFragment();
        }
    }
}

