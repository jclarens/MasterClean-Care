package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentLogin;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentRegister;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class AuthActivity extends ParentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        doChangeFragmentlogin();
    }

    public void doChangeFragmentlogin(){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_auth, new FragmentLogin()).commit();
    }

    public void doChangeFragmentRegister(){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_auth, new FragmentRegister()).commit();
    }

    public void dofinishActivity(Intent intent){
        setResult(MainActivity.RESULT_SUCCESS, intent);
        finish();
    }
}
