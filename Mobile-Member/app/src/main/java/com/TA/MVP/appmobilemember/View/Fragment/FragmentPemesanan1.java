package com.TA.MVP.appmobilemember.View.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static android.R.attr.fragment;

/**
 * Created by Zackzack on 14/06/2017.
 */

public class FragmentPemesanan1 extends Fragment  implements OnMapReadyCallback {
    private static final int PERMS_REQUEST_CODE = 123;
    private Button next;
    private FragmentAsistenmini fragmentAsistenmini;
    private MapView mapView;
    private LocationManager locationManager;
    private Location location;
    GoogleMap mGoogleMap;
    private User art;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan1, container, false);
        art = GsonUtils.getObjectFromJson(getArguments().getString(ConstClass.ART_EXTRA), User.class);

        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            location = getLastKnownLocation();
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }

        fragmentAsistenmini = new FragmentAsistenmini();
        next = (Button) _view.findViewById(R.id.pms1_btn_next);
        mapView = (MapView) _view.findViewById(R.id.pms1_picklocation);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
        fragmentAsistenmini.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragmentAsistenmini).commit();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi dulu
//                ((PemesananActivity)getActivity()).doChangeFragment(2);
            }
        });

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
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition Me = CameraPosition.builder().target(new LatLng(location.getLatitude(),location.getLongitude())).zoom(14).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Me));
    }

}