package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;


/**
 * Created by Zackzack on 18/06/2017.
 */

public class FragmentPemesanan3 extends Fragment {
    private TextView namaasis, usiaasis, agamaasis, estimasitext;
    private EditText prof, estimasi, mulaitime, mulaidate, selesaitime, selesaidate, total;
    private LinearLayout layoutlistpekerjaan;
    private ImageView fotoasis;
    private RatingBar ratingasis;
    private CheckBox ketentuan;
    private Button prev, pesan, selengkapnya;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan3, container, false);

        namaasis = (TextView) _view.findViewById(R.id.pms3_tv_namaasis);
        usiaasis = (TextView) _view.findViewById(R.id.pms3_tv_usiaasis);
        agamaasis = (TextView) _view.findViewById(R.id.pms3_tv_agamaasis);
        estimasitext = (TextView) _view.findViewById(R.id.pms3_tv_estimasiwaktu);
        prof = (EditText) _view.findViewById(R.id.pms3_et_prof);
        estimasi = (EditText) _view.findViewById(R.id.pms3_et_estimate);
        mulaitime = (EditText) _view.findViewById(R.id.pms3_et_mulaitime);
        mulaidate = (EditText) _view.findViewById(R.id.pms3_et_mulaidate);
        selesaitime = (EditText) _view.findViewById(R.id.pms3_et_selesaitime);
        selesaidate = (EditText) _view.findViewById(R.id.pms3_et_selesaidate);
        total = (EditText) _view.findViewById(R.id.pms3_et_total);
        layoutlistpekerjaan = (LinearLayout) _view.findViewById(R.id.pms3_layout_listpekerjaan);
        selengkapnya = (Button) _view.findViewById(R.id.pms3_btn_selengkapnya);
        prev = (Button) _view.findViewById(R.id.pms3_btn_prev);
        pesan = (Button) _view.findViewById(R.id.pms3_btn_pesan);
        fotoasis = (ImageView) _view.findViewById(R.id.pms3_img_asis);
        ratingasis = (RatingBar) _view.findViewById(R.id.pms3_asisrating);
        ketentuan = (CheckBox) _view.findViewById(R.id.pms3_cb_kttn);

        selengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PemesananActivity)getActivity()).doChangeFragment(2);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi dulu
            }
        });

        return _view;
    }
}
