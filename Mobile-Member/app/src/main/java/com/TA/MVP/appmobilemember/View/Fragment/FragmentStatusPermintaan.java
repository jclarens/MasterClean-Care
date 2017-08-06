package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button btn_add;
    private User user;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_status_permintaan, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
        btn_add = (Button) _view.findViewById(R.id.btn_addpermintaan);
        btn_add.setOnClickListener(new View.OnClickListener() {
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
                rec_Adapter.setOffers(response.body(), 0);
                swipeRefreshLayout.setRefreshing(false);
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
                Toast.makeText(getContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
