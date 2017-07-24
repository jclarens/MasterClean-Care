package com.mvp.mobile_art.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.User;
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
                showDialog("Logging in");
                HashMap<String,Object> map = new HashMap<>();
                map.put("grant_type","password");
                map.put("client_id", Settings.getClientID());
                map.put("client_secret",Settings.getclientSecret());
                map.put("username",email.getText().toString());
                map.put("password",katasandi.getText().toString());
                Call<Token> caller = APIManager.getRepository(UserRepo.class).loginuser(map);
                caller.enqueue(new APICallback<Token>() {
                    @Override
                    public void onSuccess(Call<Token> call, Response<Token> response) {
                        super.onSuccess(call, response);
                        SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getAccess_token());
                        getOwnData();
                    }

                    @Override
                    public void onUnauthorized(Call<Token> call, Response<Token> response) {
                        super.onUnauthorized(call, response);
                        dismissDialog();
                        Toast.makeText(getApplicationContext(), "Email or Password is wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNotFound(Call<Token> call, Response<Token> response) {
                        super.onNotFound(call, response);
                        dismissDialog();
                        Toast.makeText(getApplicationContext(), "Link Not Found", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call<Token> call, Response<Token> response) {
                        super.onError(call, response);
                        dismissDialog();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        super.onFailure(call, t);
                        dismissDialog();
                        Toast.makeText(getApplicationContext(), "Fail to connect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getOwnData(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("email",email.getText().toString());
        map.put("password",katasandi.getText().toString());
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).getOwnData(map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                Intent i = new Intent();
                User user = response.body().getUser();
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
                Log.d("temporary","onSuccess:"+ SharedPref.getValueString(ConstClass.USER));
                dismissDialog();
                dofinishActivity(i);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }

    public void dofinishActivity(Intent intent){
        setResult(MainActivity.RESULT_SUCCESS, intent);
        finish();
    }
}
