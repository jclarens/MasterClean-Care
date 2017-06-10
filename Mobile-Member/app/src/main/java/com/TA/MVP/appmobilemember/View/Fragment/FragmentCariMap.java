package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.FilterActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentCariMap extends Fragment {
    private ImageButton imgcari;
    private EditText namalokasi;
    private Button btnfilter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_cari_map, container, false);

        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        namalokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);
        btnfilter = (Button) _view.findViewById(R.id.carimap_btn_filter);

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.doStartActivity(getContext(), FilterActivity.class);
            }
        });

        return _view;
    }
}