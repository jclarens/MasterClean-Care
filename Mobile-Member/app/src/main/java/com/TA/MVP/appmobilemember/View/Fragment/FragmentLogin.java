package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class FragmentLogin extends Fragment {
    private EditText username, katasandi;
    private Button btnlogin;
    private TextView tvdaftar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) _view.findViewById(R.id.lgn_et_username);
        katasandi = (EditText) _view.findViewById(R.id.lgn_et_katasandi);
        btnlogin = (Button) _view.findViewById(R.id.lgn_btn_login);
        tvdaftar = (TextView) _view.findViewById(R.id.lgn_tv_daftar);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthActivity.doChangeActivity(getContext(), MainActivity.class);
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
