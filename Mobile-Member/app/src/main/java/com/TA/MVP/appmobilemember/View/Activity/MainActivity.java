package com.TA.MVP.appmobilemember.View.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Basic.AdditionalInfo;
import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Basic.Language;
import com.TA.MVP.appmobilemember.Model.Basic.Place;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.AdditionalInfoRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.JobRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.LanguageRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.PlaceRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WTRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WalletRepo;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentCari;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentHome;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentLainnya;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPesan;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatus;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends ParentActivity {
    public final static int REQUEST_LOGIN = 1;

    public final static int RESULT_SUCCESS = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private Context context;
    private Menu menutoolbar;
    private boolean success;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    public StaticData staticData = new StaticData();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),SharedPref.getValueString(ConstClass.USER), Toast.LENGTH_SHORT).show();
//        alertDialog = new AlertDialog.Builder(getApplicationContext());
//        alertDialog.setMessage("Loading failed. reloading.");
//        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });

        getstaticData1();
//        while (!success) {
//            progressDialog = ProgressDialog.show(MainActivity.this," ","Loading Data.", true);
//            progressDialog.dismiss();
//            if (!success){
//                alertDialog.show();
//            }
//        }

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();

        context = getApplicationContext();

        fragmentManager.beginTransaction().replace(R.id.main_container, new FragmentHome()).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_beranda:
                        fragment = new FragmentHome();
                        break;
                    case R.id.menu_cari:
                        fragment = new FragmentCari();
                        break;
                    case R.id.menu_status:
                        fragment = new FragmentStatus();
                        break;
                    case R.id.menu_pesan:
                        fragment = new FragmentPesan();
                        break;
                    case R.id.menu_lainnya:
                        fragment = new FragmentLainnya();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menutoolbar = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
                doChangeActivity(context,EmergencyActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    public void doStartActivityForResult(Intent intent){
        startActivityForResult(intent, MainActivity.REQUEST_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN){
            if (resultCode == RESULT_SUCCESS){
                user = GsonUtils.getObjectFromJson(data.getStringExtra(ConstClass.USER), User.class);
                SharedPref.save(ConstClass.USER, data.getStringExtra(ConstClass.USER));
                Toast.makeText(context,SharedPref.getValueString(ConstClass.USER), Toast.LENGTH_SHORT).show();
            }
        }
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
//                getstaticData1();
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
                Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show();
                success = true;
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
