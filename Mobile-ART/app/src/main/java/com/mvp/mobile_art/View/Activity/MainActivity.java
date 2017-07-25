package com.mvp.mobile_art.View.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.AdditionalInfo;
import com.mvp.mobile_art.Model.Basic.Job;
import com.mvp.mobile_art.Model.Basic.Language;
import com.mvp.mobile_art.Model.Basic.Place;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.Waktu_Kerja;
import com.mvp.mobile_art.Model.Basic.Wallet;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.AdditionalInfoRepo;
import com.mvp.mobile_art.Route.Repositories.JobRepo;
import com.mvp.mobile_art.Route.Repositories.LanguageRepo;
import com.mvp.mobile_art.Route.Repositories.PlaceRepo;
import com.mvp.mobile_art.Route.Repositories.WTRepo;
import com.mvp.mobile_art.Route.Repositories.WalletRepo;
import com.mvp.mobile_art.View.Fragment.FragmentJadwal;
import com.mvp.mobile_art.View.Fragment.FragmentHistory;
import com.mvp.mobile_art.View.Fragment.FragmentPekerjaan;
import com.mvp.mobile_art.View.Fragment.FragmentPesan;
import com.mvp.mobile_art.View.Fragment.FragmentProfile;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class MainActivity extends ParentActivity {
    public final static int REQUEST_LOGIN = 1;
    public final static int REQUEST_PESAN = 2;
    public final static int REQUEST_ORDER = 3;
    public final static int REQUEST_OFFER = 4;
    public static final int RESULT_SUCCESS = 1;
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    public StaticData staticData = new StaticData();
    private Context context;
    private boolean success;
    private Integer posisiF = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();


        if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(i, REQUEST_LOGIN);
        }
        else {
            initProgressDialog("Loading...");
            showDialog();
            getstaticData1();

        }
    }
    public void settampilan(){
        fragmentManager.beginTransaction().replace(R.id.content, new FragmentProfile()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_profile:
                        posisiF = 1;
                        fragment = new FragmentProfile();
                        break;
                    case R.id.menu_pekerjaan:
                        posisiF = 2;
                        fragment = new FragmentPekerjaan();
                        break;
                    case R.id.menu_jadwal:
                        posisiF = 3;
                        fragment = new FragmentJadwal();
                        break;
                    case R.id.menu_pesan:
                        posisiF = 4;
                        fragment = new FragmentPesan();
                        break;
                    case R.id.menu_lainnya:
                        posisiF = 5;
                        fragment = new FragmentHistory();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, fragment).commit();
                return true;
            }
        });
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
                Intent intent = new Intent(getApplicationContext(),EmergencyActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:SharedPref.save(SharedPref.ACCESS_TOKEN, "");
                abuildermessage("Anda akan melakukan Logout?", "Konfirmasi");
                abuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPref.save(ConstClass.USER, "");
                        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivityForResult(intent1, MainActivity.REQUEST_LOGIN);
                    }
                });
                abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                showalertdialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_SUCCESS) {
                initProgressDialog("Loading...");
                showDialog();
                getstaticData1();
            }
            else finish();
        }
        else if(requestCode == REQUEST_PESAN){
            refreshfragment();
        }
    }
    public void refreshfragment(){
        switch (posisiF){
            case 1:
                fragment = new FragmentProfile();
                break;
            case 2:
                fragment = new FragmentPekerjaan();
                break;
            case 3:
                fragment = new FragmentJadwal();
                break;
            case 4:
                fragment = new FragmentPesan();
                break;
            case 5:
                posisiF=5;
                fragment = new FragmentHistory();
                break;
        }
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment).commit();
    }

    public void getstaticData1() {
        success = false;
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
                success = false;
                Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                dismissDialog();
                abuildermessage("Reconnect?","No Connection");
                abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDialog();
                        getstaticData1();
                    }
                });
                abuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
            }
        });
    }
    public  void getstaticData5(){
        Call < List <AdditionalInfo>> caller5 = APIManager.getRepository(AdditionalInfoRepo.class).getadditional_infos();
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
            }
        });
    }
    public  void getstaticData6(){
        Call < List < Wallet >> caller6 = APIManager.getRepository(WalletRepo.class).getwallets();
        caller6.enqueue(new APICallback<List<Wallet>>() {
            @Override
            public void onSuccess(Call<List<Wallet>> call, Response<List<Wallet>> response) {
                super.onSuccess(call, response);
                staticData.setWallets(response.body());
                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                success = true;
                settampilan();
                dismissDialog();
            }

            @Override
            public void onFailure(Call<List<Wallet>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
//        if (!success)
//            getstaticData1();
        ((MasterCleanApplication) getApplication()).setGlobalStaticData(staticData);
    }
}
