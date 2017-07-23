package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Responses.MyMessageResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.models.GenericResponse;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class BacaPesanMasukActivity extends ParentActivity {
    private EditText nama, sub, msg;
    private Button kembali, hapus, balas;
    private Toolbar toolbar;
    private MyMessage myMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacapesan_masuk);
        Intent i = getIntent();
        myMessage = GsonUtils.getObjectFromJson(i.getStringExtra("msg"), MyMessage.class);

        nama = (EditText) findViewById(R.id.bp_et_sender);
        sub = (EditText) findViewById(R.id.bp_et_subject);
        msg = (EditText) findViewById(R.id.bp_et_pesan);
        kembali = (Button) findViewById(R.id.bp_btn_kembali);
        hapus = (Button) findViewById(R.id.bp_btn_hps);
        balas = (Button) findViewById(R.id.bp_btn_balas);

        nama.setText(myMessage.getSender().getName());
        sub.setText(myMessage.getSubject());
        msg.setText(myMessage.getMessage());

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuildermessage("Hapus pesan ini?","Pesan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<MyMessageResponse> caller = APIManager.getRepository(MessageRepo.class).deletemessage(myMessage.getId().toString());
                        caller.enqueue(new APICallback<MyMessageResponse>() {
                            @Override
                            public void onSuccess(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {
                                super.onSuccess(call, response);
                                Toast.makeText(getApplicationContext(),"Pesan Terhapus", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<MyMessageResponse> call, Throwable t) {
                                super.onFailure(call, t);
                            }

                            @Override
                            public void onNotFound(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {
                                super.onNotFound(call, response);
                                finish();
                            }
                        });
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                showalertdialog();
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        balas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TulisPesanActivity.class);
                intent.putExtra("msg", GsonUtils.getJsonFromObject(myMessage));
                startActivity(intent);
            }
        });

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
