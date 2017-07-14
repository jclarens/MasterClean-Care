package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterPemesanan;
import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentStatusDisetujui extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterPemesanan rec_Adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_status_disetujui, container, false);

        recyclerView = (RecyclerView) _view.findViewById(R.id.recycleview_asisten);

        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);

        rec_Adapter = new RecyclerAdapterPemesanan();
        recyclerView.setAdapter(rec_Adapter);
        return _view;
    }
}
