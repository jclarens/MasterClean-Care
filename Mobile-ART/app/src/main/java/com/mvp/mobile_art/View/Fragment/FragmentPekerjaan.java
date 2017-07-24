package com.mvp.mobile_art.View.Fragment;

import android.Manifest;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentPekerjaan extends Fragment implements OnMapReadyCallback {
    private static final int PERMS_REQUEST_CODE = 123;
    private LocationManager locationManager;
    private Location location;
    private ImageButton imageButton;
    private EditText textlokasi;
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

        //on click marker
//        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(getContext(), AsistenActivity.class);
//                intent.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(marker.getTag()));
//                startActivity(intent);
//            }
//        });

        if (location != null){
            targetcamera = CameraPosition.builder().target(new LatLng(location.getLatitude(),location.getLongitude())).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(targetcamera));
        }


        //get order
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

    public void resetmapview(List<Order> orders){
        mGoogleMap.clear();
        for (int i = 0; i < orders.size(); i++){
            if (orders.get(i).getContact().size()>0){
                latlng = orders.get(i).getContact().get(0).getLocation().split(",");
//                String temp = "Profesi : ";
//                for(int n=0;n<orders.get(i).getUser_job().size();n++){
//                    if (n != 0)
//                        temp = temp + ", ";
//                    temp = temp + defaultjobs.get(art.get(i).getUser_job().get(n).getJob_id()-1);
//                }
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]))).title(orders.get(i).getWork_time().getWork_time()));
            }
        }
    }
}
//  F7:98:EA:14:25:C4:52:C5:F7:9E:61:44:F3:67:6F:C4:C4:97:B6:03