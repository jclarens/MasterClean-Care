package com.mvp.mobile_art.View.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.UserContact;
import com.mvp.mobile_art.Model.Basic.Waktu_Kerja;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OfferRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Activity.OfferActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentPekerjaan extends Fragment implements OnMapReadyCallback {
    private static final int PERMS_REQUEST_CODE = 123;
    private ImageButton imgcari;
    private EditText namalokasi,nama;
    private LocationManager locationManager;
    private Location location;
    private List<Offer> offers = new ArrayList<>();
    private List<Waktu_Kerja> defaultwk = new ArrayList<>();
    private String[] latlng;
    private User user = new User();
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private Calendar waktumulai = new GregorianCalendar();
    private Calendar calendar = Calendar.getInstance();
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
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        defaultwk = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getWaktu_kerjas();

        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = getLastKnownLocation();
        }
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }

        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        namalokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);

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
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
                Intent intent = new Intent(getContext(), OfferActivity.class);
                intent.putExtra(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(marker.getTag()));
                startActivity(intent);
            }
        });

        if (location != null){
            targetcamera = CameraPosition.builder().target(new LatLng(location.getLatitude(),location.getLongitude())).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(targetcamera));
            updatemylocation(location.getLatitude(),location.getLongitude());
        }
        getoffers();
    }
    public void resetmapview(List<Offer> offers){
        List<Offer> tempoffer = new ArrayList<>();

        for (int n=0;n<offers.size();n++){
            for (int m=0;m<user.getUser_job().size();m++){
                Log.d("Check job id :",offers.get(n).getJob_id()+"=="+user.getUser_job().get(m).getJob_id());
                if (offers.get(n).getJob_id() == user.getUser_job().get(m).getJob_id()) {
                    tempoffer.add(offers.get(n));
                    break;
                }
            }
        }

        offers = tempoffer;

        mGoogleMap.clear();
        for (int i = 0; i < offers.size(); i++){
            latlng = offers.get(i).getContact().getLocation().split(",");
            String temp = "Type Waktu : " + defaultwk.get(offers.get(i).getWork_time_id()-1).getWork_time();
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]))).title(offers.get(i).getMember().getName()).snippet(temp)).setTag(offers.get(i));
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

            }
        }
    }
    public void getoffers(){
        Call<List<Offer>> caller = APIManager.getRepository(OfferRepo.class).getoffersbystatus(0);
        caller.enqueue(new APICallback<List<Offer>>() {
            @Override
            public void onSuccess(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onSuccess(call, response);
                offers = response.body();
                resetmapview(removeexpired(offers));
            }

            @Override
            public void onError(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void updatemylocation(Double lat, Double lng){
        HashMap<String, Object> map = new HashMap<>();
        UserContact userContact = user.getContact();
        userContact.setLocation(lat+", "+lng);
        map.put("contact", userContact);
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(), map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
            }

            @Override
            public void onError(Call<UserResponse> call, Response<UserResponse> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public List<Offer> removeexpired(List<Offer> offers){
        List<Offer> temp = new ArrayList<>();
        calendar = Calendar.getInstance();
        for (int n=0; n< offers.size();n++){
            try {
                waktumulai.setTime(getdateFormat.parse(offers.get(n).getStart_date()));
            } catch (ParseException e) {

            }
            if (!calendar.after(waktumulai)){
                temp.add(offers.get(n));
            }
        }
        return temp;
    }
}