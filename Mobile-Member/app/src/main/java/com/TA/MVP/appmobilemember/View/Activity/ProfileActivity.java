package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 09/06/2017.
 */

public class ProfileActivity extends ParentActivity {
    private Toolbar toolbar;
    private ImageView imgfoto;
    private TextView nama, alamat, notelp, email;
    private Button btnisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgfoto = (ImageView) findViewById(R.id.prof_iv_foto);
        nama = (TextView) findViewById(R.id.prof_tv_nama);
        alamat = (TextView) findViewById(R.id.prof_tv_alamat);
        notelp = (TextView) findViewById(R.id.prof_tv_notelp);
        email = (TextView) findViewById(R.id.prof_tv_email);
        btnisi =(Button) findViewById(R.id.prof_btn_isi);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.profile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.prof_menu_edit:
                //dostartactivity
                doStartActivity(ProfileActivity.this, EditProfileActivity.class);
                break;
            case R.id.prof_menu_editks:
                //dostartactivity
                break;
            case R.id.prof_menu_editdoc:
                //dostartactivity
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
