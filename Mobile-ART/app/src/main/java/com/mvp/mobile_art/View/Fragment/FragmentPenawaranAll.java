package com.mvp.mobile_art.View.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPenawaranAll;
import com.mvp.mobile_art.Model.Adapter.RecyclerAdapterPermintaan;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.OfferRepo;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class FragmentPenawaranAll extends Fragment {
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPenawaranAll rec_Adapter;
    private List<Offer> offers = new ArrayList<>();
    private User user = new User();
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutnolist, layoutloading, layoutnoconnection;
    private Calendar calendar = Calendar.getInstance();
    private Calendar waktumulai = new GregorianCalendar();
    private Calendar waktuselesai = new GregorianCalendar();
    private Calendar batasmulai = new GregorianCalendar();
    private Calendar batasselesai = new GregorianCalendar();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_penawaran_all, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        layoutnolist = (LinearLayout) _view.findViewById(R.id.layout_nolist);
        layoutloading = (LinearLayout) _view.findViewById(R.id.layout_loading);
        layoutnoconnection = (LinearLayout) _view.findViewById(R.id.layout_noconnection);
        swipeRefreshLayout = (SwipeRefreshLayout) _view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_penawaran);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPenawaranAll();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaultwk(((MasterCleanApplication)getActivity().getApplication()).globalStaticData.getWaktu_kerjas());
        rec_Adapter.setcontext(getContext());

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
        Call<List<Offer>> caller = APIManager.getRepository(OfferRepo.class).getoffersbystatus(0);
        caller.enqueue(new APICallback<List<Offer>>() {
            @Override
            public void onSuccess(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onSuccess(call, response);
                offers = removeexpired(response.body());
                getjadwal(offers);
            }

            @Override
            public void onError(Call<List<Offer>> call, Response<List<Offer>> response) {
                super.onError(call, response);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public List<Offer> removeexpired(List<Offer> offers){
        List<Offer> temp = new ArrayList<>();
        calendar = Calendar.getInstance();
        for (int n=0; n< offers.size();n++){
            try {
                waktumulai.setTime(getdateFormat.parse(offers.get(n).getStart_date()));
            } catch (ParseException e) {

            }
            if (!calendar.after(waktumulai)){
                temp.add(offers.get(n));
            }
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
    public List<Offer> validasijadwal(List<Order> orders, List<Offer> offers){
        List<Offer> result = new ArrayList<>();
        boolean bentrok = false;
        for (int m=0;m<offers.size();m++){
            bentrok = false;
            try {
                waktumulai.setTime(getdateFormat.parse(offers.get(m).getStart_date()));
                waktuselesai.setTime(getdateFormat.parse(offers.get(m).getEnd_date()));
            } catch (ParseException e) {
//            e.printStackTrace();
            }
            for (int n=0;n<orders.size();n++){

                try {
                    batasmulai.setTime(getdateFormat.parse(orders.get(n).getStart_date()));
                    batasselesai.setTime(getdateFormat.parse(orders.get(n).getEnd_date()));
                    batasmulai.add(Calendar.HOUR_OF_DAY, -1);
                    batasselesai.add(Calendar.HOUR_OF_DAY, 1);
                } catch (ParseException e) {
//                e.printStackTrace();
                }
                if (waktumulai.after(batasmulai) && waktumulai.before(batasselesai) || waktuselesai.after(batasmulai) && waktuselesai.before(batasselesai))
                    bentrok = true;
            }
            if (!bentrok){
                result.add(offers.get(m));
            }
        }
        return result;
    }
    public void getjadwal(final List<Offer> offers){
        Call<List<Order>> callerjadwal = APIManager.getRepository(OrderRepo.class).getordersByArtstatus(user.getId(), 1);
        callerjadwal.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                hidenoconnection();
                List<Offer> offerz = validasijadwal(response.body(), offers);
                if (offerz.size() < 1)
                    hidelist();
                else {
                    showlist();
                    rec_Adapter.setOffers(offerz);
                }
                swipeRefreshLayout.setRefreshing(false);
                hideloading();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                hideloading();
                shownoconnection();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }
}
