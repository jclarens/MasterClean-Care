package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPemesanan;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 06/07/2017.
 */

public class FragmentPesananMasuk extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPemesanan rec_Adapter;
    private List<Order> orders = new ArrayList<>();
    private User user = new User();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pesanan_masuk, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_order);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPemesanan();
        rec_Adapter.setcontext(getActivity());
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setOrders(orders, 0);

        Call<List<Order>> caller = APIManager.getRepository(OrderRepo.class).getorderByArt(user.getId().toString());
        caller.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                orders = response.body();
                rec_Adapter.setOrders(orders, 0);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getContext(),"Fail", Toast.LENGTH_SHORT).show();
            }
        });

        return _view;
    }
}
