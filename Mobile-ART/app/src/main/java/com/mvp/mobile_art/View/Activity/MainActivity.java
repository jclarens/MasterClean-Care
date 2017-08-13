package com.mvp.mobile_art.View.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.AdditionalInfo;
import com.mvp.mobile_art.Model.Basic.Emergencycall;
import com.mvp.mobile_art.Model.Basic.Job;
import com.mvp.mobile_art.Model.Basic.Language;
import com.mvp.mobile_art.Model.Basic.MyTask;
import com.mvp.mobile_art.Model.Basic.Place;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.Waktu_Kerja;
import com.mvp.mobile_art.Model.Basic.Wallet;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.AdditionalInfoRepo;
import com.mvp.mobile_art.Route.Repositories.EmergencycallRepo;
import com.mvp.mobile_art.Route.Repositories.JobRepo;
import com.mvp.mobile_art.Route.Repositories.LanguageRepo;
import com.mvp.mobile_art.Route.Repositories.MyTaskRepo;
import com.mvp.mobile_art.Route.Repositories.PlaceRepo;
import com.mvp.mobile_art.Route.Repositories.WTRepo;
import com.mvp.mobile_art.Route.Repositories.WalletRepo;
import com.mvp.mobile_art.View.Fragment.FragmentJadwal;
import com.mvp.mobile_art.View.Fragment.FragmentLainnya;
import com.mvp.mobile_art.View.Fragment.FragmentPekerjaan;
import com.mvp.mobile_art.View.Fragment.FragmentPenawaran;
import com.mvp.mobile_art.View.Fragment.FragmentPesan;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class MainActivity extends ParentActivity {
    private static final int PERMS_REQUEST_CODE = 123;
    public final static int REQUEST_LOGIN = 1;
    public final static int REQUEST_PESAN = 2;
    public final static int REQUEST_ORDER = 3;
    public final static int REQUEST_OFFER = 4;
    public static final int RESULT_SUCCESS = 1;
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private User user;
    private LinearLayout splashscreen;
    public StaticData staticData = new StaticData();
    private Integer posisiF = 1;
    private Emergencycall EC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        splashscreen = (LinearLayout) findViewById(R.id.splashscreen);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();


        if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(i, REQUEST_LOGIN);
        }
        else {
            getstaticData1();
        }
    }
    public void settampilan(){
        fragmentManager.beginTransaction().replace(R.id.content, new FragmentJadwal()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_penawaran:
                        posisiF = 1;
                        fragment = new FragmentJadwal();
                        break;
                    case R.id.menu_pekerjaan:
                        posisiF = 2;
                        fragment = new FragmentPekerjaan();
                        break;
                    case R.id.menu_jadwal:
                        posisiF = 3;
                        fragment = new FragmentPenawaran();
                        break;
                    case R.id.menu_pesan:
                        posisiF = 4;
                        fragment = new FragmentPesan();
                        break;
                    case R.id.menu_lainnya:
                        posisiF = 5;
                        fragment = new FragmentLainnya();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, fragment).commit();
                return true;
            }
        });
    }
    public void splashout(){
        //tunggu beberapa dtk
        SystemClock.sleep(2000);

        splashscreen.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        bottomNavigation.setVisibility(View.VISIBLE);
    }
    public void splashin(){
        splashscreen.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.GONE);
        bottomNavigation.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMS_REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_SUCCESS) {
                splashin();
                getstaticData1();
            }
            else finish();
        }else refreshfragment();
    }
    public void refreshfragment(){
        switch (posisiF){
            case 1:
                fragment = new FragmentJadwal();
                break;
            case 2:
                fragment = new FragmentPekerjaan();
                break;
            case 3:
                fragment = new FragmentPenawaran();
                break;
            case 4:
                fragment = new FragmentPesan();
                break;
            case 5:
                posisiF=5;
                fragment = new FragmentLainnya();
                break;
        }
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment).commit();
    }

    public void getstaticData1() {
        Call<List<Place>> caller1 = APIManager.getRepository(PlaceRepo.class).getplaces();
        caller1.enqueue(new APICallback<List<Place>>() {
            @Override
            public void onSuccess(Call<List<Place>> call, Response<List<Place>> response) {
                super.onSuccess(call, response);
                staticData.setPlaces(response.body());
                getstaticData2();
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData1();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }
    public  void getstaticData2(){
        Call<List<Language>> caller2 = APIManager.getRepository(LanguageRepo.class).getlanguages();
        caller2.enqueue(new APICallback<List<Language>>() {
            @Override
            public void onSuccess(Call<List<Language>> call, Response<List<Language>> response) {
                super.onSuccess(call, response);
                staticData.setLanguages(response.body());
                getstaticData3();
            }

            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData2();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }
    public  void getstaticData3(){
        Call<List<Job>> caller3 = APIManager.getRepository(JobRepo.class).getJobs();
        caller3.enqueue(new APICallback<List<Job>>() {
            @Override
            public void onSuccess(Call<List<Job>> call, Response<List<Job>> response) {
                super.onSuccess(call, response);
                staticData.setJobs(response.body());
                getstaticData4();
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData3();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }
    public  void getstaticData4(){
        Call < List < Waktu_Kerja >> caller4 = APIManager.getRepository(WTRepo.class).getworktimes();
        caller4.enqueue(new APICallback<List<Waktu_Kerja>>() {
            @Override
            public void onSuccess(Call<List<Waktu_Kerja>> call, Response<List<Waktu_Kerja>> response) {
                super.onSuccess(call, response);
                staticData.setWaktu_kerjas(response.body());
                getstaticData5();
            }

            @Override
            public void onFailure(Call<List<Waktu_Kerja>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData4();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }
    public  void getstaticData5(){
        Call < List < AdditionalInfo >> caller5 = APIManager.getRepository(AdditionalInfoRepo.class).getadditional_infos();
        caller5.enqueue(new APICallback<List<AdditionalInfo>>() {
            @Override
            public void onSuccess(Call<List<AdditionalInfo>> call, Response<List<AdditionalInfo>> response) {
                super.onSuccess(call, response);
                staticData.setAdditionalInfos(response.body());
                getstaticData6();
            }

            @Override
            public void onFailure(Call<List<AdditionalInfo>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData5();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }
    public  void getstaticData6(){
        Call<List<MyTask>> caller = APIManager.getRepository(MyTaskRepo.class).gettasks();
        caller.enqueue(new APICallback<List<MyTask>>() {
            @Override
            public void onSuccess(Call<List<MyTask>> call, Response<List<MyTask>> response) {
                super.onSuccess(call, response);
                staticData.setMyTasks(response.body());
                getstaticData7();
            }

            @Override
            public void onFailure(Call<List<MyTask>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData6();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }
    public  void getstaticData7(){
        Call < List < Wallet >> caller6 = APIManager.getRepository(WalletRepo.class).getwallets();
        caller6.enqueue(new APICallback<List<Wallet>>() {
            @Override
            public void onSuccess(Call<List<Wallet>> call, Response<List<Wallet>> response) {
                super.onSuccess(call, response);
                staticData.setWallets(response.body());
                ((MasterCleanApplication) getApplication()).setGlobalStaticData(staticData);
                splashout();
                settampilan();
                getemergencystatus();
            }

            @Override
            public void onFailure(Call<List<Wallet>> call, Throwable t) {
                super.onFailure(call, t);
                abuildermessage("Koneksi bermasalah. Muat ulang?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getstaticData7();
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(), EmergencyActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),"Fitur tidak dapat dijalankan tanpa izin pengguna", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SharedPref.save(ConstClass.EMERGENCY_EXTRA, "on");
                    openec();

                } else {
                    finish();
                }
                return;
            }
        }
    }
    public void getemergencystatus(){
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        Call<Emergencycall> caller = APIManager.getRepository(EmergencycallRepo.class).getemergencycall(user.getId(), 1);
        caller.enqueue(new APICallback<Emergencycall>() {
            @Override
            public void onSuccess(Call<Emergencycall> call, Response<Emergencycall> response) {
                super.onSuccess(call, response);
                dismissDialog();
                EC = response.body();
                try{
                    if (EC.getStatus() == 1){
                        getpermission();
                    }
                }catch (NullPointerException e){

                }
            }

            @Override
            public void onNotFound(Call<Emergencycall> call, Response<Emergencycall> response) {
                super.onNotFound(call, response);
                dismissDialog();
            }

            @Override
            public void onError(Call<Emergencycall> call, Response<Emergencycall> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<Emergencycall> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void getpermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                111);
    }
    public void openec(){
        Intent intent = new Intent(getApplicationContext(), EmergencyActivity.class);
        intent.putExtra("item", GsonUtils.getJsonFromObject(EC));
        startActivity(intent);
    }
}
