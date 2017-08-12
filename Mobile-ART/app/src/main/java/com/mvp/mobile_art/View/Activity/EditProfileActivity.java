package com.mvp.mobile_art.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.UserContact;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 11/08/17.
 */

public class EditProfileActivity extends ParentActivity {
    private EditText nama, notelp, notelp2, cttn;
    private Button simpan;
    private User user = new User();
    private Spinner spinnerkota;
    private ArrayAdapter arrayAdapterkota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        nama = (EditText) findViewById(R.id.et_nama);
        notelp = (EditText) findViewById(R.id.notelp);
        notelp2 = (EditText) findViewById(R.id.notelp2);
        simpan = (Button) findViewById(R.id.simpan);
        cttn = (EditText) findViewById(R.id.keterangan);
        spinnerkota = (Spinner) findViewById(R.id.spinnerkota);

        arrayAdapterkota = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, ((MasterCleanApplication)getApplication()).getGlobalStaticData().getPlaces());
        spinnerkota.setAdapter(arrayAdapterkota);

        nama.setText(user.getName());
        notelp.setText(user.getContact().getPhone());
        notelp2.setText(user.getContact().getEmergency_numb());

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama.getText().toString().equals("") || notelp.getText().toString().equals("") || notelp2.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Nama dan No.telp harus diisi.",Toast.LENGTH_SHORT).show();
                else {
                    updateprofile();
                }
            }
        });
    }
    public void updateprofile(){
        initProgressDialog("Menyimpan");
        showDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", nama.getText().toString());
        map.put("description", cttn.getText().toString());
        UserContact userContact = user.getContact();
        userContact.setAddress(user.getContact().getAddress());
        userContact.setCity((spinnerkota.getSelectedItemPosition() + 1));
        userContact.setPhone(notelp.getText().toString());
        userContact.setPhone(notelp.getText().toString());
        map.put("contact", userContact);
        user.setName(nama.getText().toString());
        user.getContact().setAddress(user.getContact().getAddress());
        user.getContact().setPhone(notelp.getText().toString());
        user.getContact().setEmergency_numb(notelp2.getText().toString());
        SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(),map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                Intent i = new Intent();
                setResult(ProfileActivity.RESULT_SUCCESS, i);
                Toast.makeText(getApplicationContext(),"Edit profile berhasil.",Toast.LENGTH_SHORT).show();
                dismissDialog();
                finish();
            }

            @Override
            public void onUnprocessableEntity(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnprocessableEntity(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnauthorized(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnauthorized(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
