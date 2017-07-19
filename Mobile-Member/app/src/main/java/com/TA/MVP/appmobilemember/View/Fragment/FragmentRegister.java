package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.SpinnerAdapter;
import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.UserContact;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class FragmentRegister extends Fragment {
    private EditText nama, email, katasandi, konfkatasandi, notelp, alamat, bplace, bdate, thn, bln, hr;
    private Spinner spinnergender, spinnerkota, spinneragama;
    private Button btndaftar;
    private TextView tvlogin;
    private ArrayAdapter arrayAdaptergender, arrayAdapterkota;
    private SpinnerAdapter spinnerAdapteragama;
    private ArrayAgama arrayAgama=new ArrayAgama();
    private String[] genders = new String[]{"Pria","Wanita"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_register, container, false);

        nama = (EditText) _view.findViewById(R.id.reg_et_nama);
        email = (EditText) _view.findViewById(R.id.reg_et_email);
        katasandi = (EditText) _view.findViewById(R.id.reg_et_katasandi);
        konfkatasandi = (EditText) _view.findViewById(R.id.reg_et_konfkatasandi);
        bplace = (EditText) _view.findViewById(R.id.reg_et_bornplace);
        thn = (EditText) _view.findViewById(R.id.reg_et_thn);
        bln = (EditText) _view.findViewById(R.id.reg_et_bln);
        hr = (EditText) _view.findViewById(R.id.reg_et_hr);
//        bdate = (EditText) _view.findViewById(R.id.reg_et_borndate);
        spinnergender = (Spinner) _view.findViewById(R.id.reg_spinner_gender);
        spinneragama = (Spinner) _view.findViewById(R.id.reg_spinner_agama);
        notelp = (EditText) _view.findViewById(R.id.reg_et_notelp);
        spinnerkota = (Spinner) _view.findViewById(R.id.reg_spinner_kota);
        alamat = (EditText) _view.findViewById(R.id.reg_et_alamat);
        btndaftar = (Button) _view.findViewById(R.id.reg_btn_daftar);
        tvlogin = (TextView) _view.findViewById(R.id.reg_tv_login);

        arrayAdaptergender = new ArrayAdapter(getContext(), R.layout.spinner_item, genders);
        spinnergender.setAdapter(arrayAdaptergender);
        arrayAdapterkota = new ArrayAdapter(getContext(), R.layout.spinner_item, ((MasterCleanApplication) getActivity().getApplication()).getGlobalStaticData().getPlaces());
        spinnerkota.setAdapter(arrayAdapterkota);
        spinnerAdapteragama = new SpinnerAdapter(getContext(), arrayAgama.getArrayList());
        spinneragama.setAdapter(spinnerAdapteragama.getArrayAdapter());

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",nama.getText().toString());
                map.put("email",email.getText().toString());
                map.put("password",katasandi.getText().toString());
                map.put("gender", String.valueOf(spinnergender.getSelectedItemPosition()+1));
                map.put("born_place",bplace.getText().toString());
                map.put("born_date", thn.getText() + "-" + bln.getText() + "-" + hr.getText());
                map.put("religion",String.valueOf(spinneragama.getSelectedItemPosition()+1));
                map.put("user_type",String.valueOf(1));//cek lg
                map.put("status",String.valueOf(1));//cek lg
                UserContact userContact = new UserContact();
                userContact.setAddress(alamat.getText().toString());
                userContact.setCity((spinnerkota.getSelectedItemPosition()+1));
                userContact.setProvince(((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getPlaces().get(spinnerkota.getSelectedItemPosition()+1).getParent());
                userContact.setPhone(notelp.getText().toString());
                map.put("Contact", userContact);//cek lg
                Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).registeruser(map);
                caller.enqueue(new APICallback<UserResponse>() {
                    @Override
                    public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                        super.onSuccess(call, response);
                        Intent i = new Intent();
                        i.putExtra(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        getActivity().setResult(MainActivity.RESULT_SUCCESS, i);
                        getToken(email.getText().toString(), katasandi.getText().toString());
                        getActivity().finish();
                    }

                    @Override
                    public void onUnauthorized(Call<UserResponse> call, Response<UserResponse> response) {
                        super.onUnauthorized(call, response);
                    }

                    @Override
                    public void onUnprocessableEntity(Call<UserResponse> call, Response<UserResponse> response) {
                        super.onUnprocessableEntity(call, response);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });

            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)getActivity()).doChangeFragment(new FragmentLogin());
            }
        });

        return _view;
    }
    public void getToken(String email, String password){
        HashMap<String,Object> map = new HashMap<>();
        map.put("grant_type","password");
        map.put("client_id", Settings.getClientID());
        map.put("client_secret",Settings.getclientSecret());
        map.put("username",email);
        map.put("password",password);
        Call<Token> caller = APIManager.getRepository(UserRepo.class).loginuser(map);
        caller.enqueue(new APICallback<Token>() {
            @Override
            public void onSuccess(Call<Token> call, Response<Token> response) {
                super.onSuccess(call, response);
                SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getAccess_token());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
