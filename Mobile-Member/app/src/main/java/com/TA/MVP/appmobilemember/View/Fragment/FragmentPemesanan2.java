package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;

/**
 * Created by Zackzack on 18/06/2017.
 */

public class FragmentPemesanan2 extends Fragment {
    private Spinner prof, waktukrj;
    private LinearLayout layoutlistkerja;
    private EditText mulaitime, mulaidate, selesaitime, selesaidate, estimasi, cttntmbhn, totalbiaya;
    private TextView estimasiwaktutext;
    private Button prev,next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan2, container, false);

        prof = (Spinner) _view.findViewById(R.id.pms2_spinner_prof);
        waktukrj = (Spinner) _view.findViewById(R.id.pms2_spinner_waktukerja);
        layoutlistkerja = (LinearLayout) _view.findViewById(R.id.pms2_layout_listkerja);
        mulaitime = (EditText) _view.findViewById(R.id.pms2_et_mulaitime);
        mulaidate = (EditText) _view.findViewById(R.id.pms2_et_mulaidate);
        selesaitime = (EditText) _view.findViewById(R.id.pms2_et_selesaitime);
        selesaidate = (EditText) _view.findViewById(R.id.pms2_et_selesaidate);
        estimasi = (EditText) _view.findViewById(R.id.pms2_et_estimasi);
        cttntmbhn = (EditText) _view.findViewById(R.id.pms2_et_cttn);
        totalbiaya = (EditText) _view.findViewById(R.id.pms2_et_total);
        estimasiwaktutext = (TextView) _view.findViewById(R.id.pms2_tv_estimasiwaktu);
        prev = (Button) _view.findViewById(R.id.pms2_btn_prev);
        next = (Button) _view.findViewById(R.id.pms2_btn_next);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PemesananActivity)getActivity()).doChangeFragment(1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi dulu
                ((PemesananActivity)getActivity()).doChangeFragment(3);
            }
        });

        return _view;
    }
}
