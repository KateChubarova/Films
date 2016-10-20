package com.ekaterinachubarova.films1.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.adapter.NavBarPagerAdapter;
import com.ekaterinachubarova.films1.ui.adapter.SwipeEnableViewPager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by ekaterinachubarova on 24.09.16.
 */

public class MainActivity extends AppCompatActivity implements ToolTipView.OnToolTipViewClickedListener{
    public static final String BITMAP_STORAGE_KEY = "BITMAP STORAGE KEY";
    private static final int CHOOSE_PICTURE = 1;
    private static final int TAKE_PICTURE = 2;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.nvView)
    protected NavigationView nvDrawer;
    @BindView(R.id.pager)
    protected SwipeEnableViewPager pager;
    private Bitmap mImageBitmap;
    private ActionBarDrawerToggle drawerToggle;

    private AlertDialog alert;
    private ImageView imageView;
    private Uri imageUri;

    private ToolTipView mRedToolTipView;
    private ToolTipRelativeLayout toolTipRelativeLayout;
    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        setupDrawerContent(nvDrawer);
        setPhotoAndName();
        pager.setAdapter(new NavBarPagerAdapter(getSupportFragmentManager()));
        pager.setSwipeEnabled(false);
        createDialog();

        toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.activity_main_tooltipRelativeLayout);
        chatButton = (Button) LayoutInflater.from(this).inflate(R.layout.menu_button, null);
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CHOOSE_PICTURE);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, TAKE_PICTURE);
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Photo master")
                .setMessage("Do you want to take a photo or choose from the album?")
                .setIcon(R.mipmap.videocamera)
                .setCancelable(false)
                .setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Choose from album",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                choosePhoto();
                                dialog.cancel();
                            }
                        });
        alert = builder.create();
    }


    private void setPhotoAndName() {
        Profile profile = Profile.getCurrentProfile();
        View hView = nvDrawer.getHeaderView(0);
        imageView = (ImageView) hView.findViewById(R.id.photo);
        imageUri = profile.getProfilePictureUri(200, 200);
        changePhoto();
        TextView textView = (TextView) hView.findViewById(R.id.name);
        textView.setText(profile.getName());

        hView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });
    }

    private void changePhoto() {
        Picasso.with(this)
                .load(imageUri)
                .transform(new CropCircleTransformation())
                .into(imageView);
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
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.list_of_films:
                pager.setCurrentItem(NavBarPagerAdapter.LIST_OF_FILMS, false);
                break;
            case R.id.empty_fragment:
                pager.setCurrentItem(NavBarPagerAdapter.MAP_FRAGMENT, false);
                break;
            case R.id.log_out:
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRedToolTipView();
            }
        });
        menu.getItem(0).setActionView(chatButton);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return drawerToggle.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CHOOSE_PICTURE:
                imageUri = data.getData();
                changePhoto();
                break;
            case TAKE_PICTURE:
                handleSmallCameraPhoto(data);

        }
    }

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        mImageBitmap = (Bitmap) extras.get("data");
        imageUri = getImageUri(this, mImageBitmap);
        changePhoto();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageUri = Uri.parse(savedInstanceState.getString(BITMAP_STORAGE_KEY));
        changePhoto();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BITMAP_STORAGE_KEY, imageUri.toString());
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void addRedToolTipView() {
        ToolTip toolTip = new ToolTip()
                .withText("A beautiful Button")
                .withColor(Color.RED)
                .withShadow()
                .withAnimationType(ToolTip.AnimationType.FROM_TOP);

        mRedToolTipView = toolTipRelativeLayout.showToolTipForView(toolTip, chatButton);
        mRedToolTipView.setOnToolTipViewClickedListener(this);
    }

    @Override
    public void onToolTipViewClicked(ToolTipView toolTipView) {
        mRedToolTipView = null;
    }
}

