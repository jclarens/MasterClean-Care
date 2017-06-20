package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPemesanan1;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPemesanan2;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPemesanan3;

/**
 * Created by Zackzack on 14/06/2017.
 */

public class PemesananActivity extends ParentActivity {
    private Toolbar toolbar;
    private FragmentPemesanan1 fragp1;
    private FragmentPemesanan2 fragp2;
    private FragmentPemesanan3 fragp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        fragp1 = new FragmentPemesanan1();
        fragp2 = new FragmentPemesanan2();
        fragp3 = new FragmentPemesanan3();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pemesanan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_pemesanan, fragp1).commit();
    }

    public void doChangeFragment(int nmrfrag){
        switch (nmrfrag){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_pemesanan, fragp1).commit();
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_pemesanan, fragp2).commit();
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_pemesanan, fragp3).commit();
        }
//        getSupportFragmentManager().beginTransaction().replace(R.id.layout_pemesanan, fragment).commit();
    }
}
