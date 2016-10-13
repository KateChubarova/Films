package com.ekaterinachubarova.films1.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.ui.activity.StreetViewActivity;
import com.ekaterinachubarova.films1.utils.ValidationUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

/**
 * Created by ekaterinachubarova on 24.09.16.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, parent, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMyLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ValidationUtils.PERMISSION_REQUEST_CODE) {
            boolean value = ValidationUtils.checkPermissionsGranted(grantResults);
            if (value) {
                setMyLocation();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setMyLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ValidationUtils.checkPermissions(this, mapFragment.getView(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getMyCurrentPosition(), 15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                drawMarker(latLng);
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(), StreetViewActivity.class);
                intent.putExtra("latitude", marker.getPosition().latitude);
                intent.putExtra("longitude", marker.getPosition().longitude);
                startActivity(intent);

            }
        });
    }

    private LatLng getMyCurrentPosition() {
        LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        return new LatLng(latitude, longitude);
    }

    private String format(double latitude, double longitude) {
        return "latitude : " + df.format(latitude) + ", longitude: " + df.format(longitude);
    }

    private void drawMarker(LatLng currentPosition) {
        mMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet(format(currentPosition.latitude, currentPosition.longitude))
                .title("ME"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ValidationUtils.SETTINGS_REQUEST_CODE) {
            setMyLocation();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mapFragment.getMapAsync(this);
        }
    }
}

