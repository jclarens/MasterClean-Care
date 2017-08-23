package com.mvp.mobile_art.View.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.mvp.mobile_art.Model.Array.ArrayBulan;
import com.mvp.mobile_art.Model.Basic.MyTask;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.OfferArt;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.OfferResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OfferRepo;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Fragment.FragmentMembermini;
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

public class OfferActivity extends ParentActivity {
    private RecyclerView recyclerView, recyclerViewart;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerView.LayoutManager rec_LayoutManager2;
    private RecyclerAdapterListKerjaShow rec_Adapter;
    private User user = new User();
    private StaticData staticData;
    private FragmentMembermini fragmentMembermini;

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
    private Calendar waktuselesai = new GregorianCalendar();
    private Calendar batasmulai = new GregorianCalendar();
    private Calendar batasselesai = new GregorianCalendar();

    private Offer offer = new Offer();
    private Toolbar toolbar;

    private Button bersedia, kembali;
    private ImageButton btnlocation;
    private TextView estimasitext, tugastext, penerima;
    private Integer mystatus;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_active);
        Intent intent = getIntent();
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
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
        bersedia = (Button) findViewById(R.id.bersedia);
        kembali = (Button) findViewById(R.id.kembali);
        btnlocation = (ImageButton) findViewById(R.id.btnlocation);
        tugastext = (TextView) findViewById(R.id.tugas);
        recyclerView = (RecyclerView) findViewById(R.id.listkerja);

        getuser(offer.getMember_id());

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Penawaran Kerja");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));

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

        //listkerja
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
        switch (offer.getJob_id()){
            case 1:
                break;
            case 2:
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
            case 3:
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
            case 4:
                recyclerView.setVisibility(View.GONE);
                tugastext.setVisibility(View.GONE);
                break;
        }

        switch (offer.getStatus()){
            case 0:
                bersedia.setVisibility(View.VISIBLE);
                break;
            case 1:
                bersedia.setVisibility(View.GONE);
                break;
            case 2:
                bersedia.setVisibility(View.GONE);
                break;
        }

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bersedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getStatus() == 0){
                    Toast.makeText(getApplicationContext(),"Anda sedang tidak aktif", Toast.LENGTH_SHORT).show();
                }
                else {
                    //konfirmasi
                    abuildermessage("Bersedia menerima tawaran ini?", "Konfirmasi");
                    abuilder.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getjadwal();
                        }
                    });
                    abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    showalertdialog();
                }

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
        if (offer.getStatus() == 0){
            checksudahterdaftar("Batalkan");
            checkexpired();
        }
        else if (offer.getStatus() == 1){
            checksudahterdaftar("Hapus");
        }
    }
    public String costumedateformat(Date date){
//        String hari = arrayHari.getArrayList().get(Integer.parseInt(hariFormat.format(date)));
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        // Senin, Januari 30
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
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
        tempp = tempp + numberFormat.format(number);
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
                dismissDialog();
                abuildermessage("Pendaftaran berhasil. Lihat status pada tab Jadwal>Penawaran untuk info lebih lanjut.", "Sukses");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }

            @Override
            public void onError(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }
    public void checksudahterdaftar(final String string){
        //returnnya array
        initProgressDialog("Loading");
        showDialog();
        Call<List<OfferArt>> caller = APIManager.getRepository(OfferRepo.class).getofferartbyid(offer.getId(), user.getId());
        caller.enqueue(new APICallback<List<OfferArt>>() {
            @Override
            public void onSuccess(Call<List<OfferArt>> call, Response<List<OfferArt>> response) {
                super.onSuccess(call, response);
                if (response.body().size() == 1){
                    bersedia.setText(string);
                    bersedia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            abuildermessage(string+"?","Konfirmasi");
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
                    removeartfromoffer();
                    finish();
                }
            });
            showalertdialog();
        }
    }
    public void removeartfromoffer(){
        initProgressDialog("Loading");
        showDialog();
        Call<OfferArt> caller = APIManager.getRepository(OfferRepo.class).deleteofferartbyid(offer.getId(), user.getId());
        caller.enqueue(new APICallback<OfferArt>() {
            @Override
            public void onSuccess(Call<OfferArt> call, Response<OfferArt> response) {
                super.onSuccess(call, response);
                Toast.makeText(getApplicationContext(),"Berhasil dibatalkan", Toast.LENGTH_SHORT).show();
                finish();
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
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void getjadwal(){
        initProgressDialog("Pemesanan sedang diperoses");
        showDialog();
        Call<List<Order>> callerjadwal = APIManager.getRepository(OrderRepo.class).getordersByArtstatus(user.getId(), 1);
        callerjadwal.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                //check jadwal bentrok
                if (validasijadwal(response.body())){
                    addthisarttooffer();
                }
                else Toast.makeText(getApplicationContext(), "Anda tidak dapat menerima pemesanan pada jam ini. Harap periksa jadwal asisten sebelum melakukan pemesanan.", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public boolean validasijadwal(List<Order> orders){
        try {
            waktumulai.setTime(getdateFormat.parse(offer.getStart_date()));
            waktuselesai.setTime(getdateFormat.parse(offer.getEnd_date()));
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        for (int n=0;n<orders.size();n++){
            try {
                batasmulai.setTime(getdateFormat.parse(orders.get(n).getStart_date()));
                batasselesai.setTime(getdateFormat.parse(orders.get(n).getEnd_date()));
                batasmulai.add(Calendar.HOUR_OF_DAY, -1);
                batasselesai.add(Calendar.HOUR_OF_DAY, 1);
            } catch (ParseException e) {
//                e.printStackTrace();
            }
            if (waktumulai.after(batasmulai) && waktumulai.before(batasselesai))
                return false;
            if (waktuselesai.after(batasmulai) && waktuselesai.before(batasselesai))
                return false;
        }
        return true;
    }
    public void getuser(Integer id){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                loadmini(response.body());
                dismissDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                abuildermessage("Koneksi bermasalah. Coba lagi?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getuser(offer.getMember_id());
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }
    public void loadmini(User member){
        fragmentMembermini = new FragmentMembermini();
        Bundle b = new Bundle();
        b.putString(ConstClass.MEMBER_EXTRA, GsonUtils.getJsonFromObject(member));
        fragmentMembermini.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_member, fragmentMembermini).commit();
    }
}
