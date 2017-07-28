package com.TA.MVP.appmobilemember.View.Fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.OfferContact;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActivity;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class FragmentPermintaan1 extends Fragment {
    public static final int PLACE_PICKER_REQUEST = 1;
    private PlacePicker.IntentBuilder ppbuilder = new PlacePicker.IntentBuilder();
    private Offer offer = new Offer();
    private Bundle bundle = new Bundle();
    private User member = new User();

    private static final int PERMS_REQUEST_CODE = 123;
    private Button next;
    private Place place;
    private Button btnpilih;
    private EditText address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_permintaan1, container, false);
        member = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        address = (EditText) _view.findViewById(R.id.prm1_et_address);
        next = (Button) _view.findViewById(R.id.prm1_btn_next);
        btnpilih = (Button) _view.findViewById(R.id.prm1_btn_pilih);
        btnpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    launchPlace();
                } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(place == null)){
                    OfferContact offerContact = new OfferContact();
                    offerContact.setAddress(address.getText().toString());
                    offerContact.setLocation(place.getLatLng().latitude + "," +place.getLatLng().longitude);
                    offerContact.setPhone(member.getContact().getPhone());
                    offerContact.setCity(member.getContact().getCity());
                    offer.setContact(offerContact);
                    SharedPref.save(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offer));
                    ((PermintaanActivity)getActivity()).doChangeFragment(2);
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
                //simpan latlng
                ((PermintaanActivity)getActivity()).setPlace(place);
            }
        }
    }
}
