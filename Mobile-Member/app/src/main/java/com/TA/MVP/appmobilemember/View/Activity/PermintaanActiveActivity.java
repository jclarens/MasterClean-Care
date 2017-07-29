package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListOfferArt;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.OfferArt;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OfferRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentAsistenmini;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 28/07/17.
 */

public class PermintaanActiveActivity extends ParentActivity {
    private RecyclerView recyclerView, recyclerViewart;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerView.LayoutManager rec_LayoutManager2;
    private RecyclerAdapterListKerjaShow rec_Adapter;
    private RecyclerAdapterListOfferArt rec_Adapter2;
    private List<MyTask> myTasks = new ArrayList<>();
    private List<MyTask> defaulttask = new ArrayList<>();
    private List<OfferArt> offerArts = new ArrayList<>();

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Offer offer = new Offer();
    private Toolbar toolbar;

    private Button batal, kembali;
    private TextView estimasitext, tugastext, penerima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_active);
        Intent intent = getIntent();
        offer = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.OFFER_EXTRA), Offer.class);
        defaulttask = ((MasterCleanApplication)getApplication()).getGlobalStaticData().getMyTasks();

        mulaitime = (EditText) findViewById(R.id.mulaitime);
        mulaidate = (EditText) findViewById(R.id.mulaidate);
        selesaitime = (EditText) findViewById(R.id.selesaitime);
        selesaidate = (EditText) findViewById(R.id.selesaidate);
        cttn = (EditText) findViewById(R.id.catatan);
        total = (EditText) findViewById(R.id.total);
        batal = (Button) findViewById(R.id.batalkan);
        kembali = (Button) findViewById(R.id.kembali);
        tugastext = (TextView) findViewById(R.id.tugas);
        penerima = (TextView) findViewById(R.id.penerima);

        try{
            mulaitime.setText(timeFormat.format(getdateFormat.parse(offer.getStart_date())));
            mulaidate.setText(dateFormat.format(getdateFormat.parse(offer.getStart_date())));
            selesaitime.setText(timeFormat.format(getdateFormat.parse(offer.getEnd_date())));
            selesaidate.setText(dateFormat.format(getdateFormat.parse(offer.getEnd_date())));
        }
        catch (ParseException pe){

        }
        cttn.setText(offer.getRemark());
        total.setText(setRP(offer.getCost()));

        //listart
        recyclerViewart = (RecyclerView) findViewById(R.id.listart);
        rec_LayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerViewart.setLayoutManager(rec_LayoutManager2);
        rec_Adapter2 = new RecyclerAdapterListOfferArt(offer, this);
        recyclerViewart.setAdapter(rec_Adapter2);
        rec_Adapter2.setlistart(offerArts);
        getarts();

        //listkerja
        recyclerView = (RecyclerView) findViewById(R.id.listkerja);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaShow();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaulttask(defaulttask);
        rec_Adapter.setList(offer.getOffer_task_list());


        switch (offer.getWork_time_id()){
            case 1:
//                estimasitext.setText("Jam");
                recyclerView.setVisibility(View.VISIBLE);
                tugastext.setVisibility(View.VISIBLE);
                break;
            case 2:
//                estimasitext.setText("Hari");
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
            case 3:
//                estimasitext.setText("Bulan");
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
        }

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Permintaan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void getarts(){
        Call<List<OfferArt>> caller = APIManager.getRepository(OfferRepo.class).getofferarts(offer.getId());
        caller.enqueue(new APICallback<List<OfferArt>>() {
            @Override
            public void onSuccess(Call<List<OfferArt>> call, Response<List<OfferArt>> response) {
                super.onSuccess(call, response);
                rec_Adapter2.setlistart(response.body());
                if (response.body().size() == 0){
                    recyclerViewart.setVisibility(View.GONE);
                }else penerima.setVisibility(View.GONE);
            }

            @Override
            public void onNotFound(Call<List<OfferArt>> call, Response<List<OfferArt>> response) {
                super.onNotFound(call, response);
                Toast.makeText(getApplicationContext(),"Order tidak ditemukan", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<List<OfferArt>> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
