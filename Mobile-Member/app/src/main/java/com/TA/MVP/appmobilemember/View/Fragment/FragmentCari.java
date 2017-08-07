package com.TA.MVP.appmobilemember.View.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.PagerAdapterCari;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentCari extends Fragment  implements OnMapReadyCallback {
    private static final int PERMS_REQUEST_CODE = 123;
    private ImageButton imgcari;
    private EditText namalokasi,nama;
    private LocationManager locationManager;
    private Location location;
    private List<User> arts = new ArrayList<>();
    private List<Job> defaultjobs = new ArrayList<>();
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

//        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = getLastKnownLocation();
        }
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }

        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        namalokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);
        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);


        namalokasi.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    focusmap();
                    return true;
                }
                return false;
            }
        });
        imgcari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                }
                catch (Error error){

                }
                focusmap();
            }
        });

        return _view;
    }

    private Location getLastKnownLocation() {
        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager)getContext().getSystemService(getContext().LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
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
        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        map.put("role_id","3");
        map.put("status","1");
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
    public void resetmapview(List<User> arts){
        mGoogleMap.clear();
        for (int i = 0; i < arts.size(); i++){
            latlng = arts.get(i).getContact().getLocation().split(",");
            String temp = "Profesi : ";
            for(int n=0;n<arts.get(i).getUser_job().size();n++){
                if (n != 0)
                    temp = temp + ", ";
                temp = temp + defaultjobs.get(arts.get(i).getUser_job().get(n).getJob_id()-1);
            }
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]))).title(arts.get(i).getName()).snippet(temp)).setTag(arts.get(i));
        }
    }
    public void focusmap(){
        String stringlocation = namalokasi.getText().toString();
        List<Address> addresses = null;
        if (stringlocation != ""){
            Geocoder geocoder = new Geocoder(getContext());
            try {
                addresses = geocoder.getFromLocationName(stringlocation, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
            catch (IndexOutOfBoundsException e){
                Toast.makeText(getContext(), "Coba kata kunci lain", Toast.LENGTH_SHORT).show();
            }
            catch (NullPointerException e){
//                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                //                    focusmap(address);
            }
        }
    }
}
