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
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPesanTerkirim;
import com.mvp.mobile_art.Model.Basic.MyMessage;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.MessageRepo;
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

public class FragmentPesanTerkirim extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPesanTerkirim rec_Adapter;
    private List<MyMessage> myMessages = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pesan_terkirim, container, false);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_pesan);

        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);

        rec_Adapter = new RecyclerAdapterPesanTerkirim();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setPesan(myMessages);

        String userid = String.valueOf(GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class).getId());
        Call<List<MyMessage>> caller = APIManager.getRepository(MessageRepo.class).getallmsgfromsenderid(userid);
        caller.enqueue(new APICallback<List<MyMessage>>() {
            @Override
            public void onSuccess(Call<List<MyMessage>> call, Response<List<MyMessage>> response) {
                super.onSuccess(call, response);
                rec_Adapter.setPesan(response.body());
            }

            @Override
            public void onUnauthorized(Call<List<MyMessage>> call, Response<List<MyMessage>> response) {
                super.onUnauthorized(call, response);
            }

            @Override
            public void onFailure(Call<List<MyMessage>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
        return _view;
    }
}
