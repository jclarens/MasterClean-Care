package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.TA.MVP.appmobilemember.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentCariMap extends Fragment implements OnMapReadyCallback {
    private ImageButton imgcari;
    private EditText namalokasi,nama;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View _view;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_cari_map, container, false);

        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        namalokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);

        return _view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView= (MapView) _view.findViewById(R.id.carimap_mapview);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(3.5879738,98.6884172)).title("Medan").snippet("Kampus A Mikroskil"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(3.5881532,98.6832049)).title("Medan").snippet("Pusat PAsar"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(3.5824921,98.669481)).title("Medan").snippet("Sun Plaza"));
        CameraPosition Medan = CameraPosition.builder().target(new LatLng(3.6426182, 98.5290616)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Medan));
    }
}