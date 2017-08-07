package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPemesanan;
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
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentHistory extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPemesanan rec_Adapter;
    private List<Order> orders = new ArrayList<>();
    private User user = new User();
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView wallet;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_history, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
        wallet = (TextView) _view.findViewById(R.id.wallet);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_order);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPemesanan();
        rec_Adapter.setcontext(getActivity());
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setOrders(orders, 3);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaduser();
            }
        });
        loadtampilan();
        return _view;
    }
    public  void loadtampilan(){
        wallet.setText(setRP(user.getUser_wallet().getAmt()));
        Call<List<Order>> caller = APIManager.getRepository(OrderRepo.class).getorderByArt(user.getId().toString());
        caller.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                orders = response.body();
                rec_Adapter.setOrders(orders, 3);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getContext(),"Fail", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void loaduser(){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(user.getId().toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                user = response.body();
                loadtampilan();
            }

            @Override
            public void onError(Call<User> call, Response<User> response) {
                super.onError(call, response);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
