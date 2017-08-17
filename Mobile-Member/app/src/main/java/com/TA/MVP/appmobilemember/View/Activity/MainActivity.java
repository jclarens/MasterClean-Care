package com.TA.MVP.appmobilemember.View.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Basic.AdditionalInfo;
import com.TA.MVP.appmobilemember.Model.Basic.Emergencycall;
import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Basic.Language;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Place;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.Model.Responses.EmergencyCallResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.AdditionalInfoRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.EmergencycallRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.JobRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.LanguageRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.MyTaskRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.PlaceRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WTRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WalletRepo;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentCari;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentHome;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentLainnya;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPesan;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentStatus;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentUnauthorized;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends ParentActivity {
    private static final int PERMS_REQUEST_CODE = 123;
    public final static int REQUEST_LOGIN = 1;
    public final static int REQUEST_PESAN = 2;
    public final static int REQUEST_ORDER = 3;
    public final static int REQUEST_OFFER = 4;
    public final static int RESULT_SUCCESS = 1;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    public StaticData staticData = new StaticData();
    private User user;
    private Integer posisiF;
    private LinearLayout splashscreen;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;
    private FragmentHome fragmentHome = new FragmentHome();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.splashprogressbar);
        splashscreen = (LinearLayout) findViewById(R.id.splashscreen);
        frameLayout = (FrameLayout) findViewById(R.id.main_container);
        getstaticData1();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN).equals("")) {
            bottomNavigation.inflateMenu(R.menu.navigation2);
        }
        else
            bottomNavigation.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();

        posisiF =1;
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_beranda:
                        posisiF=1;
                        fragment = fragmentHome;
                        break;
                    case R.id.menu_cari:
                        posisiF=2;
                        fragment = new FragmentCari();
                        break;
                    case R.id.menu_status:
                        posisiF=3;
                        if (SharedPref.getValueString(ConstClass.USER) == "")
                            fragment = new FragmentUnauthorized();
                        else
                            fragment = new FragmentStatus();
                        break;
                    case R.id.menu_pesan:
                        posisiF=4;
                        if (SharedPref.getValueString(ConstClass.USER) == "")
                            fragment = new FragmentUnauthorized();
                        else
                            fragment = new FragmentPesan();
                        break;
                    case R.id.menu_lainnya:
                        posisiF=5;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
                if (SharedPref.getValueString(ConstClass.USER).equals("")){
                    Toast.makeText(getApplicationContext(), "Fitur membutuhkan authentikasi",Toast.LENGTH_SHORT).show();
                }
                else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            PERMS_REQUEST_CODE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        bottomNavigation.getMenu().clear();
        if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN).equals(""))
            bottomNavigation.inflateMenu(R.menu.navigation2);
        else
            bottomNavigation.inflateMenu(R.menu.navigation);
        return true;
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
                final FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                transaction2.replace(R.id.main_container, fragment).commit();
            }
        }
        hidekeyboard();
        refreshfragment();
        invalidateOptionsMenu();
    }
    public void refreshfragment(){
        switch (posisiF){
            case 1:
                fragment = fragmentHome;
                break;
            case 2:
                fragment = new FragmentCari();
                break;
            case 3:
                if (SharedPref.getValueString(ConstClass.USER).equals(""))
                    fragment = new FragmentUnauthorized();
                else
                    fragment = new FragmentStatus();
                break;
            case 4:
                if (SharedPref.getValueString(ConstClass.USER).equals(""))
                    fragment = new FragmentUnauthorized();
                else
                    fragment = new FragmentPesan();
                break;
            case 5:
                posisiF=5;
                fragment = new FragmentLainnya();
                break;
        }
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
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
//                Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show();
                splashout();
                if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) != "")
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
        ((MasterCleanApplication) getApplication()).setGlobalStaticData(staticData);
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
        }
    }
    public void splashout(){
        //tunggu beberapa dtk
        SystemClock.sleep(2000);
        progressBar.setVisibility(View.VISIBLE);

        splashscreen.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        bottomNavigation.setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction().replace(R.id.main_container, new FragmentHome()).commit();
    }
    public void getemergencystatus(){
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        Call<Emergencycall> caller = APIManager.getRepository(EmergencycallRepo.class).getemergencycall(user.getId(), 1);
        caller.enqueue(new APICallback<Emergencycall>() {
            @Override
            public void onSuccess(Call<Emergencycall> call, Response<Emergencycall> response) {
                super.onSuccess(call, response);
                try{
                    if (response.body().getStatus() == 1) {
                        Intent intent = new Intent(getApplicationContext(), EmergencyActivity.class);
                        intent.putExtra("item", GsonUtils.getJsonFromObject(response.body()));
                        startActivity(intent);
                    }
                }catch (NullPointerException e){

                }
            }

            @Override
            public void onNotFound(Call<Emergencycall> call, Response<Emergencycall> response) {
                super.onNotFound(call, response);
            }

            @Override
            public void onError(Call<Emergencycall> call, Response<Emergencycall> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<Emergencycall> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void hidekeyboard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}