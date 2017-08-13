package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterPesanTerkirim;
import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentPesanTerkirim extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPesanTerkirim rec_Adapter;
    private List<MyMessage> myMessages = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutnolist, layoutloading, layoutnoconnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pesan_terkirim, container, false);

        layoutnolist = (LinearLayout) _view.findViewById(R.id.layout_nolist);
        layoutloading = (LinearLayout) _view.findViewById(R.id.layout_loading);
        layoutnoconnection = (LinearLayout) _view.findViewById(R.id.layout_noconnection);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_pesan);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPesanTerkirim();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setcontext(getActivity());

        showloading();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadmessage();
            }
        });
        loadmessage();

        return _view;
    }
    public void loadmessage(){
        String userid = String.valueOf(GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class).getId());
        Call<List<MyMessage>> caller = APIManager.getRepository(MessageRepo.class).getallmsgfromsenderid(userid);
        caller.enqueue(new APICallback<List<MyMessage>>() {
            @Override
            public void onSuccess(Call<List<MyMessage>> call, Response<List<MyMessage>> response) {
                super.onSuccess(call, response);
                hidenoconnection();
                myMessages = response.body();
                if (myMessages.size() < 1){
                    hidelist();
                }else{
                    showlist();
                    rec_Adapter.setPesan(myMessages);
                }
                swipeRefreshLayout.setRefreshing(false);
                hideloading();
            }

            @Override
            public void onFailure(Call<List<MyMessage>> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                shownoconnection();
                hideloading();
            }
        });
    }
    public void hidelist(){
        recyclerView.setVisibility(View.GONE);
        layoutnolist.setVisibility(View.VISIBLE);
    }
    public void showlist(){
        recyclerView.setVisibility(View.VISIBLE);
        layoutnolist.setVisibility(View.GONE);
    }
    public void showloading(){
        layoutloading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void hideloading(){
        layoutloading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    public void shownoconnection(){
        layoutnoconnection.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void hidenoconnection(){
        layoutnoconnection.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
