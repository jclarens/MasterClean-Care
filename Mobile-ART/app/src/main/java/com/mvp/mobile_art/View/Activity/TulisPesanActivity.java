package com.mvp.mobile_art.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.MyMessage;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.MyMessageResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.MessageRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class TulisPesanActivity extends ParentActivity {
    private EditText nama, sub, msg;
    private Button batal, kirim;
    private Toolbar toolbar;
    private MyMessage myMessage;
    private User art;
    private User user;
    private Integer targetid;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulispesan);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        nama = (EditText) findViewById(R.id.tp_et_reciever);
        sub = (EditText) findViewById(R.id.tp_et_subject);
        msg = (EditText) findViewById(R.id.tp_et_pesan);
        batal = (Button) findViewById(R.id.tp_btn_btl);
        kirim = (Button) findViewById(R.id.tp_btn_krm);

        Intent i = getIntent();
        if (i.getStringExtra("msg") != null){
            myMessage = GsonUtils.getObjectFromJson(i.getStringExtra("msg"), MyMessage.class);
            nama.setText(myMessage.getSender_id().getName());
            sub.setText(myMessage.getSubject());
            targetid = myMessage.getSender_id().getId();
        }
        else if (i.getStringExtra(ConstClass.ART_EXTRA) != null){
            art = GsonUtils.getObjectFromJson(i.getStringExtra(ConstClass.ART_EXTRA), User.class);
            nama.setText(art.getName());
            targetid = art.getId();
        }
        else
            Toast.makeText(getApplicationContext(),"Tujuan tidak ditemukan", Toast.LENGTH_SHORT).show();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sub.getText().toString().equals("") || msg.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Pesan dan Subject Tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }
                else {
                    abuildermessage("Kirim pesan?", "Pesan");
                    abuilder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sendmessage();
                        }
                    });
                    abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    showalertdialog();
                }
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kirim pesan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));
    }
    public void sendmessage(){
        initProgressDialog("Mengirim pesan.");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("sender_id", user.getId().toString());
        map.put("receiver_id", targetid.toString());
        map.put("subject", sub.getText().toString());
        map.put("message", msg.getText().toString());
        map.put("status_member", "0");
        map.put("status_art", "1");
        Log.d("testing", map.toString());
        Call<MyMessageResponse> caller = APIManager.getRepository(MessageRepo.class).postmessage(map);
        caller.enqueue(new APICallback<MyMessageResponse>() {
            @Override
            public void onSuccess(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Pesan terkirim", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<MyMessageResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                dismissDialog();
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
}