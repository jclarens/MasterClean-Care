package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class EditPassActivity extends ParentActivity{
    private Toolbar toolbar;
    private EditText pass, newpass, knewpass;
    private Button btnsimpan, btnbatal;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpass);

        pass = (EditText) findViewById(R.id.epass_et_pass);
        newpass = (EditText) findViewById(R.id.epass_et_newpass);
        knewpass = (EditText) findViewById(R.id.epass_et_knewpass);
        btnsimpan = (Button) findViewById(R.id.epass_btn_simpan);
        btnbatal = (Button) findViewById(R.id.epass_btn_batal);

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
        getSupportActionBar().setTitle(R.string.toolbar_editpass);
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
