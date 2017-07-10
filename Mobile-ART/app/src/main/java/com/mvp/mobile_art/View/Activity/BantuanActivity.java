package com.mvp.mobile_art.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mvp.mobile_art.R;

/**
 * Created by Zackzack on 06/07/2017.
 */

public class BantuanActivity extends ParentActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.bantuan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
