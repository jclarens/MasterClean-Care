package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterPemesanan;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterPermintaan;
import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OfferRepo;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActivity;
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
 * Created by Zackzack on 14/07/2017.
 */

public class FragmentStatusPermintaan extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPermintaan rec_Adapter;
    private List<Offer> offers = new ArrayList<>();
    private User user;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton btnadd;
    private LinearLayout layoutnolist, layoutloading, layoutnoconnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_status_permintaan, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        layoutnolist = (LinearLayout) _view.findViewById(R.id.layout_nolist);
        layoutloading = (LinearLayout) _view.findViewById(R.id.layout_loading);
        layoutnoconnection = (LinearLayout) _view.findViewById(R.id.layout_noconnection);
        btnadd = (FloatingActionButton) _view.findViewById(R.id.btn_addpermintaan);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
//        btn_add = (Button) _view.findViewById(R.id.btn_addpermintaan);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (SharedPref.getValueString(ConstClass.USER).equals(""))
//                    Toast.makeText(getContext(),"Silahkan login untuk menggunakan fitur ini", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), PermintaanActivity.class);
                startActivityForResult(i, MainActivity.REQUEST_OFFER);
            }
        });

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_order);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPermintaan();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setcontext(getActivity());

        showloading();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadoffers();
            }
        });

        loadoffers();
        return _view;
    }
    public void loadoffers(){
        Call<List<Offer>> caller = APIManager.getRepository(OfferRepo.class).getofferByMember(user.getId().toString());
        caller.enqueue(new APICallback<List<Offer>>() {
            @Override
            public void onSuccess(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onSuccess(call, response);
                hidenoconnection();
                offers = filter(response.body());
                if (offers.size() < 1)
                    hidelist();
                else {
                    showlist();
                    rec_Adapter.setOffers(offers);
                }
                swipeRefreshLayout.setRefreshing(false);
                hideloading();
            }

            @Override
            public void onError(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onError(call, response);
                Toast.makeText(getContext(), "Terjadi kesalahan ", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
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
    public List<Offer> filter(List<Offer> offers){
        List<Offer> result = new ArrayList<>();
        for (int n = 0; n < offers.size(); n++) {
            if (offers.get(n).getStatus() == 0)
                result.add(offers.get(n));
        }
        return result;
    }
}
