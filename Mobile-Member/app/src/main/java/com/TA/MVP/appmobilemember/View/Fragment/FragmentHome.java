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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterAsisten;
import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Model.Basic.Language;
import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.GetArtsResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.FilterActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

import java.text.NumberFormat;
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
    private LinearLayout layoutnolist, layouttags;
    private RelativeLayout layoutshow;
    private TextView tags, clear;
    private String stringtags;
    private Intent filterresult;
    private StaticData staticData;
    private ArrayAgama arrayAgama = new ArrayAgama();
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private Integer currentpage = 1;
    private Integer lastpage = 1;
    Integer wkid;
    Integer profesi;
    Integer usiamin;
    Integer usiamax;
    Integer gaji;
    Integer kota;
    View _view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_cari_list, container, false);
        thisyear = calendar.get(Calendar.YEAR);
        staticData = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData();

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
        filter = (FloatingActionButton) _view.findViewById(R.id.carilist_btn_filter);
        layoutnolist = (LinearLayout) _view.findViewById(R.id.layout_nolist);
        layoutshow = (RelativeLayout) _view.findViewById(R.id.layout_showlist);
        layouttags = (LinearLayout) _view.findViewById(R.id.layout_tagfilter);
        tags = (TextView) _view.findViewById(R.id.filtertags);
        clear = (TextView) _view.findViewById(R.id.clear);

        tags.setHorizontallyScrolling(false);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layouttags.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(true);
                SharedPref.save("searching", "");
                getarts();
            }
        });

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
                if (filterresult == null){
                    SharedPref.save("searching", "");
                    getarts();
                }
                else {
                    if (SharedPref.getValueString("searching").equals("yes")){
                        searchuser();
                    } else {
                        SharedPref.save("searching", "");
                        getarts();
                    }
                }
            }
        });

        //recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);
        rec_Adapter = new RecyclerAdapterAsisten(getContext(), this);
        recyclerView.setAdapter(rec_Adapter);

        if (filterresult == null)
            SharedPref.save("searching", "");
        if (SharedPref.getValueString("searching").equals("")) {
            getarts();
        }
//        getarts();

        Log.d("why","ooooooooooooooooooooooooooooooooo");
        return _view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (resultCode == Activity.RESULT_OK){
                filterresult = data;
                //search users
                searchuser();
            }
            if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
    public void searchuser(){
        stringtags = "";
        Map<String,String> map = new HashMap<>();
        if (!filterresult.getStringExtra("nama").equals("")) {
            map.put("name", filterresult.getStringExtra("nama"));
            addtag("Nama:" + filterresult.getStringExtra("nama"));
        }
        if (!filterresult.getStringExtra("suku").equals("")) {
            map.put("race", filterresult.getStringExtra("suku"));
            addtag("Suku:" + filterresult.getStringExtra("suku"));
        }
        if (filterresult.getStringExtra("kota") != null){
            map.put("city", filterresult.getStringExtra("kota"));
            addtag("Kota:" + staticData.getPlaces().get(Integer.valueOf(filterresult.getStringExtra("kota"))-1).getName());
        }
        if (filterresult.getStringExtra("agama") != null){
            map.put("religion", filterresult.getStringExtra("agama"));
            addtag("Agama:" + arrayAgama.getArrayList().get(Integer.valueOf(filterresult.getStringExtra("agama"))-1));
        }
        if (filterresult.getStringExtra("WT") != null){
            map.put("work_time", filterresult.getStringExtra("WT"));
            addtag("Waktu Kerja" + staticData.getWaktu_kerjas().get(Integer.valueOf(filterresult.getStringExtra("WT"))-1).getWork_time());
        }else wkid = null;
        if (filterresult.getStringExtra("profesi") != null){
            map.put("job", filterresult.getStringExtra("profesi"));
            addtag("Profesi:" + staticData.getJobs().get(Integer.valueOf(filterresult.getStringExtra("profesi"))-1).getJob());
        }
        else profesi = null;
        languages = (List<Language>) GsonUtils.getObjectFromJson(filterresult.getStringExtra("listbahasa"), new TypeToken<List<Language>>(){}.getType());
        if (languages.size() > 0){
            String temp = "";
            String temp2 = "";
            for (int n=0 ; n<languages.size();n++){
                if (n != 0) {
                    temp = temp + ",";
                    temp2 = temp2 + ",";
                }
                temp = temp + languages.get(n).getId();
                temp2 = temp2 + languages.get(n).getLanguage();
            }
            map.put("language", temp);
            addtag("Bahasa:(" + temp2 +")");
        }
        usiamin = Integer.valueOf(filterresult.getStringExtra("usiamin"));
        map.put("minAge",filterresult.getStringExtra("usiamin"));
        usiamax = Integer.valueOf(filterresult.getStringExtra("usiamax"));
        map.put("maxAge",filterresult.getStringExtra("usiamax"));
        addtag("Usia:" + usiamin + "-" + usiamax);
        gaji = filterresult.getIntExtra("gaji", 0);
        if (gaji > 0){
            addtag("Gaji:" + setRP(gaji));
            map.put("maxCost",String.valueOf(gaji));
        }
        swipeRefreshLayout.setRefreshing(true);
//        map.put("page", String.valueOf(1));
        Call<GetArtsResponse> caller = APIManager.getRepository(UserRepo.class).searcharts(map);
        caller.enqueue(new APICallback<GetArtsResponse>() {
            @Override
            public void onSuccess(Call<GetArtsResponse> call, Response<GetArtsResponse> response) {
                super.onSuccess(call, response);
                currentpage = response.body().getCurrent_page();
                lastpage = response.body().getLast_page();
                if (response.body().getData().size() > 0){
                    rec_Adapter.setART(response.body().getData());
                    showlist();
                } else nolist();
                swipeRefreshLayout.setRefreshing(false);

                //tags
                tags.setText(stringtags);
                if(!stringtags.equals(""))
                    layouttags.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<GetArtsResponse> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void getarts(){
        Map<String, String> map = new HashMap<>();
        map.put("page","1");
        Call<GetArtsResponse> caller = APIManager.getRepository(UserRepo.class).getarts(map);
        caller.enqueue(new APICallback<GetArtsResponse>() {
            @Override
            public void onSuccess(Call<GetArtsResponse> call, Response<GetArtsResponse> response) {
                super.onSuccess(call, response);
//                arts = removeinactive(response.body().getData());
                currentpage = response.body().getCurrent_page();
                lastpage = response.body().getLast_page();
                rec_Adapter.setART(response.body().getData());
                swipeRefreshLayout.setRefreshing(false);
                if (response.body().getData().size() > 0){
                    showlist();
                } else nolist();
            }

            @Override
            public void onFailure(Call<GetArtsResponse> call, Throwable t) {
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

    public void nolist(){
        layoutnolist.setVisibility(View.VISIBLE);
        layoutshow.setVisibility(View.GONE);
    }
    public void showlist(){
        layoutnolist.setVisibility(View.GONE);
        layoutshow.setVisibility(View.VISIBLE);
    }

    public void addtag(String string){
        if (!stringtags.equals("")){
            stringtags = stringtags + ", " + string;
        } else stringtags = string;
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
    public void loadmore(){
        if (currentpage < lastpage){
            if (SharedPref.getValueString("searching").equals("")) {
                Map<String, String> map = new HashMap<>();
                map.put("page", String.valueOf(currentpage+1));
                Call<GetArtsResponse> caller = APIManager.getRepository(UserRepo.class).getarts(map);
                caller.enqueue(new APICallback<GetArtsResponse>() {
                    @Override
                    public void onSuccess(Call<GetArtsResponse> call, Response<GetArtsResponse> response) {
                        super.onSuccess(call, response);
                        rec_Adapter.addmore(response.body().getData());
                        currentpage = response.body().getCurrent_page();
                        lastpage = response.body().getLast_page();
                    }

                    @Override
                    public void onFailure(Call<GetArtsResponse> call, Throwable t) {
                        super.onFailure(call, t);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                stringtags = "";
                Map<String,String> map = new HashMap<>();
                if (!filterresult.getStringExtra("nama").equals("")) {
                    map.put("name", filterresult.getStringExtra("nama"));
                    addtag("Nama:" + filterresult.getStringExtra("nama"));
                }
                if (!filterresult.getStringExtra("suku").equals("")) {
                    map.put("race", filterresult.getStringExtra("suku"));
                    addtag("Suku:" + filterresult.getStringExtra("suku"));
                }
                if (filterresult.getStringExtra("kota") != null){
                    map.put("city", filterresult.getStringExtra("kota"));
                    addtag(staticData.getPlaces().get(kota-1).getName());
                }
                if (filterresult.getStringExtra("agama") != null){
                    map.put("religion", filterresult.getStringExtra("agama"));
                    addtag(arrayAgama.getArrayList().get(Integer.valueOf(filterresult.getStringExtra("agama"))-1));
                }
                if (filterresult.getStringExtra("WT") != null){
                    map.put("work_time", filterresult.getStringExtra("WT"));
                    addtag(staticData.getWaktu_kerjas().get(wkid-1).getWork_time());
                }else wkid = null;
                if (filterresult.getStringExtra("profesi") != null){
                    map.put("job", filterresult.getStringExtra("profesi"));
                    addtag(staticData.getJobs().get(profesi-1).getJob());
                }else profesi = null;
                languages = (List<Language>) GsonUtils.getObjectFromJson(filterresult.getStringExtra("listbahasa"), new TypeToken<List<Language>>(){}.getType());
                if (languages.size() > 0){
                    String temp = "";
                    String temp2 = "";
                    for (int n=0 ; n<languages.size();n++){
                        if (n != 0) {
                            temp = temp + ",";
                            temp2 = temp2 + ",";
                        }
                        temp = temp + languages.get(n).getId();
                        temp2 = temp2 + languages.get(n).getLanguage();
                    }
                    map.put("language", temp);
                    addtag("Bahasa:(" + temp2 +")");
                }
                usiamin = Integer.valueOf(filterresult.getStringExtra("usiamin"));
                map.put("minAge",filterresult.getStringExtra("usiamin"));
                usiamax = Integer.valueOf(filterresult.getStringExtra("usiamax"));
                map.put("maxAge",filterresult.getStringExtra("usiamax"));
                addtag("Usia:" + usiamin + "-" + usiamax);
                gaji = filterresult.getIntExtra("gaji", 0);
                if (gaji > 0){
                    addtag("Gaji:" + setRP(gaji));
                    map.put("maxCost",String.valueOf(gaji));
                }
                swipeRefreshLayout.setRefreshing(true);
//                map.put("page", String.valueOf(currentpage+1));
                Call<GetArtsResponse> caller = APIManager.getRepository(UserRepo.class).searcharts(map);
                caller.enqueue(new APICallback<GetArtsResponse>() {
                    @Override
                    public void onSuccess(Call<GetArtsResponse> call, Response<GetArtsResponse> response) {
                        super.onSuccess(call, response);
                        rec_Adapter.addmore(response.body().getData());
                        currentpage = response.body().getCurrent_page();
                        lastpage = response.body().getLast_page();
                        swipeRefreshLayout.setRefreshing(false);

                        //tags
                        tags.setText(stringtags);
                        if(!stringtags.equals(""))
                            layouttags.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(Call<GetArtsResponse> call, Throwable t) {
                        super.onFailure(call, t);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }
    }

    //    public List<User> secondfilter(List<User> users , @Nullable Integer wt_id, @Nullable Integer prof_id, Integer usiamin, Integer usimax, List<Language> languagess, @Nullable Integer gajii, @Nullable Integer kota){
    public List<User> secondfilter(List<User> users , Integer usiamin, Integer usimax, List<Language> languagess, @Nullable Integer gajii){
        List<User> result = users;

        //filter status
        users = result;
        result = new ArrayList<>();
        for (int n = 0; n<users.size(); n++){
            if (users.get(n).getStatus() == 1){
                result.add(users.get(n));
            }
        }

        //filter work time
//        if (wt_id != null){
//            users = result;
//            result = new ArrayList<>();
//            for (int n=0; n<users.size();n++){
//                for (int m=0; m<users.get(n).getUser_work_time().size();m++){
//                    if (users.get(n).getUser_work_time().get(m).getWork_time_id() == wt_id)
//                        result.add(users.get(n));
//                }
//            }
//        }

        //filter profesi
//        if (prof_id != null){
//            users = result;
//            result = new ArrayList<>();
//            for (int n=0; n<users.size();n++) {
//                for (int m = 0; m < users.get(n).getUser_job().size(); m++) {
//                    if (users.get(n).getUser_job().get(m).getJob_id() == prof_id)
//                        result.add(users.get(n));
//                }
//            }
//        }

        //filter usia
        users = result;
        result = new ArrayList<>();
        for (int n=0; n<users.size();n++) {
            calendar.setTime(users.get(n).getBorn_date());
            tempyear = calendar.get(Calendar.YEAR);
            if (thisyear - tempyear >= usiamin && thisyear - tempyear <= usimax){
                result.add(users.get(n));
            }
        }

        //filter bahasa
        for (int n = 0; n<languagess.size(); n++){
            users = result;
            result = new ArrayList<>();
            for (int m = 0; m<users.size();m++){
                for (int o=0;o<users.get(m).getUser_language().size() ;o++){
//                    Log.d("Bahasa =============",languagess.get(n).getId()+" - "+users.get(m).getId() + " - " + users.get(m).getUser_language().get(o).getLanguage_id());
                    if (languagess.get(n).getId() == users.get(m).getUser_language().get(o).getLanguage_id())
                        result.add(users.get(m));
                }
            }
        }

        //filter kota
//        if (kota != null){
//            users = result;
//            result = new ArrayList<>();
//            for (int n=0;n< users.size();n++){
//                if (users.get(n).getContact().getCity().equals(kota)){
//                    result.add(users.get(n));
//                }
//            }
//        }

        //filter gaji
//        if (gajii != 0 && wt_id !=null){
//            users = result;
//            result = new ArrayList<>();
//            for (int n=0; n<users.size();n++){
//                for (int m=0; m<users.get(n).getUser_work_time().size();m++){
//                    if (users.get(n).getUser_work_time().get(m).getWork_time_id() == wt_id  && users.get(n).getUser_work_time().get(m).getCost() <= gajii)
//                        result.add(users.get(n));
//                }
//            }
//        }
        return result;
    }
}
