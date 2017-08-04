package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPermintaan;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OfferRepo;
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
 * Created by jcla123ns on 30/07/17.
 */

public class FragmentListOffer extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPermintaan rec_Adapter;
    private List<Offer> offers = new ArrayList<>();
    private User user = new User();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_list_offer, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);


        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_offer);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPermintaan();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaultwk(((MasterCleanApplication)getActivity().getApplication()).globalStaticData.getWaktu_kerjas());
        rec_Adapter.setcontext(getContext());
//        rec_Adapter.setOffers(offers, user.getId());

        Call<List<Offer>> caller = APIManager.getRepository(OfferRepo.class).getoffersbyart(user.getId());
        caller.enqueue(new APICallback<List<Offer>>() {
            @Override
            public void onSuccess(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onSuccess(call, response);
                rec_Adapter.setOffers(response.body(), user.getId());
            }

            @Override
            public void onError(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });

        return _view;
    }
}
