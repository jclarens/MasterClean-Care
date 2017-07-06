package com.mvp.mobile_art.View.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.mobile_art.R;

/**
 * Created by Zackzack on 06/07/2017.
 */

public class LoginActivity extends ParentActivity {
    private EditText username, katasandi;
    private Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.lgn_et_username);
        katasandi = (EditText) findViewById(R.id.lgn_et_katasandi);
        btnlogin = (Button) findViewById(R.id.lgn_btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check
//                finish();
                LoginActivity.doChangeActivity(getApplicationContext(), MainActivity.class);
            }
        });
//        if (username.getText() != null && katasandi.getText() != null )
    }
}
