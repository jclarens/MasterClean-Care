package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.FilterActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentCariMap extends Fragment {
    private ImageButton imgcari;
    private EditText namalokasi,nama;
    private Button btnfilter;
//    private DialogFragmentFilter dialogFragmentFilter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_cari_map, container, false);

        imgcari = (ImageButton) _view.findViewById(R.id.carimap_icon_cari);
        namalokasi = (EditText) _view.findViewById(R.id.carimap_et_carilokasi);
        btnfilter = (Button) _view.findViewById(R.id.carimap_btn_filter);

//        dialogFragmentFilter = new DialogFragmentFilter();
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialogFragmentFilter.show(getActivity().getFragmentManager(), "filter");
//                MainActivity.doStartActivity(getContext(), FilterActivity.class);
                Intent i = new Intent(getContext(),FilterActivity.class);
                startActivityForResult(i, 1);
            }
        });

        return _view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("nama");
                Toast.makeText(getContext(),"Nama = " + result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }
}