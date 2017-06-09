package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentLogin;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class AuthActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        doChangeFragment(new FragmentLogin());
    }

    public void doChangeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_auth, fragment).commit();
    }

    public static void doChangeActivity(Context context, Class activityClass){
        Intent _intent = new Intent(context, activityClass);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(_intent);
    }
}
