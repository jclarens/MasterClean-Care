package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FilterActivity extends ParentActivity{
    private Toolbar toolbar;
    private EditText nama, gaji;
    private NumberPicker usiamin, usiamax, pengalamankrj;
    private Spinner spinnerkota, spinneragama, spinnersuku, spinnerprofesi, spinnerwaktukrj;
    private CheckBox inggris, mandarin, melayu;
    private Button btncari, btnbatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        nama = (EditText) findViewById(R.id.filter_et_nama);
        gaji = (EditText) findViewById(R.id.filter_et_gaji);
        usiamin = (NumberPicker) findViewById(R.id.filter_np_usiamin);
        usiamax = (NumberPicker) findViewById(R.id.filter_np_usiamax);
        pengalamankrj = (NumberPicker) findViewById(R.id.filter_np_pengalamankrj);
        spinnerkota = (Spinner) findViewById(R.id.filter_spinner_kota);
        spinneragama = (Spinner) findViewById(R.id.filter_spinner_agama);
        spinnersuku = (Spinner) findViewById(R.id.filter_spinner_suku);
        spinnerprofesi = (Spinner) findViewById(R.id.filter_spinner_profesi);
        spinnerwaktukrj = (Spinner) findViewById(R.id.filter_spinner_waktukrj);
        inggris = (CheckBox) findViewById(R.id.filter_cb_bhsinggris);
        mandarin = (CheckBox) findViewById(R.id.filter_cb_bhsmandarin);
        melayu = (CheckBox) findViewById(R.id.filter_cb_bhsmelayu);
        btncari = (Button) findViewById(R.id.filter_btn_cari);
        btnbatal = (Button) findViewById(R.id.filter_btn_batal);

        usiamin.setMinValue(20);
        usiamin.setMaxValue(50);
        usiamax.setMinValue(20);
        usiamax.setMaxValue(50);
        pengalamankrj.setMinValue(0);
        pengalamankrj.setMaxValue(20);

        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_filter);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
