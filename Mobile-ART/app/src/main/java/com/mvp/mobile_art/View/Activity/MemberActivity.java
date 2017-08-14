package com.mvp.mobile_art.View.Activity;

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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Array.ArrayAgama;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.RoundedTransformation;
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
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class MemberActivity extends ParentActivity {
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView alamat, notelp, agama, usia, nama;
    private StaticData staticData;
    private User member = new User();
    private ArrayAgama arrayAgama = new ArrayAgama();
    private int thisYear, artbornyear;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat yearformat = new SimpleDateFormat("yyyy");
    private Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Intent intent = getIntent();
        member = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.MEMBER_EXTRA), User.class);
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        kembali = (Button) findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initProgressDialog("Loading...");
        showDialog();
        getuser(member.getId());

    }

    private void initAllView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        alamat = (TextView) findViewById(R.id.alamat);
        nama = (TextView) findViewById(R.id.nama);
        notelp = (TextView) findViewById(R.id.notelp);
        agama = (TextView) findViewById(R.id.agama);
        usia = (TextView) findViewById(R.id.usia);
        imageView = (ImageView) findViewById(R.id.imageView);
        setAll();


    }

    private void setAll(){
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Info Member");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama.setText(member.getName());
        alamat.setText(member.getContact().getAddress());
        notelp.setText(member.getContact().getPhone());
        agama.setText(arrayAgama.getArrayList().get(member.getReligion()-1));

        thisYear = calendar.get(Calendar.YEAR);
        artbornyear = Integer.valueOf(yearformat.format(member.getBorn_date()));
        usia.setText(thisYear - artbornyear + " Thn");

        Picasso.with(getApplicationContext())
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+member.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .transform(new RoundedTransformation(10, 0))
                .into(imageView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_member, menu);
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
                    i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(member));
                    startActivity(i);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getuser(Integer id){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                member = response.body();
                initAllView();
                dismissDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Coba lagi?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getuser(member.getId());
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
}
