package com.mvp.mobile_art.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.mobile_art.R;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

/**
 * Created by Zackzack on 06/07/2017.
 */

public class LoginActivity extends ParentActivity {
    private EditText email, katasandi;
    private Button btnlogin;
    private User user = new User();
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
                //dologin
                //get user
                //get access token
                Intent intent = new Intent();
                setResult(MainActivity.RESULT_SUCCESS,intent);
                //save token
                SharedPref.save(ConstClass.USER,GsonUtils.getJsonFromObject(user));
                //put user to intent
                finish();
            }
        });
    }
}
