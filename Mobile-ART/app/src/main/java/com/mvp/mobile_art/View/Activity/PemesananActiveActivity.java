package com.mvp.mobile_art.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterListKerjaEdit;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.mvp.mobile_art.Model.Array.ArrayBulan;
import com.mvp.mobile_art.Model.Basic.MyTask;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Responses.OrderResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
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
 * Created by jcla123ns on 30/07/17.
 */

public class PemesananActiveActivity extends ParentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerjaEdit rec_Adapter;
    private List<MyTask> myTasks = new ArrayList<>();

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn, profesi, worktime, alamat;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Order order = new Order();
    private Toolbar toolbar;

    private Button btnextra, kembali, terima;
    private ImageButton btnlocation;
    private TextView estimasitext, tugastext;

    private Calendar calendar = Calendar.getInstance();
    private Calendar waktumulai = new GregorianCalendar();
    private Calendar waktuselesai = new GregorianCalendar();
    private Calendar batasmulai = new GregorianCalendar();
    private Calendar batasselesai = new GregorianCalendar();
    private Calendar tempcalendar = new GregorianCalendar();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private StaticData staticData;
    private boolean sdgbrlgsg = false;
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
        terima = (Button) findViewById(R.id.pmsa_btn_terima);
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

        //listkerja
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaEdit(this);
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
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terimaorder(order.getId());
            }
        });
        switch (order.getStatus()){
            case 0:
                btnextra.setText("Tolak");
                terima.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (sdgbrlgsg)
                    btnextra.setText("Selesaikan");
                else btnextra.setText("Kirim Pesan");
                //selesaikan dari member
                break;
            case 3:
                btnextra.setText("Lihat Review");
                break;
        }
        btnextra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (order.getStatus()){
                    case 0:
//                        Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan.", Toast.LENGTH_SHORT).show();
                        tolakorder(order.getId());
                        break;
                    case 1:
                        if (sdgbrlgsg){
                            abuildermessage("Anda tidak dapat mengubah list kerja setelah menyimpan","Selesaikan");
                            abuilder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    gantimystatus(1);
                                }
                            });
                            abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            showalertdialog();
                        }else {
                            Intent intent1 = new Intent(getApplicationContext(), TulisPesanActivity.class);
                            intent1.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
                            startActivity(intent1);
                        }
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan.", Toast.LENGTH_SHORT).show();
//                        Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
//                        intent2.putExtra(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(order));
//                        startActivity(intent2);
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
        if (order.getStatus() == 1){
            checkselesai();
            checksedangberlangsung();
        }
        else if (order.getStatus() == 0){
            checkexpired();
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
    public void terimaorder(final Integer id){
        abuildermessage("Anda yakin ingin menerima pesanan ini?", "Konfirmasi");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<List<Order>> callerjadwal = APIManager.getRepository(OrderRepo.class).getordersByArtstatus(order.getArt_id(), 1);
                callerjadwal.enqueue(new APICallback<List<Order>>() {
                    @Override
                    public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                        super.onSuccess(call, response);
                        if (validasijadwal(response.body())){
                            gantistatus(1);
                        }
                    }

                    @Override
                    public void onError(Call<List<Order>> call, Response<List<Order>> response) {
                        super.onError(call, response);
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        super.onFailure(call, t);
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
    public void tolakorder(final Integer id){
        abuildermessage("Anda yakin ingin menolak pesanan ini?","Konfirmasi");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gantistatus(4);
            }
        });
        abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        showalertdialog();
    }
    public boolean validasijadwal(List<Order> orders){
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
            waktuselesai.setTime(getdateFormat.parse(order.getEnd_date()));
        } catch (ParseException e) {

        }
        for (int n=0;n<orders.size();n++){
            try {
                batasmulai.setTime(getdateFormat.parse(orders.get(n).getStart_date()));
                batasselesai.setTime(getdateFormat.parse(orders.get(n).getEnd_date()));
                batasmulai.add(Calendar.HOUR_OF_DAY, -1);
                batasselesai.add(Calendar.HOUR_OF_DAY, 1);
            } catch (ParseException e) {

            }
            if (waktumulai.after(batasmulai) && waktumulai.before(batasselesai))
                return false;
            if (waktuselesai.after(batasmulai) && waktuselesai.before(batasselesai))
                return false;
        }
        return true;
    }
    public void gantistatus(Integer status){
        initProgressDialog("Sedang memperoses");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("status", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(order.getId().toString(), map);
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
//        String hari = arrayHari.getArrayList().get(Integer.parseInt(hariFormat.format(date)));
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        // Senin, Januari 30
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public void checkselesai(){
        calendar = Calendar.getInstance();
        try {
            waktuselesai.setTime(getdateFormat.parse(order.getEnd_date()));
        } catch (ParseException e) {

        }
        Log.d("Tanggal",getdateFormat.format(calendar.getTime()) + " - " + getdateFormat.format(waktuselesai.getTime()));
        if (calendar.after(waktuselesai)){
            abuildermessage("Pemesanan ini sudah selesai.", "Pemberitahuan");
            abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gantistatus(3);
                }
            });
            showalertdialog();
        }
    }
    public void checkexpired(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
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
    public void checksedangberlangsung(){
        calendar = Calendar.getInstance();
        try {
            waktumulai.setTime(getdateFormat.parse(order.getStart_date()));
        } catch (ParseException e) {

        }
        if (calendar.after(waktumulai)){
            sdgbrlgsg = true;
            if (order.getStatus_art().equals(1)) {
                rec_Adapter.setStatus(false);
                btnextra.setEnabled(false);
            }
            else {
                rec_Adapter.setStatus(true);
            }
        }
    }
    public void updateliststatus(Integer taskid, Integer status){
        HashMap<String,String> map = new HashMap<>();
        map.put("status", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchordertasklistbyid(taskid, map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
            }

            @Override
            public void onError(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void gantimystatus(Integer status){
        initProgressDialog("Sedang memperoses");
        showDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("status_art", status.toString());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(order.getId().toString(), map);
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
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
