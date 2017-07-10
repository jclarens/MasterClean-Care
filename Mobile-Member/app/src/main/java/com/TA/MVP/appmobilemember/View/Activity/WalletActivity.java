package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterWallet;
import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.Presenter.Repositories.WalletRepo;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.models.GenericResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 19/06/2017.
 */

public class WalletActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterWallet rec_Adapter;
    private Toolbar toolbar;
    private List<Wallet> walletList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_wallet);
        walletList = new ArrayList<>();
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterWallet(walletList);
        recyclerView.setAdapter(rec_Adapter);


        walletList = new ArrayList<>();
        //enqueue
        Call<GenericResponse<List<Wallet>>> caller =  APIManager.getRepository(WalletRepo.class).getAllWallet();
        caller.enqueue(new APICallback<GenericResponse<List<Wallet>>>() {
           @Override
           public void onSuccess(Call<GenericResponse<List<Wallet>>> call, Response<GenericResponse<List<Wallet>>> response) {
               super.onSuccess(call, response);
               walletList = response.body().data;
               rec_Adapter.setnew(walletList);
           }

           @Override
           public void onNotFound(Call<GenericResponse<List<Wallet>>> call, Response<GenericResponse<List<Wallet>>> response) {
               super.onNotFound(call, response);
               Toast.makeText(getApplicationContext(),"Not Found", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(Call<GenericResponse<List<Wallet>>> call, Response<GenericResponse<List<Wallet>>> response) {
               super.onError(call, response);
               Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onFailure(Call<GenericResponse<List<Wallet>>> call, Throwable t) {
               super.onFailure(call, t);
               Toast.makeText(getApplicationContext(),"Failure", Toast.LENGTH_SHORT).show();
           }
        });

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_wallet);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_isi_code:
                doStartActivity(WalletActivity.this, WalletVoucherActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
