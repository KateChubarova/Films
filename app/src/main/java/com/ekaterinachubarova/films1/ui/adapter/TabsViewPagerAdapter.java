package com.ekaterinachubarova.films1.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ekaterinachubarova.films1.rest.model.Film;
import com.ekaterinachubarova.films1.ui.fragment.CanvaFragment;
import com.ekaterinachubarova.films1.ui.fragment.DescriptionFilmFragment;

/**
 * Created by ekaterinachubarova on 03.10.16.
 */

public class TabsViewPagerAdapter extends FragmentPagerAdapter {

    private static final int CANVA_FRAGMENT = 1;
    private static final int DESCRIPTION_FRAGMENT = 0;

    private Film film;

    public TabsViewPagerAdapter(FragmentManager fragmentManager, Film film) {
        super(fragmentManager);
        this.film = film;
    }

    @Override
    public int getCount() {
        return (2);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CANVA_FRAGMENT:
                return new CanvaFragment();
            case DESCRIPTION_FRAGMENT:
                return DescriptionFilmFragment.newInstance(film);
            default:
                return DescriptionFilmFragment.newInstance(film);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case CANVA_FRAGMENT:
                return "Canva fragment";
            case DESCRIPTION_FRAGMENT:
                return "Film's description";
            default:
                return "Film's description";
        }
    }
}
