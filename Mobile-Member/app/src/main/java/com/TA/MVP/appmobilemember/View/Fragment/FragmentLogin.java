package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
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
 * Created by Zackzack on 08/06/2017.
 */

public class FragmentLogin extends Fragment {
    private EditText email, katasandi;
    private Button btnlogin;
    private TextView tvdaftar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText) _view.findViewById(R.id.lgn_et_email);
        katasandi = (EditText) _view.findViewById(R.id.lgn_et_katasandi);
        btnlogin = (Button) _view.findViewById(R.id.lgn_btn_login);
        tvdaftar = (TextView) _view.findViewById(R.id.lgn_tv_daftar);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("grant_type","password");
                map.put("client_id", Settings.getClientID());
                map.put("client_secret",Settings.getclientSecret());
                map.put("username",email.getText().toString());
                map.put("password",katasandi.getText().toString());
                Call<Token> caller = APIManager.getRepository(UserRepo.class).logintoken(map);
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
                        Toast.makeText(getContext(), "Email or Password is wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNotFound(Call<Token> call, Response<Token> response) {
                        super.onNotFound(call, response);
                    }

                    @Override
                    public void onError(Call<Token> call, Response<Token> response) {
                        super.onError(call, response);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }
        });

        tvdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)getActivity()).doChangeFragment(new FragmentRegister());
            }
        });
        return _view;
    }

    public void getOwnData(){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getOwnData();
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                if (getActivity().getIntent().hasExtra(ConstClass.LOGIN_EXTRA)) {
                    Intent i = new Intent();
                    i.putExtra(ConstClass.USER, GsonUtils.getJsonFromObject(response.body(),User.class));
                    getActivity().setResult(FragmentLainnya.RESULT_SUCCESS, i);
                }
                getActivity().finish();
            }
        });
    }
}
