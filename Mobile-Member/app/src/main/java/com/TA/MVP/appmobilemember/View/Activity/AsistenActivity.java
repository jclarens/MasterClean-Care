package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterReview;
import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Model.Array.ListStatus;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.RoundedTransformation;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class AsistenActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterReview rec_Adapter;
    private Toolbar toolbar;
    private TextView nama,usia,notelp,agama,suku,status,keterangan, txtprofesi, txtbhs, kota;
    private RatingBar ratingBar;
    private TextView gajijam, gajihari, gajibulan, tktanjg;
    private Button docpndkg, jadwal, pemesanan;
    private int thisYear, artbornyear;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat yearformat = new SimpleDateFormat("yyyy");
    private User art;
    private ArrayAgama arrayAgama = new ArrayAgama();
    private ListStatus listStatus = new ListStatus();
    private StaticData staticData;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private LinearLayout layoutreview, layoutgaji, layoutstatus;
    private ImageView imageView;
    private boolean minidetail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten);
        Intent intent = getIntent();
        art = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ART_EXTRA), User.class);
        if (intent.getStringExtra("minidetail") != null)
            minidetail = true;
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        recyclerView = (RecyclerView) findViewById(R.id.recycleview_review);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterReview(this);
        recyclerView.setAdapter(rec_Adapter);

        initProgressDialog("Loading...");
        showDialog();
        getart(art.getId());

    }

    private void initAllView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        nama = (TextView) findViewById(R.id.asis_tv_nama);
        ratingBar = (RatingBar) findViewById(R.id.asis_ratingBar);
        usia = (TextView) findViewById(R.id.asis_tv_usia);
        notelp = (TextView) findViewById(R.id.asis_tv_notelp);
        agama = (TextView) findViewById(R.id.asis_tv_agama);
        suku = (TextView) findViewById(R.id.asis_tv_suku);
        kota = (TextView) findViewById(R.id.asis_tv_kota);
        status = (TextView) findViewById(R.id.asis_tv_status);
        keterangan = (TextView) findViewById(R.id.asis_tv_keterangan);
        gajijam = (TextView) findViewById(R.id.asis_tv_gajijam);
        gajihari = (TextView) findViewById(R.id.asis_tv_gajihari);
        gajibulan = (TextView) findViewById(R.id.asis_tv_gajibulan);
        txtprofesi = (TextView) findViewById(R.id.asis_tv_profesi);
        txtbhs = (TextView) findViewById(R.id.asis_tv_bhs);
        tktanjg = (TextView) findViewById(R.id.asis_cb_tktanjg);
        jadwal = (Button) findViewById(R.id.asis_btn_lhtjdwl);
        pemesanan = (Button) findViewById(R.id.asis_btn_pemesanan);
        layoutreview = (LinearLayout) findViewById(R.id.layout_review);
        layoutgaji = (LinearLayout) findViewById(R.id.layout_gaji);
        layoutstatus = (LinearLayout) findViewById(R.id.layout_status);

        setAll();


    }

    private void setAll(){
        if (minidetail){
            layoutstatus.setVisibility(View.GONE);
            layoutgaji.setVisibility(View.GONE);
            layoutreview.setVisibility(View.GONE);
        }

        nama.setText(art.getName());

        try{
            keterangan.setText(art.getDescription());
        }catch (NullPointerException e){

        }

        thisYear = calendar.get(Calendar.YEAR);
        artbornyear = Integer.valueOf(yearformat.format(art.getBorn_date()));
        usia.setText(thisYear - artbornyear + " Thn");
        notelp.setText(art.getContact().getPhone());
        agama.setText(arrayAgama.getArrayList().get(art.getReligion()-1));
        suku.setText(art.getRace());
        kota.setText(staticData.getPlaces().get(art.getContact().getCity()-1).getName());
        status.setText(listStatus.getStatus().get(art.getStatus()));
        if (art.getStatus() == 1){
            status.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroundgreen));
        }
        else status.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.backgroundred));
        String temp = "";
        for(int n=0;n<art.getUser_job().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getJobs().get(art.getUser_job().get(n).getJob_id()-1);
        }
        txtprofesi.setText(temp);
        temp = "";
        for(int n=0;n<art.getUser_language().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getLanguages().get(art.getUser_language().get(n).getLanguage_id()-1).getLanguage();
        }
        txtbhs.setText(temp);

        try{
            ratingBar.setRating(art.getRate());
        }
        catch (Exception e){
            ratingBar.setRating(0);
        }


        for (int n=0;n<art.getUser_work_time().size();n++){
            switch (art.getUser_work_time().get(n).getWork_time_id()){
                case 1:
                    gajijam.setText(setRP(art.getUser_work_time().get(n).getCost()));
                    break;
                case 2:
                    gajihari.setText(setRP(art.getUser_work_time().get(n).getCost()));
                    break;
                case 3:
                    gajibulan.setText(setRP(art.getUser_work_time().get(n).getCost()));
                    break;
            }
        }
//        tktanjg.setChecked(false);
        tktanjg.setVisibility(View.GONE);
        if (art.getUser_additional_info().size() > 0) {
            switch (art.getUser_additional_info().get(0).getInfo_id()){
                case 1:
//                    tktanjg.setChecked(true);
                    tktanjg.setVisibility(View.VISIBLE);
                    break;
            }
        }
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AsistenJadwalActivity.class);
                i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                startActivity(i);
            }
        });
        pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPref.getValueString(ConstClass.USER) == "")
                    Toast.makeText(getApplicationContext(),"Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                else if (art.getStatus() != 1){
                    Toast.makeText(getApplicationContext(),"Asisten ini sedang tidak aktif", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(getApplicationContext(), PemesananActivity.class);
                    i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                    startActivity(i);
                }
            }
        });

        Picasso.with(getApplicationContext())
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+art.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .transform(new RoundedTransformation(10, 0))
                .into(imageView);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_asisten);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asisten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.prof_menu_pesan:
                if (SharedPref.getValueString(ConstClass.USER) == "")
                    Toast.makeText(getApplicationContext(),"Silahkan login terlebih dahulu untuk mengirim pesan", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(getApplicationContext(), TulisPesanActivity.class);
                    i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                    startActivity(i);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void getart(Integer id){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                art = response.body();
                initAllView();
                getreviews();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                dismissDialog();
                finish();
            }
        });
    }
    public void getreviews(){
        Call<List<Order>> caller = APIManager.getRepository(OrderRepo.class).getorderreviewByArt(art.getId());
        caller.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                //setrecview
                if (response.body().size() > 0){
                    rec_Adapter.setlist(response.body());
                    layoutreview.setVisibility(View.VISIBLE);
                }
                dismissDialog();
            }

            @Override
            public void onError(Call<List<Order>> call, Response<List<Order>> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }
}
