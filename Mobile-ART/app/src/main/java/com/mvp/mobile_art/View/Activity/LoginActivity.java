package com.mvp.mobile_art.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.LoginResponse;
import com.mvp.mobile_art.Model.Responses.Token;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 06/07/2017.
 */

public class LoginActivity extends ParentActivity {
    private EditText email, katasandi;
    private Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.lgn_et_email);
        katasandi = (EditText) findViewById(R.id.lgn_et_katasandi);
        btnlogin = (Button) findViewById(R.id.lgn_btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("") || katasandi.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Email atau Password salah", Toast.LENGTH_SHORT).show();
                showDialog("Logging in");
                HashMap<String, Object> map = new HashMap<>();
                map.put("email", email.getText().toString());
                map.put("password", katasandi.getText().toString());
                Call<LoginResponse> caller = APIManager.getRepository(UserRepo.class).loginasisten(map);
                caller.enqueue(new APICallback<LoginResponse>() {
                    @Override
                    public void onSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {
                        super.onSuccess(call, response);
                        SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getToken().getAccess_token());
                        dismissDialog();
                        Intent i = new Intent();
                        i.putExtra(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        dofinishActivity(i);
                    }

                    @Override
                    public void onUnauthorized(Call<LoginResponse> call, Response<LoginResponse> response) {
                        super.onUnauthorized(call, response);
                        dismissDialog();
                        Toast.makeText(getApplicationContext(), "Email atau Password salah", Toast.LENGTH_SHORT).show();
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
        });
    }

    public void dofinishActivity(Intent intent){
        setResult(MainActivity.RESULT_SUCCESS, intent);
        finish();
    }
}
