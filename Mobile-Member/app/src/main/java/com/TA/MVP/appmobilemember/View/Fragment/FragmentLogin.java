package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Responses.LoginResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

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

        tvdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)getActivity()).doChangeFragmentRegister();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidekeyboard();
                if (email.getText().toString().equals("") || katasandi.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Email atau katasandi belum diisi", Toast.LENGTH_SHORT).show();
                }else if (!isValidEmail(email.getText().toString())){
                    Toast.makeText(getContext(),"Input Email tidak benar.", Toast.LENGTH_SHORT).show();
                }
                else {
                    dologin();
                }
            }
        });
        return _view;
    }
    public void dologin(){
        ((AuthActivity) getActivity()).showDialog("Logging in");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email.getText().toString());
        map.put("password", katasandi.getText().toString());
        Call<LoginResponse> caller = APIManager.getRepository(UserRepo.class).loginmember(map);
        caller.enqueue(new APICallback<LoginResponse>() {
            @Override
            public void onSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onSuccess(call, response);
                try {
                    if (response.body().getUser().getActivation() == 0){
                        Toast.makeText(getContext(),"Anda belum mendapat hak akses", Toast.LENGTH_SHORT).show();
                        ((AuthActivity) getActivity()).dismissDialog();
                    } else {
                        SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getToken().getAccess_token());
                        ((AuthActivity) getActivity()).dismissDialog();
                        Intent i = new Intent();
                        i.putExtra(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                        ((AuthActivity) getActivity()).dismissDialog();
                        ((AuthActivity) getActivity()).dofinishActivity(i);
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), "Email atau katasandi salah", Toast.LENGTH_SHORT).show();
                    ((AuthActivity) getActivity()).dismissDialog();
                }
            }

            @Override
            public void onUnauthorized(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onUnauthorized(call, response);
                ((AuthActivity) getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Email atau katasandi salah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onError(call, response);
                ((AuthActivity) getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((AuthActivity) getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void hidekeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}