package com.mvp.mobile_art.View.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Responses.LoginResponse;
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
                hidekeyboard();
                if (email.getText().toString().equals("") || katasandi.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Email atau katasandi belum diisi", Toast.LENGTH_SHORT).show();
                }else if (!isValidEmail(email.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Input Email tidak benar.", Toast.LENGTH_SHORT).show();
                }
                else {
                    dologin();
                }
            }
        });
    }
    public void hidekeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void dofinishActivity(Intent intent){
        setResult(MainActivity.RESULT_SUCCESS, intent);
        finish();
    }
    public void dologin(){
        showDialog("Logging in");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email.getText().toString());
        map.put("password", katasandi.getText().toString());
        Call<LoginResponse> caller = APIManager.getRepository(UserRepo.class).loginasisten(map);
        caller.enqueue(new APICallback<LoginResponse>() {
            @Override
            public void onSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                try {
                    if (response.body().getUser().getActivation() == 0) {
                        Toast.makeText(getApplicationContext(), "Anda belum mendapat hak akses.", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getToken().getAccess_token());
                        Intent i = new Intent();
                        i.putExtra(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        dofinishActivity(i);
                    }
                } catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUnauthorized(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onUnauthorized(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Email atau Password salah.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah silahkan coba lagi.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotFound(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
