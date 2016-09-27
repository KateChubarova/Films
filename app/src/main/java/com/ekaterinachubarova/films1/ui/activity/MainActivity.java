package com.ekaterinachubarova.films1.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.adapter.NavBarPagerAdapter;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by ekaterinachubarova on 24.09.16.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout) protected DrawerLayout mDrawer;
    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.nvView) protected NavigationView nvDrawer;
    @BindView(R.id.pager) protected ViewPager pager;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        setupDrawerContent(nvDrawer);
        setPhotoAndName (); ;
        pager.setAdapter(new NavBarPagerAdapter(getSupportFragmentManager()));
    }

    private void setPhotoAndName () {
        Profile profile = Profile.getCurrentProfile();
        View hView =  nvDrawer.getHeaderView(0);
        ImageView imageView = (ImageView) hView.findViewById(R.id.photo);
        Picasso.with(this).load(profile.getProfilePictureUri(200, 200)).
                transform(new CropCircleTransformation()).into(imageView);
        TextView textView = (TextView)hView.findViewById(R.id.name);
        textView.setText(profile.getName());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.list_of_films:
                pager.setCurrentItem(NavBarPagerAdapter.LIST_OF_FILMS, false);
                break;
            case R.id.empty_fragment:
                pager.setCurrentItem(NavBarPagerAdapter.EMPTY_FRAGMENT, false);
                break;
            default:
                pager.setCurrentItem(NavBarPagerAdapter.LIST_OF_FILMS);
                break;
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed () {
        /**TODO: make onBackPressed
         *
         */
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers();
        } else {
            super.onBackPressed();
            finish();
        }
    }
}

