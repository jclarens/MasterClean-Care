package com.mvp.mobile_art.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mvp.mobile_art.R;
import com.mvp.mobile_art.View.Fragment.FragmentJadwal;
import com.mvp.mobile_art.View.Fragment.FragmentLainnya;
import com.mvp.mobile_art.View.Fragment.FragmentPekerjaan;
import com.mvp.mobile_art.View.Fragment.FragmentPesan;
import com.mvp.mobile_art.View.Fragment.FragmentProfile;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class MainActivity extends ParentActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private Context context;
    private Menu menutoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();

        context = getApplicationContext();

        fragmentManager.beginTransaction().replace(R.id.content, new FragmentProfile()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_profile:
                        fragment = new FragmentProfile();
                        break;
                    case R.id.menu_pekerjaan:
                        fragment = new FragmentPekerjaan();
                        break;
                    case R.id.menu_jadwal:
                        fragment = new FragmentJadwal();
                        break;
                    case R.id.menu_pesan:
                        fragment = new FragmentPesan();
                        break;
                    case R.id.menu_lainnya:
                        fragment = new FragmentLainnya();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, fragment).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menutoolbar = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
//                doChangeActivity(context,EmergencyActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
