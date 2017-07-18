package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.TA.MVP.appmobilemember.Model.Basic.Message;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class BacaPesanActivity extends ParentActivity {
    private EditText nama, sub, msg;
    private Toolbar toolbar;
    private Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacapesan);
        Intent i = getIntent();
        message = GsonUtils.getObjectFromJson(i.getStringExtra("message"), Message.class);

        nama = (EditText) findViewById(R.id.bp_et_sender);
        sub = (EditText) findViewById(R.id.bp_et_subject);
        msg = (EditText) findViewById(R.id.bp_et_pesan);

        nama.setText(message.getSender().getName());
        sub.setText(message.getSubject());
        msg.setText(message.getMessage());

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pesan);
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
