package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.ReviewOrder;
import com.TA.MVP.appmobilemember.Model.Responses.ReviewOrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.ReviewOrderRepo;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentAsistenmini;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 28/07/17.
 */

public class ReviewActivity extends ParentActivity{
    private FragmentAsistenmini fragmentAsistenmini;
    private RatingBar ratingBar;
    private EditText editText;
    private Button kembali, simpan;
    private Order order;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Intent intent = getIntent();
        order = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ORDER_EXTRA), Order.class);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        editText = (EditText) findViewById(R.id.editText);
        kembali = (Button) findViewById(R.id.button_kembali);
        simpan = (Button) findViewById(R.id.button_simpan);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuildermessage("Review tidak dapat diubah setelah disimpan.", "Konfirmasi.");
                abuilder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        postreview();
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

        fragmentAsistenmini = new FragmentAsistenmini();
        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
        fragmentAsistenmini.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragmentAsistenmini).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Review");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));

        getreview();
    }
    public void postreview(){
        initProgressDialog("Sedang memperoses...");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("order_id", order.getId().toString());
        map.put("rate", Float.toString(ratingBar.getRating()));
        map.put("remark", editText.getText().toString());
        Call<ReviewOrderResponse> caller = APIManager.getRepository(ReviewOrderRepo.class).postriview(map, order.getId());
        caller.enqueue(new APICallback<ReviewOrderResponse>() {
            @Override
            public void onSuccess(Call<ReviewOrderResponse> call, Response<ReviewOrderResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Review anda telah tersimpan.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Call<ReviewOrderResponse> call, Response<ReviewOrderResponse> response) {
                super.onError(call, response);
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada server.", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }

            @Override
            public void onFailure(Call<ReviewOrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah, silahkan coba lagi.", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }
        });
    }
    public void getreview(){
        initProgressDialog("Sedang memperoses...");
        showDialog();
        Call<ReviewOrder> caller = APIManager.getRepository(ReviewOrderRepo.class).getriview(order.getId());
        caller.enqueue(new APICallback<ReviewOrder>() {
            @Override
            public void onSuccess(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onSuccess(call, response);
                ratingBar.setIsIndicator(true);
                simpan.setEnabled(false);
                simpan.setVisibility(View.GONE);
                editText.setEnabled(false);
                ratingBar.setRating(response.body().getRate());
                editText.setText(response.body().getRemark());
                dismissDialog();
            }

            @Override
            public void onNotFound(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onNotFound(call, response);
                dismissDialog();

            }

            @Override
            public void onError(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ReviewOrder> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
//                Toast.makeText(getApplicationContext(),"Koneksi bermasalah, silahkan coba lagi", Toast.LENGTH_SHORT).show();
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
