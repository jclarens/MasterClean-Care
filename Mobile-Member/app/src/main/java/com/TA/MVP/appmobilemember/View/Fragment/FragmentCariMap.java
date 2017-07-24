package com.TA.MVP.appmobilemember.View.Fragment;

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

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AsistenActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentCariMap extends Fragment implements OnMapReadyCallback {
    private static final int PERMS_REQUEST_CODE = 123;
    private ImageButton imgcari;
    private EditText namalokasi,nama;
    private LocationManager locationManager;
    private Location location;
    private List<User> arts = new ArrayList<>();
    private List<Job> defaultjobs = new ArrayList<>();
    private User user;
    private String[] latlng;
    CameraPosition targetcamera;
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
        defaultjobs = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getJobs();

        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = getLastKnownLocation();
        }
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }

        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        namalokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);

        return _view;
    }

    private Location getLastKnownLocation() {
        Location bestLocation = null;
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager)getContext().getSystemService(getContext().LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
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
        }
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
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
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    return false;
                }
            });
        }
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getContext(), AsistenActivity.class);
                intent.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(marker.getTag()));
                startActivity(intent);
            }
        });

        if (location != null){
            targetcamera = CameraPosition.builder().target(new LatLng(location.getLatitude(),location.getLongitude())).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(targetcamera));
        }


        //get users
        Map<String,String> map = new HashMap<>();
        map.put("user_type","2");
        Call<List<User>> caller = APIManager.getRepository(UserRepo.class).searchuser(map);
        caller.enqueue(new APICallback<List<User>>() {
            @Override
            public void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                super.onSuccess(call, response);
                arts = response.body();
                resetmapview(arts);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void resetmapview(List<User> art){
        mGoogleMap.clear();
        for (int i = 0; i < art.size(); i++){
            if (art.get(i).getContact().size()>0){
                latlng = art.get(i).getContact().get(0).getLocation().split(",");
                String temp = "Profesi : ";
                for(int n=0;n<art.get(i).getUser_job().size();n++){
                    if (n != 0)
                        temp = temp + ", ";
                    temp = temp + defaultjobs.get(art.get(i).getUser_job().get(n).getJob_id()-1);
                }
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]))).title(art.get(i).getName()).snippet(temp)).setTag(art.get(i));
            }
        }
    }
}