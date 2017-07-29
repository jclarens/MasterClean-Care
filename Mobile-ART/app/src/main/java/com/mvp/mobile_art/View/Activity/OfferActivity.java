package com.mvp.mobile_art.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mvp.mobile_art.R;

/**
 * Created by jcla123ns on 28/07/17.
 */

public class OfferActivity extends ParentActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Permintaan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
