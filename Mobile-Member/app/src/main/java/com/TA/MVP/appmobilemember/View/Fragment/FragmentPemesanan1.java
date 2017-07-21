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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Zackzack on 14/06/2017.
 */

public class FragmentPemesanan1 extends Fragment{
    public static final int PLACE_PICKER_REQUEST = 1;
    private PlacePicker.IntentBuilder ppbuilder = new PlacePicker.IntentBuilder();

    private static final int PERMS_REQUEST_CODE = 123;
    private Button next;
    private FragmentAsistenmini fragmentAsistenmini;
    private Place place;
    private Button btnpilih;
    private User art;
    private EditText address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan1, container, false);
        art = GsonUtils.getObjectFromJson(getArguments().getString(ConstClass.ART_EXTRA), User.class);

        address = (EditText) _view.findViewById(R.id.pms1_et_address);
        next = (Button) _view.findViewById(R.id.pms1_btn_next);
        btnpilih = (Button) _view.findViewById(R.id.pms1_btn_pilih);
        btnpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    launchPlace();
                } catch (GooglePlayServicesNotAvailableException |  GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

        fragmentAsistenmini = new FragmentAsistenmini();
        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
        fragmentAsistenmini.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragmentAsistenmini).commit();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(place == null)){
                    ((PemesananActivity)getActivity()).doChangeFragment(2);
                }
                else
                    Toast.makeText(getContext(),"Silahkan pilih lokasi terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });
        return _view;
    }
    public void launchPlace() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, getActivity().getApplicationContext());
                address.setText(place.getAddress());
                ((PemesananActivity)getActivity()).setPlace(place);
            }
        }
    }
}