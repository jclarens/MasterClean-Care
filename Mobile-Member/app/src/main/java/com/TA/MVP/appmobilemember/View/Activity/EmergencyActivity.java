package com.TA.MVP.appmobilemember.View.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Emergencycall;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.EmergencyCallResponse;
import com.TA.MVP.appmobilemember.Model.Responses.LoginResponse;
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
    private FrameLayout calllayout;
    private Emergencycall EC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        Intent intent = getIntent();
        if (intent.getStringExtra("item") != null){
            EC = GsonUtils.getObjectFromJson(intent.getStringExtra("item"), Emergencycall.class);
        }
        if (SharedPref.getValueString(ConstClass.EMERGENCY_EXTRA).equals("")) {
            addtoemergencylist();
            SharedPref.save(ConstClass.EMERGENCY_EXTRA, "on");
            calladmin();
        }

        calllayout = (FrameLayout) findViewById(R.id.layout_call);
        code = (EditText) findViewById(R.id.sos_et_code);
        tutup = (Button) findViewById(R.id.sos_btn_tutup);

        calllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calladmin();
            }
        });

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
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:082168360303"));
        startActivity(callIntent);
    }
    public void trypass(){
        showDialog("Sedang melakukan validasi");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("password", code.getText().toString());
        Call<LoginResponse> caller = APIManager.getRepository(UserRepo.class).loginmember(map);
        caller.enqueue(new APICallback<LoginResponse>() {
            @Override
            public void onSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onSuccess(call, response);
                if (response.body().status.equals("200")){
                    SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                    SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getToken().getAccess_token());
                    SharedPref.save(ConstClass.EMERGENCY_EXTRA, "");
                    changestatus();
                } else {
                    dismissDialog();
                    Toast.makeText(getApplicationContext(), "Password tidak valid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onUnauthorized(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onUnauthorized(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Harap gunakan tombol tutup", Toast.LENGTH_SHORT).show();
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
                EC = response.body().getEmergencycall();
            }

            @Override
            public void onFailure(Call<EmergencyCallResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void changestatus(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("status", 0);
        Call<EmergencyCallResponse> caller = APIManager.getRepository(EmergencycallRepo.class).patchemergencycall(EC.getId(),map);
        caller.enqueue(new APICallback<EmergencyCallResponse>() {
            @Override
            public void onSuccess(Call<EmergencyCallResponse> call, Response<EmergencyCallResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                doChangeActivity(EmergencyActivity.this, MainActivity.class);
            }

            @Override
            public void onError(Call<EmergencyCallResponse> call, Response<EmergencyCallResponse> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<EmergencyCallResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }
}
