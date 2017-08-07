package com.TA.MVP.appmobilemember.View.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterAsisten;
import com.TA.MVP.appmobilemember.Model.Basic.Language;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.FilterActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapterAsisten rec_Adapter;
    private Button btnfilter;
    private FloatingActionButton filter;
    private List<User> arts = new ArrayList<>();
    private List<Language> languages = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private int thisyear, tempyear;
    private SwipeRefreshLayout swipeRefreshLayout;
    Integer wkid;
    Integer profesi;
    Integer usiamin;
    Integer usiamax;
    Integer gaji;
    View _view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_cari_list, container, false);
        thisyear = calendar.get(Calendar.YEAR);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
//        btnfilter = (Button) _view.findViewById(R.id.carilist_btn_filter);
        filter = (FloatingActionButton) _view.findViewById(R.id.carilist_btn_filter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),FilterActivity.class);
                startActivityForResult(i, 111);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPref.save("searching", "");
                getarts();
            }
        });

        //recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);
        rec_Adapter = new RecyclerAdapterAsisten(getContext());
        recyclerView.setAdapter(rec_Adapter);

        if (SharedPref.getValueString("searching").equals("")) {
            getarts();
        }

        Log.d("why","ooooooooooooooooooooooooooooooooo");
        return _view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (resultCode == Activity.RESULT_OK){
                Map<String,String> map = new HashMap<>();
                map.put("role_id","3");
                map.put("name", data.getStringExtra("nama"));
                map.put("race", data.getStringExtra("suku"));
                if (data.getStringExtra("agama") != null){
                    map.put("religion", data.getStringExtra("agama"));
                }
                if (data.getStringExtra("WT") != null){
                    wkid = Integer.valueOf(data.getStringExtra("WT"));
                }
                else wkid = null;
                if (data.getStringExtra("profesi") != null){
                    profesi = Integer.valueOf(data.getStringExtra("profesi"));
                }
                else profesi = null;
                languages = (List<Language>) GsonUtils.getObjectFromJson(data.getStringExtra("listbahasa"), new TypeToken<List<Language>>(){}.getType());

                usiamin = Integer.valueOf(data.getStringExtra("usiamin"));
                usiamax = Integer.valueOf(data.getStringExtra("usiamax"));
                gaji = data.getIntExtra("gaji", 0);

                //search users
                Call<List<User>> caller = APIManager.getRepository(UserRepo.class).searchuser(map);
                caller.enqueue(new APICallback<List<User>>() {
                    @Override
                    public void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                        super.onSuccess(call, response);
                        Log.d("List[][]",GsonUtils.getJsonFromObject(response.body()));
                        arts = secondfilter(response.body(), wkid, profesi, usiamin, usiamax, languages, gaji);
                        updateadapter(arts);
                        SharedPref.save("searching", "");
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }
            if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
    public List<User> secondfilter(List<User> users , @Nullable Integer wt_id, @Nullable Integer prof_id, Integer usiamin, Integer usimax, List<Language> languagess, @Nullable Integer gajii){
        List<User> result = users;

        //filter work time
        if (wt_id != null){
            users = result;
            result = new ArrayList<>();
            for (int n=0; n<users.size();n++){
                for (int m=0; m<users.get(n).getUser_work_time().size();m++){
                    if (users.get(n).getUser_work_time().get(m).getWork_time_id() == wt_id)
                        result.add(users.get(n));
                }
            }
        }

        //filter gaji
        if (gajii != 0 && wt_id !=null){
            users = result;
            result = new ArrayList<>();
            for (int n=0; n<users.size();n++){
                for (int m=0; m<users.get(n).getUser_work_time().size();m++){
                    if (users.get(n).getUser_work_time().get(m).getWork_time_id() == wt_id  && users.get(n).getUser_work_time().get(m).getCost() <= gajii)
                        result.add(users.get(n));
                }
            }
        }

        //filter profesi
        if (prof_id != null){
            users = result;
            result = new ArrayList<>();
            for (int n=0; n<users.size();n++) {
                for (int m = 0; m < users.get(n).getUser_job().size(); m++) {
                    if (users.get(n).getUser_job().get(m).getJob_id() == prof_id)
                        result.add(users.get(n));
                }
            }
        }


        //filter usia
        users = result;
        result = new ArrayList<>();
        for (int n=0; n<users.size();n++) {
            calendar.setTime(users.get(n).getBorn_date());
            tempyear = calendar.get(Calendar.YEAR);
            if (thisyear - tempyear > usiamin && thisyear - tempyear <usimax){
                result.add(users.get(n));
            }
        }

        //filter bahasa
        boolean ada=false;
        for (int n = 0; n<languagess.size(); n++){
            users = result;
            result = new ArrayList<>();
            for (int m = 0; m<users.size();m++){
                for (int o=0;o<users.get(m).getUser_language().size() ;o++){
                    if (languagess.get(n).getId() == users.get(m).getUser_language().get(o).getLanguage_id())
                        result.add(users.get(m));
                }
            }
        }

        //filter status
        users = result;
        result = new ArrayList<>();
//        String temp="";
        for (int n = 0; n<users.size(); n++){
            if (users.get(n).getStatus() == 1){
                result.add(users.get(n));
//                temp = temp + users.get(n).getStatus();
            }
        }
        return result;
    }
    public void getarts(){
        Call<List<User>> caller = APIManager.getRepository(UserRepo.class).getallart();
        caller.enqueue(new APICallback<List<User>>() {
            @Override
            public void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                super.onSuccess(call, response);
                Log.d("List[][]",GsonUtils.getJsonFromObject(response.body()));
                rec_Adapter.setART(removeinactive(response.body()));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<User> removeinactive(List<User> users){
        List<User> result = new ArrayList<>();
        for (int n = 0; n<users.size(); n++){
            if (users.get(n).getStatus() == 1){
                result.add(users.get(n));
            }
        }
        return result;
    }
    public void updateadapter(List<User> arts){
        rec_Adapter.setART(arts);
    }
}
