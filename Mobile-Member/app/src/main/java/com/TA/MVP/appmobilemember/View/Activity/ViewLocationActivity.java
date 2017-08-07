package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.TA.MVP.appmobilemember.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by jcla123ns on 07/08/17.
 */

public class ViewLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    private String alamat;
    private String[] latlng;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlocation);
        Intent intent = getIntent();
        latlng = intent.getStringExtra("location").split(",");
        alamat = intent.getStringExtra("alamat");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng target = new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]));
        CameraPosition targetcamera = CameraPosition.builder().target(target).zoom(14).bearing(0).tilt(45).build();
        mMap.addMarker(new MarkerOptions().position(target).title("Lokasi Pemesanan").snippet(alamat));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(targetcamera));
    }
}
