package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 19/06/2017.
 */

public class EmergencyActivity extends ParentActivity {
    private Button tutup;
    private EditText code;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        code = (EditText) findViewById(R.id.sos_et_code);
        tutup = (Button) findViewById(R.id.sos_btn_tutup);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi code
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
                        doChangeActivity(EmergencyActivity.this, MainActivity.class);
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
//                finish();
            }
        });
    }
}
