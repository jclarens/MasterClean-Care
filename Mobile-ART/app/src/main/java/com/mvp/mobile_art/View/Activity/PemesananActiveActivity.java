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
import com.mvp.mobile_art.Model.Basic.Order;
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
    private RecyclerAdapterListKerjaShow rec_Adapter;
    private List<MyTask> myTasks = new ArrayList<>();
    private List<MyTask> defaulttask = new ArrayList<>();

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Order order = new Order();
    private Toolbar toolbar;

    private Button btnextra, kembali, terima;
    private TextView estimasitext, tugastext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_active);
        Intent intent = getIntent();
        order = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ORDER_EXTRA), Order.class);
        defaulttask = ((MasterCleanApplication)getApplication()).getGlobalStaticData().getMyTasks();

        mulaitime = (EditText) findViewById(R.id.pmsa_et_mulaitime);
        mulaidate = (EditText) findViewById(R.id.pmsa_et_mulaidate);
        selesaitime = (EditText) findViewById(R.id.pmsa_et_selesaitime);
        selesaidate = (EditText) findViewById(R.id.pmsa_et_selesaidate);
        cttn = (EditText) findViewById(R.id.pmsa_et_catatan);
        total = (EditText) findViewById(R.id.pmsa_et_total);
        btnextra = (Button) findViewById(R.id.pmsa_btn_extra);
        terima = (Button) findViewById(R.id.pmsa_btn_terima);
        kembali = (Button) findViewById(R.id.pmsa_btn_kembali);
//        estimasitext = (TextView) findViewById(R.id.pmsa_tv_estimasiwaktu);
        tugastext = (TextView) findViewById(R.id.pmsa_tv_tugas);

        switch (order.getStatus()){
            case 0:
                btnextra.setText("Tolak");
                terima.setVisibility(View.VISIBLE);
                break;
            case 1:
                btnextra.setText("Selesaikan");
                //selesaikan dari member
                break;
            case 3:
                btnextra.setText("Lihat Review");
                break;
        }


        try{
            mulaitime.setText(timeFormat.format(getdateFormat.parse(order.getStart_date())));
            mulaidate.setText(dateFormat.format(getdateFormat.parse(order.getStart_date())));
            selesaitime.setText(timeFormat.format(getdateFormat.parse(order.getEnd_date())));
            selesaidate.setText(dateFormat.format(getdateFormat.parse(order.getEnd_date())));
        }
        catch (ParseException pe){

        }
        cttn.setText(order.getRemark());
        total.setText(setRP(order.getCost()));

        //listkerja
        recyclerView = (RecyclerView) findViewById(R.id.pmsa_rec_listkerja);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaShow();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaulttask(defaulttask);
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

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_pemesanan);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
//                        Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan.", Toast.LENGTH_SHORT).show();
                        tolakorder(order.getId());
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan.", Toast.LENGTH_SHORT).show();
//                        Intent intent1 = new Intent(getApplicationContext(), TulisPesanActivity.class);
//                        intent1.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
//                        startActivity(intent1);
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
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terimaorder(order.getId());
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
    public void terimaorder(final Integer id){
        //validasi waktu kerja art========================================================================================================
        abuildermessage("Anda yakin ingin menerima pesanan ini?","Konfirmasi");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<Order> caller = APIManager.getRepository(OrderRepo.class).getorderById(id.toString());
                caller.enqueue(new APICallback<Order>() {
                    @Override
                    public void onSuccess(Call<Order> call, Response<Order> response) {
                        super.onSuccess(call, response);
                        order = response.body();
                        if (order.getStatus() == 0){
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("status", "1");
                            Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(id.toString(), map);
                            caller.enqueue(new APICallback<OrderResponse>() {
                                @Override
                                public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                                    super.onSuccess(call, response);
                                    //ganti posisi pager
                                    finish();
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
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        super.onFailure(call, t);
                        Toast.makeText(getApplicationContext(),"Koneksi bermasalah, silahkan coba lagi",Toast.LENGTH_SHORT).show();
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
        //validasi waktu kerja art========================================================================================================
        abuildermessage("Anda yakin ingin menolak pesanan ini?","Konfirmasi");
        abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<Order> caller = APIManager.getRepository(OrderRepo.class).getorderById(id.toString());
                caller.enqueue(new APICallback<Order>() {
                    @Override
                    public void onSuccess(Call<Order> call, Response<Order> response) {
                        super.onSuccess(call, response);
                        order = response.body();
                        if (order.getStatus() == 0){
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("status", "4");
                            Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).patchorderById(id.toString(), map);
                            caller.enqueue(new APICallback<OrderResponse>() {
                                @Override
                                public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                                    super.onSuccess(call, response);
                                    finish();
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
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        super.onFailure(call, t);
                        Toast.makeText(getApplicationContext(),"Koneksi bermasalah, silahkan coba lagi",Toast.LENGTH_SHORT).show();
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
}
