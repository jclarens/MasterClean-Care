package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class AsistenActivity extends ParentActivity {
    private Toolbar toolbar;
    private TextView nama,usia,pengalaman,notelp,agama,suku,status,keterangan;
    private TextView gajijam, gajihari, gajibulan;
    private LinearLayout layoutprof;
    private CheckBox inggris, mandarin, melayu, tktanjg;
    private Button docpndkg, jadwal, pemesanan, kirimpesan;
    private User art;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten);
        Intent intent = getIntent();
        art = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ART_EXTRA), User.class);
        initAllView();

        kirimpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPref.getValueString(ConstClass.USER) == "")
                    Toast.makeText(getApplicationContext(),"Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                else {

                }
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AsistenJadwalActivity.class);
                i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                startActivity(i);
            }
        });
        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPref.getValueString(ConstClass.USER) == "")
                    Toast.makeText(getApplicationContext(),"Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(getApplicationContext(), PemesananActivity.class);
                    i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                    startActivity(i);
                }

            }
        });

    }

    private void initAllView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        nama = (TextView) findViewById(R.id.asis_tv_nama);
        usia = (TextView) findViewById(R.id.asis_tv_usia);
        pengalaman = (TextView) findViewById(R.id.asis_tv_pengalaman);
        notelp = (TextView) findViewById(R.id.asis_tv_notelp);
        agama = (TextView) findViewById(R.id.asis_tv_agama);
        suku = (TextView) findViewById(R.id.asis_tv_suku);
        status = (TextView) findViewById(R.id.asis_tv_status);
        keterangan = (TextView) findViewById(R.id.asis_tv_keterangan);
        gajijam = (TextView) findViewById(R.id.asis_tv_gajijam);
        gajihari = (TextView) findViewById(R.id.asis_tv_gajihari);
        gajibulan = (TextView) findViewById(R.id.asis_tv_gajibulan);
        layoutprof = (LinearLayout) findViewById(R.id.asis_alyout_prof);
        inggris = (CheckBox) findViewById(R.id.asis_cb_inggris);
        mandarin = (CheckBox) findViewById(R.id.asis_cb_mandarin);
        melayu = (CheckBox) findViewById(R.id.asis_cb_melayu);
        tktanjg = (CheckBox) findViewById(R.id.asis_cb_tktanjg);
        docpndkg = (Button) findViewById(R.id.asis_btn_docpdkg);
        jadwal = (Button) findViewById(R.id.asis_btn_lhtjdwl);
        pemesanan = (Button) findViewById(R.id.asis_btn_pemesanan);
        kirimpesan = (Button) findViewById(R.id.asis_btn_kirimpesan);

        setAll();


    }

    private void setAll(){
        nama.setText(art.getName());

        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PemesananActivity.class);
                i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                startActivity(i);
            }
        });
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_asisten);
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
