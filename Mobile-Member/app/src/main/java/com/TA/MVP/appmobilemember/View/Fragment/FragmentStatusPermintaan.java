package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterPemesanan;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class FragmentStatusPermintaan extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPemesanan rec_Adapter;
    private Button btn_add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_status_permintaan, container, false);

        btn_add = (Button) _view.findViewById(R.id.btn_addpermintaan);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getContext(), PemesananActivity.class);
//                startActivity(i);
            }
        });

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_order);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterPemesanan();
        recyclerView.setAdapter(rec_Adapter);
        return _view;
    }
}
