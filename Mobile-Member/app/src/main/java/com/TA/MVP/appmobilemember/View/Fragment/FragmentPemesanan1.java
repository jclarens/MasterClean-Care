package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import static android.R.attr.fragment;

/**
 * Created by Zackzack on 14/06/2017.
 */

public class FragmentPemesanan1 extends Fragment {
    private TextView namaasis, usiaasis, agamaasis;
    private ImageView fotoasis;
    private RatingBar ratingasis;
    private EditText nama, email, notelp, alamat;
    private Spinner prov, kota;
    private Button selengkapnya, next;
    private RelativeLayout layoutasisten;
    private FragmentAsistenmini fragmentAsistenmini;
    private User art;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan1, container, false);
        art = GsonUtils.getObjectFromJson(getArguments().getString(ConstClass.ART_EXTRA), User.class);

        layoutasisten = (RelativeLayout) _view.findViewById(R.id.layout_asisten);
        nama = (EditText) _view.findViewById(R.id.pms1_et_nama);
        email = (EditText) _view.findViewById(R.id.pms1_et_email);
        notelp = (EditText) _view.findViewById(R.id.pms1_et_notelp);
        alamat = (EditText) _view.findViewById(R.id.pms1_et_alamat);
        prov = (Spinner) _view.findViewById(R.id.pms1_spinner_prov);
        kota = (Spinner) _view.findViewById(R.id.pms1_spinner_kota);
        next = (Button) _view.findViewById(R.id.pms1_btn_next);

        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
        fragmentAsistenmini.setArguments(b);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi dulu
                ((PemesananActivity)getActivity()).doChangeFragment(2);
            }
        });

        return _view;
    }

    public void doChangeFragmentAsisten(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragment).commit();
    }
}
