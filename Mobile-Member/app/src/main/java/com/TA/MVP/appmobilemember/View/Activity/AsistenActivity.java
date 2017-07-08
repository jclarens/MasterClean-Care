package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Presenter.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.models.GenericResponse;

import retrofit2.Call;
import retrofit2.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten);
        initAllView();

        //Ini cara panggil function untuk request
        Call<GenericResponse<ArrayAgama>> caller =  APIManager.getRepository(UserRepo.class).ambeAgama("nama");
        // pemanggilan enqueue untuk request ke server.
        caller.enqueue(new APICallback<GenericResponse<ArrayAgama>>() {

            @Override
            public void onSuccess(Call<GenericResponse<ArrayAgama>> call, Response<GenericResponse<ArrayAgama>> response) {
                super.onSuccess(call, response);
                //ini untuk ambe data dari response json jika menggunakan GenericResponse class.
//                response.body().data;
                //jika gk gunakan Generic response
                //response.body();
            }

            @Override
            public void onNotFound(Call<GenericResponse<ArrayAgama>> call, Response<GenericResponse<ArrayAgama>> response) {
                super.onNotFound(call, response);
            }

            @Override
            public void onError(Call<GenericResponse<ArrayAgama>> call, Response<GenericResponse<ArrayAgama>> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<GenericResponse<ArrayAgama>> call, Throwable t) {
                super.onFailure(call, t);
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
