package com.TA.MVP.appmobilemember.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Model.Array.ListStatus;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.android.gms.location.places.Place;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class AsistenActivity extends ParentActivity {
    private Toolbar toolbar;
    private TextView nama,usia,notelp,agama,suku,status,keterangan, txtprofesi, txtbhs, kota;
    private RatingBar ratingBar;
    private TextView gajijam, gajihari, gajibulan;
    private CheckBox tktanjg;
    private Button docpndkg, jadwal, pemesanan;
    private int thisYear, artbornyear;
    private Calendar calendar = Calendar.getInstance();
    private Date date;
    private DateFormat yearformat = new SimpleDateFormat("yyyy");
    private User art;
    private ArrayAgama arrayAgama = new ArrayAgama();
    private ListStatus listStatus = new ListStatus();
    private StaticData staticData;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asisten);
        Intent intent = getIntent();
        art = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ART_EXTRA), User.class);
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        initProgressDialog("Loading...");
        showDialog();
        getart(art.getId());

    }

    private void initAllView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        nama = (TextView) findViewById(R.id.asis_tv_nama);
        ratingBar = (RatingBar) findViewById(R.id.asis_ratingBar);
        usia = (TextView) findViewById(R.id.asis_tv_usia);
//        pengalaman = (TextView) findViewById(R.id.asis_tv_pengalaman);
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
        tktanjg = (CheckBox) findViewById(R.id.asis_cb_tktanjg);
//        docpndkg = (Button) findViewById(R.id.asis_btn_docpdkg);
        jadwal = (Button) findViewById(R.id.asis_btn_lhtjdwl);
        pemesanan = (Button) findViewById(R.id.asis_btn_pemesanan);

        setAll();


    }

    private void setAll(){
        nama.setText(art.getName());

        thisYear = calendar.get(Calendar.YEAR);
        artbornyear = Integer.valueOf(yearformat.format(art.getBorn_date()));
        usia.setText(thisYear - artbornyear + " Thn");
//        pengalaman.setText("1 Thn");
        notelp.setText(art.getContact().getPhone());
        agama.setText(arrayAgama.getArrayList().get(art.getReligion()-1));
        suku.setText(art.getRace());
        kota.setText(staticData.getPlaces().get(art.getContact().getCity()-1).getName());
//        keterangan.setText(art.);
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
        temp = "Dapat Berbahasa : ";
        for(int n=0;n<art.getUser_language().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getLanguages().get(art.getUser_language().get(n).getLanguage_id()-1).getLanguage();
        }
        txtbhs.setText(temp);

        //rate
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
        tktanjg.setChecked(false);
        if (art.getUser_additional_info().size() > 0) {
            switch (art.getUser_additional_info().get(0).getInfo_id()){
                case 1:
                    tktanjg.setChecked(true);
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
                dismissDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Reconnect?","No Connection");
                abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDialog();
                    }
                });
                abuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dismissDialog();
                finish();
            }
        });
    }
}
