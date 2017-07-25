package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
 * Created by Zackzack on 10/06/2017.
 */

public class EditPassActivity extends ParentActivity{
    private Toolbar toolbar;
    private EditText pass, newpass, knewpass;
    private Button btnsimpan, btnbatal;
    private Context context;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpass);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        pass = (EditText) findViewById(R.id.epass_et_pass);
        newpass = (EditText) findViewById(R.id.epass_et_newpass);
        knewpass = (EditText) findViewById(R.id.epass_et_knewpass);
        btnsimpan = (Button) findViewById(R.id.epass_btn_simpan);
        btnbatal = (Button) findViewById(R.id.epass_btn_batal);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi
                abuildermessage("Simpan password baru?", "Ubah Password");
                abuilder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        initProgressDialog("Sedang Memperoses ...");
                        showDialog();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("password", newpass.getText().toString());
                        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(String.valueOf(user.getId()),map);
                        caller.enqueue(new APICallback<UserResponse>() {
                            @Override
                            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                                super.onSuccess(call, response);
                                Intent i = new Intent();
                                setResult(ProfileActivity.RESULT_SUCCESS, i);
                                dismissDialog();
                                Toast.makeText(getApplicationContext(), "Password telah diperbaharui", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(), "Koneksi bermasalah silahkan coba lagi nanti", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }
                });
                abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                showalertdialog();

//                finish();
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_editpass);
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
