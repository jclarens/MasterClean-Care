package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterLogWallet;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterWallet;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.WalletTransaction;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 18/07/2017.
 */

public class LogWalletActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterLogWallet rec_Adapter;
    private Toolbar toolbar;
    private User user;
    private List<WalletTransaction> walletTransactions = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logwallet);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_logwallet);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_logwallet);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterLogWallet();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setLogWallets(walletTransactions);

        Call<List<WalletTransaction>> caller = APIManager.getRepository(UserRepo.class).getwallettrans(user.getId());
        caller.enqueue(new APICallback<List<WalletTransaction>>() {
            @Override
            public void onSuccess(Call<List<WalletTransaction>> call, Response<List<WalletTransaction>> response) {
                super.onSuccess(call, response);
                rec_Adapter.setLogWallets(response.body());
            }

            @Override
            public void onFailure(Call<List<WalletTransaction>> call, Throwable t) {
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
