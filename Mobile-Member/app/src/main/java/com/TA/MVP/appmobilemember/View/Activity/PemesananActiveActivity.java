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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerja;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentAsistenmini;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
    private List<OrderTask> orderTasks = new ArrayList<>();

    private EditText mulaitime, mulaidate, selesaitime, selesaidate, total, cttn;
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Order order = new Order();
    private Toolbar toolbar;
    private FragmentAsistenmini fragmentAsistenmini;

    private Button batalkan, kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_active);
        Intent intent = getIntent();
        order = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ORDER_EXTRA), Order.class);

        mulaitime = (EditText) findViewById(R.id.pmsa_et_mulaitime);
        mulaidate = (EditText) findViewById(R.id.pmsa_et_mulaidate);
        selesaitime = (EditText) findViewById(R.id.pmsa_et_selesaitime);
        selesaidate = (EditText) findViewById(R.id.pmsa_et_selesaidate);
        cttn = (EditText) findViewById(R.id.pmsa_et_catatan);
        total = (EditText) findViewById(R.id.pmsa_et_total);
        batalkan = (Button) findViewById(R.id.pmsa_btn_btlkn);
        kembali = (Button) findViewById(R.id.pmsa_btn_kembali);


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

        fragmentAsistenmini = new FragmentAsistenmini();
        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(order.getArt()));
        fragmentAsistenmini.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragmentAsistenmini).commit();

        //listkerja
        recyclerView = (RecyclerView) findViewById(R.id.pmsa_rec_listkerja);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaShow();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setList(orderTasks);

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
        batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuildermessage("Anda yakin ingin membatalkan pesanan ini?","Konfirmasi pembatalan");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        batalkanorder(order.getId());
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
    public void batalkanorder(Integer id){
        Call<Order> caller = APIManager.getRepository(OrderRepo.class).getorderById(id.toString());
        caller.enqueue(new APICallback<Order>() {
            @Override
            public void onSuccess(Call<Order> call, Response<Order> response) {
                super.onSuccess(call, response);
                order = response.body();
                if (order.getStatus() == 1){
                    deleteorder(order.getId());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void deleteorder(Integer id){
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).deleteorderById(order.getId().toString());
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                Toast.makeText(getApplicationContext(),"Order Dibatalkan", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Gagal membatalkan order, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotFound(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onNotFound(call, response);
                Toast.makeText(getApplicationContext(),"Order Dibatalkan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //kembalikan uang
    }
}
