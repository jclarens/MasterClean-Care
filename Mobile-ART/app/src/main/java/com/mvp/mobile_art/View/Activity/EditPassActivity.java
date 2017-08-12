package com.mvp.mobile_art.View.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.LoginResponse;
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
 * Created by Zackzack on 10/06/2017.
 */

public class EditPassActivity extends ParentActivity{
    private Toolbar toolbar;
    private EditText pass, newpass, knewpass;
    private Button btnsimpan, btnbatal;
    private Context context;
    private User user;
    private boolean valid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpass);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        context = getApplicationContext();

        pass = (EditText) findViewById(R.id.epass_et_pass);
        newpass = (EditText) findViewById(R.id.epass_et_newpass);
        knewpass = (EditText) findViewById(R.id.epass_et_knewpass);
        btnsimpan = (Button) findViewById(R.id.epass_btn_simpan);
        btnbatal = (Button) findViewById(R.id.epass_btn_batal);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi
                valid = true;

                if (pass.getText().toString() == "" || newpass.getText().toString() == "" || knewpass.getText().toString() == "")
                    valid = false;
                if (newpass.getText().toString() != knewpass.getText().toString()) {
                    valid = false;
                }
                if (valid){
                    abuildermessage("Simpan password baru?", "Ubah Password");
                    abuilder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkoldpass();
                        }
                    });
                    abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    showalertdialog();
                }
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
    public void gantipass(){
        initProgressDialog("Sedang Memperoses ...");
        showDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", newpass.getText().toString());
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(),map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                Intent i = new Intent();
                setResult(ProfileActivity.RESULT_SUCCESS, i);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Kata sandi telah diperbaharui", Toast.LENGTH_SHORT).show();
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
            }
        });
    }
    public void checkoldpass(){
        showDialog("Logging in");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("password", pass.getText().toString());
        Call<LoginResponse> caller = APIManager.getRepository(UserRepo.class).loginasisten(map);
        caller.enqueue(new APICallback<LoginResponse>() {
            @Override
            public void onSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onSuccess(call, response);
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getToken().getAccess_token());
                gantipass();
            }

            @Override
            public void onUnauthorized(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onUnauthorized(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Kata sandi salah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
