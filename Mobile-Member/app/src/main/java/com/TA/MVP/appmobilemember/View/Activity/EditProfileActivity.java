package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.UserContact;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 09/06/2017.
 */

public class EditProfileActivity extends ParentActivity {
    private User user;
    private Toolbar toolbar;
    private ImageView imgfoto;
    private EditText nama, alamat, notelp, email;
    private Button btnsimpan, btnbatal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        imgfoto = (ImageView) findViewById(R.id.eprof_iv_foto);
        nama = (EditText) findViewById(R.id.eprof_et_nama);
        alamat = (EditText) findViewById(R.id.eprof_et_alamat);
        notelp = (EditText) findViewById(R.id.eprof_et_notelp);
        email = (EditText) findViewById(R.id.eprof_et_email);
        btnsimpan = (Button) findViewById(R.id.eprof_btn_simpan);
        btnbatal = (Button) findViewById(R.id.eprof_btn_batal);


        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        nama.setText(user.getName());
        try{
            alamat.setText(user.getContact().get(0).getAddress());
            notelp.setText(user.getContact().get(0).getPhone());
        }
        catch (NullPointerException e){
            alamat.setText("-");
            notelp.setText("-");
        }
        email.setText(user.getEmail());

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initProgressDialog("Sedang Memperoses ...");
                showDialog();
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", nama.getText().toString());
                List<UserContact> userContacts = new ArrayList<UserContact>();
                UserContact userContact = new UserContact();
                userContact.setAddress(alamat.getText().toString());
                userContact.setPhone(notelp.getText().toString());
                userContacts.add(userContact);
                map.put("contact", userContacts);

                user.setName(nama.getText().toString());
                user.getContact().get(0).setAddress(alamat.getText().toString());
                user.getContact().get(0).setPhone(notelp.getText().toString());
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));


                Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(String.valueOf(user.getId()),map);
                caller.enqueue(new APICallback<UserResponse>() {
                    @Override
                    public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                        super.onSuccess(call, response);
                        dismissDialog();
                        Intent i = new Intent();
                        setResult(ProfileActivity.RESULT_SUCCESS, i);
                        finish();
                    }

                    @Override
                    public void onUnprocessableEntity(Call<UserResponse> call, Response<UserResponse> response) {
                        super.onUnprocessableEntity(call, response);
                        dismissDialog();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        super.onFailure(call, t);
                        dismissDialog();
                    }
                });
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_editprofile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
