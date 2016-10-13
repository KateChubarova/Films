package com.ekaterinachubarova.films1.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ekaterinachubarova on 24.09.16.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private DecimalFormat df = new DecimalFormat("#.##");
    private static AlertDialog alert;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, parent, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        ButterKnife.bind(this, v);

        createDialog();
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapUtils();
    }


    private void setMapUtils() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setIndoorEnabled(false);

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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getMyCurrentPosition(), 15));
    }

    private LatLng getMyCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastlocation = locationManager.getLastKnownLocation(locationProvider);

        double latitude = lastlocation.getLatitude();
        double longitude = lastlocation.getLongitude();

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
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
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

    @OnClick (R.id.find_my_location)
    protected void findMe(){
        setMyLocation();
    }

    public static void showDialog(){
        alert.show();
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Panorama")
                .setMessage("Sorry, there is no panorama for this location.")
                .setIcon(R.mipmap.videocamera)
                .setCancelable(false)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alert = builder.create();
    }
}

