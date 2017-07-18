package com.TA.MVP.appmobilemember.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.SpinnerAdapter;
import com.TA.MVP.appmobilemember.Model.Array.FilterArrays;
import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Basic.Place;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FilterActivity extends ParentActivity{
    private Toolbar toolbar;
    private EditText nama, gaji, usiamin, usiamax, pk, suku;
    private Spinner spinnerkota, spinneragama, spinnersuku, spinnerprofesi, spinnerwaktukrj;
    private CheckBox inggris, mandarin, melayu;
    private Button btncari, btnbatal, btnuminup, btnumindown, btnumaxup, btnumaxdown, btnpkup, btnpkdown;
    private TextView textgaji;
    private SpinnerAdapter spinnerAdapterkota, spinnerAdapteragama, spinnerAdapterprofesi, spinnerAdaptersuku, spinnerAdapterwktkrj;
    private FilterArrays filterArrays;
    private Integer tmp;
    private ArrayAdapter arrayAdapterJob, arrayAdapterWaktu, arrayAdapterKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initAllView();
        setbtnlistener(btnuminup, usiamin, btnumindown, 20, 70);
        setbtnlistener(btnumaxup, usiamax, btnumaxdown, 20, 70);
        setbtnlistener(btnpkup, pk, btnpkdown, 0, 50);

        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("kota", String.valueOf(spinnerkota.getSelectedItemPosition()));
                i.putExtra("agama", String.valueOf(spinneragama.getSelectedItemPosition()));
                i.putExtra("suku", suku.getText().toString());
                i.putExtra("profesi", String.valueOf(spinnerprofesi.getSelectedItemPosition()));
                i.putExtra("WT", String.valueOf(spinnerwaktukrj.getSelectedItemPosition()));
                i.putExtra("gaji", gaji.getText().toString());
                setResult(Activity.RESULT_OK, i);
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

        //set leave focus from parent function
        setupleavefocus(findViewById(R.id.filter_inner_layout), this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideSoftKeyboard(this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAllView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        textgaji = (TextView) findViewById(R.id.filter_tv_gaji);
        nama = (EditText) findViewById(R.id.filter_et_nama);
        gaji = (EditText) findViewById(R.id.filter_et_gaji);
        usiamin = (EditText) findViewById(R.id.filter_et_umin);
        usiamax = (EditText) findViewById(R.id.filter_et_umax);
        pk = (EditText) findViewById(R.id.filter_et_pk);
        suku = (EditText) findViewById(R.id.filter_et_suku);
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
        btnuminup = (Button) findViewById(R.id.filter_btn_uminup);
        btnumindown = (Button) findViewById(R.id.filter_btn_umindown);
        btnumaxup = (Button) findViewById(R.id.filter_btn_umaxup);
        btnumaxdown = (Button) findViewById(R.id.filter_btn_umaxdown);
        btnpkup = (Button) findViewById(R.id.filter_btn_pkup);
        btnpkdown = (Button) findViewById(R.id.filter_btn_pkdown);

        setAll();
    }

    private void setAll(){
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_filter);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Spinner
        filterArrays = new FilterArrays();

        arrayAdapterJob = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, addalljob(((MasterCleanApplication) getApplication()).getGlobalStaticData().getJobs()));
        spinnerprofesi.setAdapter(arrayAdapterJob);

        arrayAdapterWaktu = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, addallwkt(((MasterCleanApplication) getApplication()).getGlobalStaticData().getWaktu_kerjas()));
        spinnerwaktukrj.setAdapter(arrayAdapterWaktu);

        arrayAdapterKota = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, addallkota(((MasterCleanApplication) getApplication()).getGlobalStaticData().getPlaces()));
        spinnerkota.setAdapter(arrayAdapterKota);


        spinnerAdapteragama = new SpinnerAdapter(this, filterArrays.getArrayAgama().getArrayList2());
        spinneragama.setAdapter(spinnerAdapteragama.getArrayAdapter());

        usiamin.setText(String.valueOf(20));
        usiamax.setText(String.valueOf(70));
        pk.setText(String.valueOf(1));
    }
    private void setbtnlistener(Button btnup, final EditText editText, Button btndown, final Integer min2, final Integer max2){
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = Integer.valueOf(editText.getText().toString());
                if (tmp < max2)
                    editText.setText(String.valueOf(tmp + 1));
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    tmp = Integer.valueOf(editText.getText().toString());
                    if (tmp > max2)
                        editText.setText(String.valueOf(max2));
                    else if (tmp < min2)
                        editText.setText(String.valueOf(min2));
                }
            }
        });
        btndown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = Integer.valueOf(editText.getText().toString());
                if (tmp > min2)
                    editText.setText(String.valueOf(tmp - 1));
            }
        });
    }

    public List<Job> addalljob(List<Job> jobs){
        List<Job> tmp = new ArrayList<>();
        Job alljob = new Job();
        alljob.setText("Semua");
        tmp.add(alljob);
        for (int n=0;n<jobs.size();n++){
            tmp.add(jobs.get(n));
        }
        return tmp;

    }

    public List<Waktu_Kerja> addallwkt(List<Waktu_Kerja> wk){
        List<Waktu_Kerja> tmp = new ArrayList<>();
        Waktu_Kerja alljob = new Waktu_Kerja();
        alljob.setWork_time("Semua");
        tmp.add(alljob);
        for (int n=0;n<wk.size();n++){
            tmp.add(wk.get(n));
        }
        return tmp;

    }

    public List<Place> addallkota(List<Place> places){
        List<Place> tmp = new ArrayList<>();
        Place alljob = new Place();
        alljob.setName("Semua");
        tmp.add(alljob);
        for (int n=0;n<places.size();n++){
            tmp.add(places.get(n));
        }
        return tmp;
    }

}
