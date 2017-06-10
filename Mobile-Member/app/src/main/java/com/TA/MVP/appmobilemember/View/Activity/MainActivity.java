package com.TA.MVP.appmobilemember.View.Activity;

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

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentCari;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentHome;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentLainnya;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPesan;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatus;

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

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();

        context = getApplicationContext();

        fragmentManager.beginTransaction().replace(R.id.main_container, new FragmentHome()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_beranda:
                        fragment = new FragmentHome();
                        break;
                    case R.id.menu_cari:
                        fragment = new FragmentCari();
                        break;
                    case R.id.menu_status:
                        fragment = new FragmentStatus();
                        break;
                    case R.id.menu_pesan:
                        fragment = new FragmentPesan();
                        break;
                    case R.id.menu_lainnya:
                        fragment = new FragmentLainnya();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menutoolbar = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        hideOption(R.id.action_alarm);//ganti
        return true;
    }

//    private void hideOption(int id){
//        MenuItem item = menutoolbar.findItem(id);
//        item.setVisible(false);
//    }
//
//    private void showOption(int id){
//        MenuItem item = menutoolbar.findItem(id);
//        item.setVisible(false);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
