package com.TA.MVP.appmobilemember.View.Fragment;

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
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.Presenter.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;

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
                map.put("email",email.getText().toString());
                map.put("password",katasandi.getText().toString());
                Call<UserResponse> caller =  APIManager.getRepository(UserRepo.class).checkLogin(map);
                caller.enqueue(new APICallback<UserResponse>() {
                   @Override
                   public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                       super.onSuccess(call, response);
                       SharedPref.save("logged_id",response.body().getUser().getId());

                       AuthActivity.doChangeActivity(getContext(), MainActivity.class);
                   }

                   @Override
                   public void onNotFound(Call<UserResponse> call, Response<UserResponse> response) {
                       super.onNotFound(call, response);
                       Toast.makeText(getContext(),"Not Found", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onError(Call<UserResponse> call, Response<UserResponse> response) {
                       super.onError(call, response);
                       Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onFailure(Call<UserResponse> call, Throwable t) {
                       super.onFailure(call, t);
                       Toast.makeText(getContext(),"Fail", Toast.LENGTH_SHORT).show();
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
//        if (username.getText() != null && katasandi.getText() != null )
        return _view;
    }
}
