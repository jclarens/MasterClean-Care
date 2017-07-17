package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterAsisten;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.FilterActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentCariList extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapterAsisten rec_Adapter;
    private Button btnfilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_cari_list, container, false);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);
        btnfilter = (Button) _view.findViewById(R.id.carilist_btn_filter);

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.doStartActivity(getContext(), FilterActivity.class);
                Intent i = new Intent(getContext(),FilterActivity.class);
                startActivityForResult(i, 1);
            }
        });

        //recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);
        rec_Adapter = new RecyclerAdapterAsisten();
        recyclerView.setAdapter(rec_Adapter);
        //get users
        Call<List<User>> caller = APIManager.getRepository(UserRepo.class).getallart();
        caller.enqueue(new APICallback<List<User>>() {
            @Override
            public void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                super.onSuccess(call, response);
                rec_Adapter.setART(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });


        return _view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
//                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                Map<String,String> map = new HashMap<>();
                map.put("user_type","2");
                map.put("name",data.getStringExtra("nama"));
                map.put("race",data.getStringExtra("suku"));
                if (data.getStringExtra("agama") != "0")
                    map.put("religion",data.getStringExtra("agama"));
                if (data.getStringExtra("kota") != "0")
                    map.put("city",data.getStringExtra("kota"));
//                if (data.getStringExtra("profesi") != "0")
//                    map.put("user_job",data.getStringExtra("profesi"));
                if (data.getStringExtra("WT") != "0")
                    map.put("",data.getStringExtra("WT"));
                //get users
                Call<List<User>> caller = APIManager.getRepository(UserRepo.class).searchuser(map);
                caller.enqueue(new APICallback<List<User>>() {
                    @Override
                    public void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                        super.onSuccess(call, response);
                        rec_Adapter.setART(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }
            if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
}
