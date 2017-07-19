package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Message;
import com.TA.MVP.appmobilemember.Model.Responses.MessageResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.models.Response;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class TulisPesanActivity extends ParentActivity {
    private EditText nama, sub, msg;
    private Button batal, kirim;
    private Toolbar toolbar;
    private Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulispesan);
        Intent i = getIntent();
        message = GsonUtils.getObjectFromJson(i.getStringExtra("msg"), Message.class);

        nama = (EditText) findViewById(R.id.tp_et_reciever);
        sub = (EditText) findViewById(R.id.tp_et_subject);
        msg = (EditText) findViewById(R.id.tp_et_pesan);
        batal = (Button) findViewById(R.id.tp_btn_btl);
        kirim = (Button) findViewById(R.id.tp_btn_krm);

        nama.setText(message.getSender().getName());
        sub.setText(message.getSubject());

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuildermessage("Kirim pesan?", "Pesan");
                abuilder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("sender_id", message.getReceiver().getId().toString());
                        map.put("receiver_id", message.getSender().getId().toString());
                        map.put("subject", sub.getText().toString());
                        map.put("message", msg.getText().toString());
                        map.put("status", "0");
                        Call<MessageResponse> caller = APIManager.getRepository(MessageRepo.class).postmessage(map);
                        caller.enqueue(new APICallback<MessageResponse>() {
                            @Override
                            public void onSuccess(Call<MessageResponse> call, retrofit2.Response<MessageResponse> response) {
                                super.onSuccess(call, response);
                                Toast.makeText(getApplicationContext(),"Pesan Terkirim", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                super.onFailure(call, t);
                            }
                        });
                    }
                });
                abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                showalertdialog();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pesan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
