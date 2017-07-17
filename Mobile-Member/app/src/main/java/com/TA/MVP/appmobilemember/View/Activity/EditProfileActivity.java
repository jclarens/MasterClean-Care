package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.HashMap;

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
        alamat.setText(user.getAddress());
        notelp.setText(user.getPhone());
        email.setText(user.getEmail());

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", nama.getText().toString());
                map.put("address",alamat.getText().toString());
                map.put("phone",notelp.getText().toString());
//                map.put("email",email.getText().toString()); mungkin g bs
//                map.put("",""); //foto
//                map.put("","");
                Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(String.valueOf(user.getId()),map);
                caller.enqueue(new APICallback<UserResponse>() {
                    @Override
                    public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                        super.onSuccess(call, response);
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
                finish();
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
