package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class FragmentRegister extends Fragment {
    private EditText nama, email, katasandi, konfkatasandi, notelp, alamat;
    private Button btndaftar;
    private TextView tvlogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_register, container, false);

        nama = (EditText) _view.findViewById(R.id.reg_et_nama);
        email = (EditText) _view.findViewById(R.id.reg_et_email);
        katasandi = (EditText) _view.findViewById(R.id.reg_et_katasandi);
        konfkatasandi = (EditText) _view.findViewById(R.id.reg_et_konfkatasandi);
        notelp = (EditText) _view.findViewById(R.id.reg_et_notelp);
        alamat = (EditText) _view.findViewById(R.id.reg_et_alamat);
        btndaftar = (Button) _view.findViewById(R.id.reg_btn_daftar);
        tvlogin = (TextView) _view.findViewById(R.id.reg_tv_login);

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                Call<Token> caller = APIManager.getRepository(UserRepo.class).registeruser(map);
                caller.enqueue(new APICallback<Token>() {
                    @Override
                    public void onSuccess(Call<Token> call, Response<Token> response) {
                        super.onSuccess(call, response);
                    }

                    @Override
                    public void onUnauthorized(Call<Token> call, Response<Token> response) {
                        super.onUnauthorized(call, response);
                    }

                    @Override
                    public void onUnprocessableEntity(Call<Token> call, Response<Token> response) {
                        super.onUnprocessableEntity(call, response);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
//                AuthActivity.doChangeActivity(getContext(), MainActivity.class); //belum tes
            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)getActivity()).doChangeFragment(new FragmentLogin()); //belom tes
            }
        });

        return _view;
    }
}
