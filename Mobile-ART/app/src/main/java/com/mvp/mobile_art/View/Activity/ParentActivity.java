package com.mvp.mobile_art.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class ParentActivity extends AppCompatActivity {
    public static void doChangeActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(_intent);
    }
    public static void doStartActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        context.startActivity(_intent);
    }
}
