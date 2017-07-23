package com.mvp.mobile_art.View.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.mvp.mobile_art.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentPekerjaan extends Fragment implements OnMapReadyCallback {
    private static final int PERMS_REQUEST_CODE = 123;
    private MapView mapView;
    private Location location;
    private LocationManager locationManager;
    private ImageButton imageButton;
    private EditText textlokasi;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View _view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_pekerjaan, container, false);

        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            location = getLastKnownLocation();
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
        imageButton = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        textlokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);

        return _view;
    }

    private Location getLastKnownLocation() {
        getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager = (LocationManager)getContext().getSystemService(getContext().LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode){
            case PERMS_REQUEST_CODE:
                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;
        }
        if (allowed)
            location = getLastKnownLocation();

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
//        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(getContext(), AsistenActivity.class);
//                intent.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(marker.getTag()));
//                startActivity(intent);
//            }
//        });
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition Me = CameraPosition.builder().target(new LatLng(location.getLatitude(),location.getLongitude())).zoom(14).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Me));


        //get users
//        Map<String,String> map = new HashMap<>();
//        map.put("user_type","2");
//        Call<List<User>> caller = APIManager.getRepository(UserRepo.class).searchuser(map);
//        caller.enqueue(new APICallback<List<User>>() {
//            @Override
//            public void onSuccess(Call<List<User>> call, Response<List<User>> response) {
//                super.onSuccess(call, response);
//                arts = response.body();
//                resetmapview(arts);
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                super.onFailure(call, t);
//            }
//        });
    }
}
