package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPemesanan;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterRiwayat;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 09/08/17.
 */

public class FragmentPesananRiwayat extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterRiwayat rec_Adapter;
    private List<Order> orders = new ArrayList<>();
    private User user = new User();
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutnolist, layoutloading, layoutnoconnection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_history, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        layoutnolist = (LinearLayout) _view.findViewById(R.id.layout_nolist);
        layoutloading = (LinearLayout) _view.findViewById(R.id.layout_loading);
        layoutnoconnection = (LinearLayout) _view.findViewById(R.id.layout_noconnection);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_order);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterRiwayat();
        rec_Adapter.setcontext(getActivity());
        recyclerView.setAdapter(rec_Adapter);

        showloading();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadorder();
            }
        });
        loadorder();
        return _view;
    }
    public void loadorder(){
        Call<List<Order>> caller = APIManager.getRepository(OrderRepo.class).getorderByArt(user.getId().toString());
        caller.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                hidenoconnection();
                orders = filter(response.body());
                if (orders.size() < 1){
                    hidelist();
                }else {
                    showlist();
                    rec_Adapter.setOrders(orders);
                }
                swipeRefreshLayout.setRefreshing(false);
                hideloading();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                hideloading();
                shownoconnection();
            }
        });
    }
    public List<Order> filter (List<Order> orders){
        List<Order> temp = new ArrayList<>();
        for (int n=0; n<orders.size();n++){
            if (!(orders.get(n).getStatus() == 0 || orders.get(n).getStatus() == 1))
                temp.add(orders.get(n));
        }
        return temp;
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
