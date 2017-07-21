package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterJadwal;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class AsistenJadwalActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterJadwal rec_Adapter;
    private List<Order> orders = new ArrayList<>();
    private Toolbar toolbar;
    private User art;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwalart);
        Intent intent = getIntent();
        art = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ART_EXTRA), User.class);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_jadwal);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_jadwalasisten);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterJadwal();
        rec_Adapter.setOrders(orders);
        recyclerView.setAdapter(rec_Adapter);

        Call<List<Order>> caller = APIManager.getRepository(OrderRepo.class).getorderByArt(art.getId().toString());
        caller.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                rec_Adapter.setOrders(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
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
