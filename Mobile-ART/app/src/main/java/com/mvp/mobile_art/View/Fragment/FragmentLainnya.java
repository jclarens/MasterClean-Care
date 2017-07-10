package com.mvp.mobile_art.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.R;
import com.mvp.mobile_art.View.Activity.BantuanActivity;
import com.mvp.mobile_art.View.Activity.KetentuanActivity;
import com.mvp.mobile_art.View.Activity.LoginActivity;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.View.Activity.PengaturanActivity;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentLainnya extends Fragment {
    private ImageView imagepengaturan, imagebantuan, imageketentuan, imagelogout;
    private TextView txtpengaturan, txtbantuan, txtketentuan, txtlogout;
    private Context context;

    public FragmentLainnya() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_lainnya, container, false);

        imagepengaturan = (ImageView) _view.findViewById(R.id.iv_pengaturan);
        imagebantuan = (ImageView) _view.findViewById(R.id.iv_bantuan);
        imageketentuan = (ImageView) _view.findViewById(R.id.iv_ketentuan);
        imagelogout = (ImageView) _view.findViewById(R.id.iv_logout);
        txtpengaturan = (TextView) _view.findViewById(R.id.tv_pengaturan);
        txtbantuan = (TextView) _view.findViewById(R.id.tv_bantuan);
        txtketentuan = (TextView) _view.findViewById(R.id.tv_ketentuan);
        txtlogout = (TextView) _view.findViewById(R.id.tv_logout);
        context = getContext();

        imagepengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Blom ada", Toast.LENGTH_SHORT).show();
                MainActivity.doStartActivity(getContext(), PengaturanActivity.class);
            }
        });
        imagebantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Blom ada", Toast.LENGTH_SHORT).show();
                MainActivity.doStartActivity(getContext(), BantuanActivity.class);
            }
        });
        imageketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Blom ada", Toast.LENGTH_SHORT).show();
                MainActivity.doStartActivity(getContext(), KetentuanActivity.class);
            }
        });
        imagelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Logout", Toast.LENGTH_SHORT).show();
                MainActivity.doChangeActivity(getContext(), LoginActivity.class);
            }
        });

        return _view;
    }
}
