package com.TA.MVP.appmobilemember.View.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.EmergencyCallResponse;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.EmergencycallRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

import static java.security.AccessController.getContext;

/**
 * Created by Zackzack on 19/06/2017.
 */

public class EmergencyActivity extends ParentActivity {
    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private static final int PERMS_REQUEST_CODE = 123;
    private Button tutup;
    private EditText code;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        addtoemergencylist();

        if (getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            calladmin();
        }
        else{
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }

//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:087868528695"));
//        startActivity(callIntent);

        code = (EditText) findViewById(R.id.sos_et_code);
        tutup = (Button) findViewById(R.id.sos_btn_tutup);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi code
                if (code.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Input Password kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    trypass();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode){
            case PERMS_REQUEST_CODE:
                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
                break;
        }
        if (allowed){
            calladmin();
        }
    }
    public void calladmin(){
        getApplicationContext().checkSelfPermission(Manifest.permission.CALL_PHONE);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:082168360303"));
        startActivity(callIntent);
    }
    public void trypass(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("grant_type","password");
        map.put("client_id", Settings.getClientID());
        map.put("client_secret",Settings.getclientSecret());
        map.put("username",user.getEmail());
        map.put("password",code.getText().toString());
        Call<Token> caller = APIManager.getRepository(UserRepo.class).loginuser(map);
        caller.enqueue(new APICallback<Token>() {
            @Override
            public void onSuccess(Call<Token> call, Response<Token> response) {
                super.onSuccess(call, response);
                SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getAccess_token());
                dismissDialog();
                SharedPref.save(ConstClass.EMERGENCY_EXTRA, "");
                doChangeActivity(EmergencyActivity.this, MainActivity.class);
            }

            @Override
            public void onUnauthorized(Call<Token> call, Response<Token> response) {
                super.onUnauthorized(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Password Salah", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Harus melalui tombol tutup", Toast.LENGTH_SHORT).show();
    }
    public void addtoemergencylist(){
        Calendar calendar = Calendar.getInstance();
        HashMap<String,Object> map = new HashMap<>();
        map.put("user_id", user.getId().toString());
        map.put("init_time", fixFormat.format(calendar.getTime()));
        map.put("status", 1);
        Call<EmergencyCallResponse> caller = APIManager.getRepository(EmergencycallRepo.class).postemergencycall(map);
        caller.enqueue(new APICallback<EmergencyCallResponse>() {
            @Override
            public void onSuccess(Call<EmergencyCallResponse> call, Response<EmergencyCallResponse> response) {
                super.onSuccess(call, response);
            }

            @Override
            public void onFailure(Call<EmergencyCallResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
