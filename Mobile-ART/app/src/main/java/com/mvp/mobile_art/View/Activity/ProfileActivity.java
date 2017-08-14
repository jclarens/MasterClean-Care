package com.mvp.mobile_art.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterReview;
import com.mvp.mobile_art.Model.Array.ArrayAgama;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 09/06/2017.
 */

public class ProfileActivity extends ParentActivity {
    public final static int REQUEST_EDIT = 1;
    public final static int REQUEST_EDITPASS = 2;
    public final static int REQUEST_EDITFOTO = 3;
    public final static int RESULT_SUCCESS = 1;
    public final static int RESULT_CANCEL = 2;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterReview rec_Adapter;
    private LinearLayout layoutreview;
    private Toolbar toolbar;
    private ImageView image;
    private RatingBar ratingBar;
    private TextView nama, telp, usia, agama, suku, kota, profesi, bahasa;
    private EditText keterangan;
    private Switch aSwitch;
    private User user = new User();
    private ArrayAgama arrayAgama = new ArrayAgama();
    private StaticData staticData;
    private int thisYear, bornyear;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat yearformat = new SimpleDateFormat("yyyy");
    private CompoundButton.OnCheckedChangeListener changelistener;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        Log.d("temporary","onCreate:"+ SharedPref.getValueString(ConstClass.USER));
        staticData = ((MasterCleanApplication)getApplication()).globalStaticData;

        layoutreview = (LinearLayout) findViewById(R.id.layout_review);
        image = (ImageView) findViewById(R.id.prof_img);
        ratingBar = (RatingBar) findViewById(R.id.prof_rate);
        keterangan = (EditText) findViewById(R.id.keterangan);
        nama = (TextView) findViewById(R.id.nama);
        telp = (TextView) findViewById(R.id.telp);
        usia = (TextView) findViewById(R.id.usia);
        agama = (TextView) findViewById(R.id.agama);
        suku = (TextView) findViewById(R.id.suku);
        kota = (TextView) findViewById(R.id.kota);
        profesi = (TextView) findViewById(R.id.prof);
        bahasa = (TextView) findViewById(R.id.bahasa);
        aSwitch  = (Switch) findViewById(R.id.prof_switch);


        recyclerView = (RecyclerView) findViewById(R.id.recycleview_review);
        rec_LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterReview(this);
        recyclerView.setAdapter(rec_Adapter);

        getallinfo(user.getId());

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_profile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.prof_menu_edit:
//                Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                ProfileActivity.this.startActivityForResult(intent, REQUEST_EDIT);
                break;
            case R.id.prof_menu_editks:
//                Toast.makeText(getApplicationContext(),"Sedang dalam pengembangan", Toast.LENGTH_SHORT).show();
                doStartActivity(ProfileActivity.this, EditPassActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void settampilan(){
        ratingBar.setRating(user.getRate());
        nama.setText(user.getName());
        telp.setText(user.getContact().getPhone());
        //usia
        thisYear = calendar.get(Calendar.YEAR);
        bornyear = Integer.valueOf(yearformat.format(user.getBorn_date()));
        usia.setText(thisYear - bornyear + " Thn");

        agama.setText(arrayAgama.getArrayList().get(user.getReligion()-1));
        suku.setText(user.getRace());
        kota.setText(staticData.getPlaces().get(user.getContact().getCity()-1).getName());
        String temp = "";
        for(int n=0;n<user.getUser_job().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getJobs().get(user.getUser_job().get(n).getJob_id()-1);
        }
        profesi.setText(temp);
        temp = "";
        for(int n=0;n<user.getUser_language().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getLanguages().get(user.getUser_language().get(n).getLanguage_id()-1).getLanguage();
        }
        bahasa.setText(temp);

        switch (user.getStatus()){
            case 0:
                aSwitch.setText("Tidak Aktif");
                aSwitch.setChecked(false);
                break;
            case 1:
                aSwitch.setText("Aktif");
                aSwitch.setChecked(true);
                break;
        }

        changelistener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                aSwitch.setOnCheckedChangeListener(null);
                if (aSwitch.isChecked()){
                    abuildermessage("Anda akan mengubah status anda menjadi aktif.","Konfirmasi");
                    abuilder.setPositiveButton("Aktifkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gantistatus(1);
                        }
                    });
                    abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aSwitch.setChecked(false);
                            aSwitch.setOnCheckedChangeListener(changelistener);
                        }
                    });
                    showalertdialog();
                }else {
                    abuildermessage("Anda tidak akan dapat menerima transaksi baru saat tidak aktif.","Konfirmasi");
                    abuilder.setPositiveButton("Nonaktifkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gantistatus(0);
                        }
                    });
                    abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aSwitch.setChecked(true);
                            aSwitch.setOnCheckedChangeListener(changelistener);
                        }
                    });
                    showalertdialog();
                }
            }
        };
        aSwitch.setOnCheckedChangeListener(changelistener);

//        Log.d("Image path ", Settings.getRetrofitAPIUrl()+"image/"+user.getAvatar());
        Picasso.with(getApplicationContext())
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+user.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .into(image);
    }
    public void gantistatus(final Integer integer){
        initProgressDialog("Mengubah Status");
        showDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", String.valueOf(integer));
        user.setStatus(integer);
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(), map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
                if (integer == 1)
                    Toast.makeText(getApplicationContext(), "Anda Sudah Aktif", Toast.LENGTH_SHORT).show();
                else if (integer == 0)
                    Toast.makeText(getApplicationContext(), "Anda Sudah Tidak Aktif", Toast.LENGTH_SHORT).show();
                dismissDialog();
                aSwitch.setOnCheckedChangeListener(changelistener);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                if (integer == 1)
                    aSwitch.setChecked(false);
                else if (integer == 0)
                    aSwitch.setChecked(true);
                aSwitch.setOnCheckedChangeListener(changelistener);
            }

            @Override
            public void onError(Call<UserResponse> call, Response<UserResponse> response) {
                super.onError(call, response);
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                if (integer == 1)
                    aSwitch.setChecked(false);
                else if (integer == 0)
                    aSwitch.setChecked(true);
                dismissDialog();
                aSwitch.setOnCheckedChangeListener(changelistener);
            }

            @Override
            public void onUnauthorized(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnauthorized(call, response);
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan autentikasi", Toast.LENGTH_SHORT).show();
                if (integer == 1)
                    aSwitch.setChecked(false);
                else if (integer == 0)
                    aSwitch.setChecked(true);
                dismissDialog();
                aSwitch.setOnCheckedChangeListener(changelistener);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_EDIT:
                if (resultCode == RESULT_SUCCESS) {
                    user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
                    settampilan();
                    dismissDialog();
                }
                break;
        }
    }
    public void getallinfo(Integer id){
        initProgressDialog("Memuat data...");
        showDialog();
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                user = response.body();
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
                settampilan();
                dismissDialog();
                getreviews();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void getreviews(){
        Call<List<Order>> caller = APIManager.getRepository(OrderRepo.class).getorderreviewByArt(user.getId());
        caller.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
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
