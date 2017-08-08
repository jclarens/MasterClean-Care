package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListOfferArt;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.OfferArt;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.OfferResponse;
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
import java.util.Date;
import java.util.GregorianCalendar;
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
    private List<OfferArt> offerArts = new ArrayList<>();
    private StaticData staticData;

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn, alamat, profesi, worktime;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private Calendar calendar = Calendar.getInstance();
    private Calendar waktumulai = new GregorianCalendar();

    private Offer offer = new Offer();
    private Toolbar toolbar;

    private Button batal, kembali;
    private ImageButton btnlocation;
    private TextView estimasitext, tugastext, penerima;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_active);
        Intent intent = getIntent();
        offer = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.OFFER_EXTRA), Offer.class);
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mulaitime = (EditText) findViewById(R.id.mulaitime);
        mulaidate = (EditText) findViewById(R.id.mulaidate);
        selesaitime = (EditText) findViewById(R.id.selesaitime);
        selesaidate = (EditText) findViewById(R.id.selesaidate);
        profesi = (EditText) findViewById(R.id.profesi);
        worktime = (EditText) findViewById(R.id.worktime);
        alamat = (EditText) findViewById(R.id.alamat);
        cttn = (EditText) findViewById(R.id.catatan);
        total = (EditText) findViewById(R.id.total);
        batal = (Button) findViewById(R.id.batalkan);
        kembali = (Button) findViewById(R.id.kembali);
        btnlocation = (ImageButton) findViewById(R.id.btnlocation);
        tugastext = (TextView) findViewById(R.id.tugas);
        penerima = (TextView) findViewById(R.id.penerima);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Permintaan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });

        loadtampilan();
    }
    public void loadtampilan(){
        try{
            mulaitime.setText(timeFormat.format(getdateFormat.parse(offer.getStart_date())));
            mulaidate.setText(costumedateformat(getdateFormat.parse(offer.getStart_date())));
            selesaitime.setText(timeFormat.format(getdateFormat.parse(offer.getEnd_date())));
            selesaidate.setText(costumedateformat(getdateFormat.parse(offer.getEnd_date())));
        }
        catch (ParseException pe){

        }

        profesi.setText(staticData.getJobs().get(offer.getJob_id()-1).getJob());
        worktime.setText(staticData.getWaktu_kerjas().get(offer.getWork_time_id()-1).getWork_time());
        alamat.setText(offer.getContact().getAddress());
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
        rec_Adapter.setDefaulttask(staticData.getMyTasks());
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

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuildermessage("Batalkan penawaran ini?", "Konfirmasi pembatalan");
                abuilder.setPositiveButton("Batalkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        batalkanoffer();
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
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewLocationActivity.class);
                intent.putExtra("location", offer.getContact().getLocation());
                intent.putExtra("alamat", offer.getContact().getAddress());
                startActivity(intent);
            }
        });

        if (offer.getStatus() == 0)
            checkexpired();
    }
    public void checkexpired(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(offer.getStart_date()));
        } catch (ParseException e) {

        }
        if (calendar.after(waktumulai)){
            abuildermessage("Pemesanan ini sudah tidak dapat diterima. Pemesanan ini dibatalkan.", "Pemberitahuan");
            abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    batalkanoffer();
                }
            });
            showalertdialog();
        }
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
    public void batalkanoffer(){
        initProgressDialog("Sedang membatalkan...");
        HashMap<String,Object> map = new HashMap<>();
        map.put("status", "2");
        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).patchoffer(offer.getId(), map);
        caller.enqueue(new APICallback<OfferResponse>() {
            @Override
            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                finish();
            }

            @Override
            public void onNotFound(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onNotFound(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }
    public String costumedateformat(Date date){
//        String hari = arrayHari.getArrayList().get(Integer.parseInt(hariFormat.format(date)));
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date)) - 1);
        // Senin, Januari 30
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public void reload(){
        Call<Offer> caller = APIManager.getRepository(OfferRepo.class).getofferById(offer.getId());
        caller.enqueue(new APICallback<Offer>() {
            @Override
            public void onSuccess(Call<Offer> call, Response<Offer> response) {
                super.onSuccess(call, response);
                offer = response.body();
                loadtampilan();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Call<Offer> call, Response<Offer> response) {
                super.onError(call, response);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
