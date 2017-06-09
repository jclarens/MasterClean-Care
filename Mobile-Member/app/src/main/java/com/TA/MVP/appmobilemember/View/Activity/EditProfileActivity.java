package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 09/06/2017.
 */

public class EditProfileActivity extends ParentActivity {
    private Toolbar toolbar;
    private ImageView imgfoto;
    private EditText nama, alamat, notelp, email;
    private Button btnsimpan, btnbatal;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        imgfoto = (ImageView) findViewById(R.id.eprof_iv_foto);
        nama = (EditText) findViewById(R.id.eprof_et_nama);
        alamat = (EditText) findViewById(R.id.eprof_et_alamat);
        notelp = (EditText) findViewById(R.id.eprof_et_notelp);
        email = (EditText) findViewById(R.id.eprof_et_email);
        btnsimpan = (Button) findViewById(R.id.eprof_btn_simpan);
        btnbatal = (Button) findViewById(R.id.eprof_btn_batal);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
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

        context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_editprofile);
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
