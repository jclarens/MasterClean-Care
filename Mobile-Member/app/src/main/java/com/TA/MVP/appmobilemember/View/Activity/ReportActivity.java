package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.ReportResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.ReportRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class ReportActivity extends ParentActivity {
    private EditText remark;
    private Button kirim;
    private TextView nama;
    private Toolbar toolbar;
    private User target;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent intent = getIntent();
        target = GsonUtils.getObjectFromJson(intent.getStringExtra("target"), User.class);

        nama = (TextView) findViewById(R.id.nama);
        remark = (EditText) findViewById(R.id.et_remark);
        kirim = (Button) findViewById(R.id.btn_kirim);

        nama.setText(target.getName());

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remark.getText().toString() != ""){
                    abuildermessage("Kirim report ini?","Konfirmasi");
                    abuilder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            lapor();
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
        getSupportActionBar().setTitle("Report");
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
    public void lapor(){
        HashMap<String,String> map = new HashMap<>();
        map.put("user_id", target.getId().toString());
        map.put("remark", remark.getText().toString());
        Call<ReportResponse> caller = APIManager.getRepository(ReportRepo.class).postreport(map);
        caller.enqueue(new APICallback<ReportResponse>() {
            @Override
            public void onSuccess(Call<ReportResponse> call, Response<ReportResponse> response) {
                super.onSuccess(call, response);
                Toast.makeText(getApplicationContext(),"Reported", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Call<ReportResponse> call, Response<ReportResponse> response) {
                super.onError(call, response);
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
