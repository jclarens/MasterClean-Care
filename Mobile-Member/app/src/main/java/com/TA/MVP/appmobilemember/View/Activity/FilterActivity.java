package com.TA.MVP.appmobilemember.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Adapter.SpinnerAdapter;
import com.TA.MVP.appmobilemember.Model.Array.FilterArrays;
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
    private TextView textgaji;
    private SpinnerAdapter spinnerAdapterkota, spinnerAdapteragama, spinnerAdapterprofesi, spinnerAdaptersuku, spinnerAdapterwktkrj;
    private FilterArrays filterArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initAllView();

        spinnerkota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i)+ " Selected.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinneragama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i)+ " = " + i + " Selected.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnintent = new Intent();
                returnintent.putExtra("nama", nama.getText().toString());
                setResult(Activity.RESULT_OK, returnintent);
                finish();
            }
        });
        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnintent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnintent);
                finish();
            }
        });
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

    private void setnumberpickervalue(NumberPicker np, int min, int max){
        np.setMinValue(min);
        np.setMaxValue(max);
    }

    private void initAllView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        textgaji = (TextView) findViewById(R.id.filter_tv_gaji);
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

        setAll();
    }

    private void setAll(){
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_filter);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //NumberPicker
        setnumberpickervalue(usiamin,20,50);
        setnumberpickervalue(usiamax,20,50);
        setnumberpickervalue(pengalamankrj,0,20);

        //Spinner
        filterArrays = new FilterArrays();
        spinnerAdapteragama = new SpinnerAdapter(this, filterArrays.getArrayAgama().getArrayList());
        spinnerAdapterkota = new SpinnerAdapter(this, filterArrays.getArrayKota().getArrayList());
        spinnerAdapterprofesi = new SpinnerAdapter(this, filterArrays.getArrayProfesi().getStringArrayList());
        spinnerAdaptersuku = new SpinnerAdapter(this, filterArrays.getArraySuku().getArrayList());
        spinnerAdapterwktkrj = new SpinnerAdapter(this, filterArrays.getArrayWaktukrj().getArrayList());
        spinneragama.setAdapter(spinnerAdapteragama.getArrayAdapter());
        spinnerkota.setAdapter(spinnerAdapterkota.getArrayAdapter());
        spinnerprofesi.setAdapter(spinnerAdapterprofesi.getArrayAdapter());
        spinnersuku.setAdapter(spinnerAdaptersuku.getArrayAdapter());
        spinnerwaktukrj.setAdapter(spinnerAdapterwktkrj.getArrayAdapter());
    }
}
