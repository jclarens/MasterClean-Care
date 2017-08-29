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
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentAsistenmini;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 28/06/2017.
 */

public class PemesananActiveActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerjaShow rec_Adapter;

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn, alamat, profesi, worktime;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Order order = new Order();
    private Toolbar toolbar;
    private FragmentAsistenmini fragmentAsistenmini;

    private Button btnextra, kembali;
    private ImageButton btnlocation;
    private TextView tugastext;
    private boolean sdgbrlgsg = false;
    private boolean dptdiselesaikan = false;

    private Calendar calendar = Calendar.getInstance();
    private Calendar waktumulai = new GregorianCalendar();
    private Calendar waktuselesai = new GregorianCalendar();
    private Calendar waktutemp = new GregorianCalendar();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private StaticData staticData;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_active);
        Intent intent = getIntent();
        order = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ORDER_EXTRA), Order.class);
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mulaitime = (EditText) findViewById(R.id.pmsa_et_mulaitime);
        mulaidate = (EditText) findViewById(R.id.pmsa_et_mulaidate);
        selesaitime = (EditText) findViewById(R.id.pmsa_et_selesaitime);
        selesaidate = (EditText) findViewById(R.id.pmsa_et_selesaidate);
        profesi = (EditText) findViewById(R.id.profesi);
        worktime = (EditText) findViewById(R.id.worktime);
        alamat = (EditText) findViewById(R.id.pmsa_et_alamat);
        cttn = (EditText) findViewById(R.id.pmsa_et_catatan);
        total = (EditText) findViewById(R.id.pmsa_et_total);
        btnextra = (Button) findViewById(R.id.pmsa_btn_extra);
        kembali = (Button) findViewById(R.id.pmsa_btn_kembali);
        btnlocation = (ImageButton) findViewById(R.id.btnlocation);
        tugastext = (TextView) findViewById(R.id.pmsa_tv_tugas);
        recyclerView = (RecyclerView) findViewById(R.id.pmsa_rec_listkerja);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pemesanan);
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
        if (order.getStatus() == 1){
            checkselesai();
            checksedangberlangsung();
        }
        else if (order.getStatus() == 0){
            checkexpired();
        }

        switch (order.getStatus()){
            case 0:
                btnextra.setText("Batalkan");
                break;
            case 1:
                if (sdgbrlgsg)
                    btnextra.setText("Selesai");
                else
                    btnextra.setText("Kirim Pesan");
                break;
            case 2:
//                btnextra.setText("Hapus");
                btnextra.setVisibility(View.GONE);
                break;
            case 3:
                btnextra.setText("Review");
                break;
            case 4:
//                btnextra.setText("Hapus");
                btnextra.setVisibility(View.GONE);
                break;
            case 5:
                btnextra.setText("Laporkan");
//                btnextra.setVisibility(View.GONE);
                break;
        }

        try{
            mulaitime.setText(timeFormat.format(getdateFormat.parse(order.getStart_date())));
            mulaidate.setText(costumedateformat(getdateFormat.parse(order.getStart_date())));
            selesaitime.setText(timeFormat.format(getdateFormat.parse(order.getEnd_date())));
            selesaidate.setText(costumedateformat(getdateFormat.parse(order.getEnd_date())));
        }
        catch (ParseException pe){

        }

        profesi.setText(staticData.getJobs().get(order.getJob_id()-1).getJob());
        worktime.setText(staticData.getWaktu_kerjas().get(order.getWork_time_id()-1).getWork_time());
        alamat.setText(order.getContact().getAddress());

        cttn.setText(order.getRemark());
        total.setText(setRP(order.getCost()));

        fragmentAsistenmini = new FragmentAsistenmini();
        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
        fragmentAsistenmini.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragmentAsistenmini).commit();

        //listkerja
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaShow();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaulttask(staticData.getMyTasks());
        rec_Adapter.setList(order.getOrder_task_list());
        switch (order.getWork_time_id()){
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
        switch (order.getJob_id()){
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

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnextra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (order.getStatus()){
                    case 0:
                        batalkanorder(order.getId());
                        break;
                    case 1:
                        if (sdgbrlgsg){
                            if (dptdiselesaikan) {
                                abuildermessage("Asisten sudah menyelesaikan tugasnya?", "Konfirmasi");
                                abuilder.setPositiveButton("sudah", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        gantimystatus(1);
                                    }
                                });
                                abuilder.setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                showalertdialog();
                            }else {
                                Toast.makeText(getApplicationContext(),"Asisten sedang mengerjakan pemesanan anda.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Intent intent1 = new Intent(getApplicationContext(), TulisPesanActivity.class);
                            intent1.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
                            startActivity(intent1);
                        }
                        break;
                    case 2:
//                        abuildermessage("Hapus riwayat pemesanan ini?","Konfirmasi");
//                        abuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteorder();
//                            }
//                        });
//                        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        showalertdialog();
                        break;
                    case 3:
                        Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
                        intent2.putExtra(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(order));
                        startActivity(intent2);
                        break;
                    case 4:
//                        abuildermessage("Hapus riwayat pemesanan ini?","Konfirmasi");
//                        abuilder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteorder();
//                            }
//                        });
//                        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        showalertdialog();
                        break;
                    case 5:
                        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                        intent.putExtra("target", GsonUtils.getJsonFromObject(order.getArt()));
                        intent.putExtra("orderid", GsonUtils.getJsonFromObject(order.getId()));
                        startActivity(intent);
                        break;
                }
            }
        });
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewLocationActivity.class);
                intent.putExtra("location", order.getContact().getLocation());
                intent.putExtra("alamat", order.getContact().getAddress());
                startActivity(intent);
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
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
    public void batalkanorder(final Integer id){
        abuildermessage("Anda yakin ingin membatalkan pesanan ini?","Konfirmasi pembatalan");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initProgressDialog("Sedang membatalkan pemesanan");
                showDialog();
                gantistatus(2);
            }
        });
        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        showalertdialog();
    }
    public void deleteorder(){
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).deleteorderById(order.getId().toString());
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                finish();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Gagal membatalkan order, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onNotFound(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onNotFound(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Order sudah tidak ditemukan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void checkselesai(){
        calendar = Calendar.getInstance();
        try {
            waktuselesai.setTime(getdateFormat.parse(order.getEnd_date()));
        } catch (ParseException e) {

        }
        if (calendar.after(waktuselesai)){
            if (order.getStatus_member() == 1 && order.getStatus_art() == 1) {
                btnextra.setVisibility(View.VISIBLE);
                abuildermessage("Pemesanan ini tidak anda konfirmasi. Silahkan laporkan pada tab riwayat pemesanan.", "Pemberitahuan");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    gantistatus(5);
                    }
                });
                abuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        gantistatus(5);
                    }
                });
                showalertdialog();
            } else {
                waktuselesai.add(Calendar.HOUR_OF_DAY, 1);
                if (calendar.after(waktuselesai)){
                    if (order.getStatus_member() == 0 || order.getStatus_art() == 0) {
                        abuildermessage("Pemesanan ini tidak dikonfirmasi oleh salah satu pihak member atau asisten, silahkan laporkan masalah ini pada tab Riwayat>Pemesanan>Laporkan.", "Pemberitahuan");
                        abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                gantistatus(5);
                            }
                        });
                        showalertdialog();
                    }
                }
                //else jika 1 jam setelah selesai, sebelum expired selesai
            }
        }
    }
    public void gantimystatus(Integer status){
        initProgressDialog("Sedang memperoses");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("status_member", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(order.getId(), map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                abuildermessage("Pemesanan ini sudah selesai. anda dapat melakukan review pada tab Riwayat", "Pemberitahuan");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                abuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                showalertdialog();
            }

            @Override
            public void onError(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void checksedangberlangsung(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
//            waktumulai.add(Calendar.MINUTE, -10); // bisa mulai kerja 10 sebelum waktunya
        } catch (ParseException e) {

        }
        if (calendar.after(waktumulai)){
            sdgbrlgsg = true;
            btnextra.setText("Selesai");
            if (order.getStatus_member().equals(1)) {
                btnextra.setEnabled(false);
                btnextra.setVisibility(View.GONE);
            }
            if (order.getStatus_art().equals(1)){
                dptdiselesaikan = true;
            }
        }
    }
    public void checkexpired(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
            waktumulai.add(Calendar.HOUR_OF_DAY, -1);
        } catch (ParseException e) {

        }
        if (calendar.after(waktumulai)){
            abuildermessage("Pemesanan ini sudah tidak dapat diterima. Pemesanan ini dibatalkan.", "Pemberitahuan");
            abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gantistatus(2);
                }
            });
            showalertdialog();
        }
    }
    public void gantistatus(Integer status){
        initProgressDialog("Sedang memperoses");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("status", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(order.getId(), map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                finish();
            }

            @Override
            public void onError(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date)) - 1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public void reload(){
        Call<Order> caller = APIManager.getRepository(OrderRepo.class).getorderById(order.getId());
        caller.enqueue(new APICallback<Order>() {
            @Override
            public void onSuccess(Call<Order> call, Response<Order> response) {
                super.onSuccess(call, response);
                order = response.body();
                loadtampilan();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Call<Order> call, Response<Order> response) {
                super.onError(call, response);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
