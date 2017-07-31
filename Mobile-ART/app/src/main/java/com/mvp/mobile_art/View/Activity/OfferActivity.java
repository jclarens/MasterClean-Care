package com.mvp.mobile_art.View.Activity;

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

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.mvp.mobile_art.Model.Basic.MyTask;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.OfferArt;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.OfferResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OfferRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

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

public class OfferActivity extends ParentActivity {
    private RecyclerView recyclerView, recyclerViewart;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerView.LayoutManager rec_LayoutManager2;
    private RecyclerAdapterListKerjaShow rec_Adapter;
//    private RecyclerAdapterListOfferArt rec_Adapter2;
    private List<MyTask> myTasks = new ArrayList<>();
    private List<MyTask> defaulttask = new ArrayList<>();
//    private List<OfferArt> offerArts = new ArrayList<>();
    private User user = new User();

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Offer offer = new Offer();
    private Toolbar toolbar;

    private Button bersedia, kembali;
    private TextView estimasitext, tugastext, penerima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_active);
        Intent intent = getIntent();
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        offer = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.OFFER_EXTRA), Offer.class);
        defaulttask = ((MasterCleanApplication)getApplication()).getGlobalStaticData().getMyTasks();

        mulaitime = (EditText) findViewById(R.id.mulaitime);
        mulaidate = (EditText) findViewById(R.id.mulaidate);
        selesaitime = (EditText) findViewById(R.id.selesaitime);
        selesaidate = (EditText) findViewById(R.id.selesaidate);
        cttn = (EditText) findViewById(R.id.catatan);
        total = (EditText) findViewById(R.id.total);
        bersedia = (Button) findViewById(R.id.bersedia);
        kembali = (Button) findViewById(R.id.kembali);
        tugastext = (TextView) findViewById(R.id.tugas);
//        penerima = (TextView) findViewById(R.id.penerima);

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
//        recyclerViewart = (RecyclerView) findViewById(R.id.listart);
//        rec_LayoutManager2 = new LinearLayoutManager(getApplicationContext());
//        recyclerViewart.setLayoutManager(rec_LayoutManager2);
//        rec_Adapter2 = new RecyclerAdapterListOfferArt(offer, this);
//        recyclerViewart.setAdapter(rec_Adapter2);
//        rec_Adapter2.setlistart(offerArts);
//        getarts();

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
        bersedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //konfirmasi
                abuildermessage("Bersedia menerima tawaran ini?", "Konfirmasi");
                abuilder.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addthisarttooffer();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                showalertdialog();
//                Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan", Toast.LENGTH_SHORT).show();

            }
        });
        checksudahterdaftar();
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
    public void addthisarttooffer(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("offer_id", offer.getId().toString());
        map.put("art_id", user.getId().toString());
        map.put("status", "0");
        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).postartoffer(offer.getId(), map);
        caller.enqueue(new APICallback<OfferResponse>() {
            @Override
            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onSuccess(call, response);
                abuildermessage("Pendaftaran berhasil.", "Sukses");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismissDialog();
                        finish();
                    }
                });
                showalertdialog();
            }

            @Override
            public void onError(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void checksudahterdaftar(){
        //returnnya array
        initProgressDialog("Loading");
        showDialog();
        Call<List<OfferArt>> caller = APIManager.getRepository(OfferRepo.class).getofferartbyid(offer.getId(), user.getId());
        caller.enqueue(new APICallback<List<OfferArt>>() {
            @Override
            public void onSuccess(Call<List<OfferArt>> call, Response<List<OfferArt>> response) {
                super.onSuccess(call, response);
                if (response.body().size() == 1){
                    bersedia.setText("Batalkan");
                    bersedia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            abuildermessage("Batalkan?","Konfirmasi");
                            abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    removeartfromoffer();
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
                }
                dismissDialog();
            }

            @Override
            public void onError(Call<List<OfferArt>> call, Response<List<OfferArt>> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<List<OfferArt>> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }
    public void removeartfromoffer(){
        initProgressDialog("Loading");
        showDialog();
        Call<OfferArt> caller = APIManager.getRepository(OfferRepo.class).deleteofferartbyid(offer.getId(), user.getId());
        caller.enqueue(new APICallback<OfferArt>() {
            @Override
            public void onSuccess(Call<OfferArt> call, Response<OfferArt> response) {
                super.onSuccess(call, response);
                bersedia.setText("Bersedia");
                bersedia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        abuildermessage("Bersedia menerima tawaran ini?", "Konfirmasi");
                        abuilder.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bersedia.setText("Batalkan");
                                bersedia.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        addthisarttooffer();
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
                dismissDialog();
            }

            @Override
            public void onError(Call<OfferArt> call, Response<OfferArt> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<OfferArt> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }
}
