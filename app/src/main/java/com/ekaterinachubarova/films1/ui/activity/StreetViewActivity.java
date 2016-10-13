package com.ekaterinachubarova.films1.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ekaterinachubarova.films1.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.text.DecimalFormat;

/**
 * Created by ekaterinachubarova on 13.10.16.
 */

public class StreetViewActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {
    private double latitude;
    private double longitude;
    private static DecimalFormat df2 = new DecimalFormat("#.#####");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.street_view);

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        latitude = convert(getIntent().getDoubleExtra("latitude", 0.0));
        longitude = convert(getIntent().getDoubleExtra("longitude", 0.0));
    }

    private double convert(double number) {
        return Double.parseDouble(df2.format(number));
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(new LatLng(latitude, longitude));
        streetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
            @Override
            public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                if (streetViewPanoramaLocation != null && streetViewPanoramaLocation.links != null) {

                } else {
                    MainActivity.showDialog();
                    onBackPressed();

                }
            }
        });
    }
}