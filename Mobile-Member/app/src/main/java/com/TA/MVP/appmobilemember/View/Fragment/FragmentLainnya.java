package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.BantuanActivity;
import com.TA.MVP.appmobilemember.View.Activity.KetentuanActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.PengaturanActivity;
import com.TA.MVP.appmobilemember.View.Activity.ProfileActivity;
import com.TA.MVP.appmobilemember.View.Activity.WalletActivity;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentLainnya extends Fragment {
    private ImageView imageprofile, imagewallet, imagebantuan, imageketentuan, imagelogout;
    private TextView txtprofile, txtwallet, txtbantuan, txtketentuan, txtlogout;
    private Context context;

    public FragmentLainnya() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_lainnya, container, false);

        imageprofile = (ImageView) _view.findViewById(R.id.iv_profile);
        imagewallet = (ImageView) _view.findViewById(R.id.iv_wallet);
        imagebantuan = (ImageView) _view.findViewById(R.id.iv_bantuan);
        imageketentuan = (ImageView) _view.findViewById(R.id.iv_ketentuan);
        imagelogout = (ImageView) _view.findViewById(R.id.iv_logout);
        txtprofile = (TextView) _view.findViewById(R.id.tv_profile);
        txtwallet = (TextView) _view.findViewById(R.id.tv_wallet);
        txtbantuan = (TextView) _view.findViewById(R.id.tv_bantuan);
        txtketentuan = (TextView) _view.findViewById(R.id.tv_ketentuan);
        txtlogout = (TextView) _view.findViewById(R.id.tv_logout);
        context = getContext();

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Profile", Toast.LENGTH_SHORT).show();
                MainActivity.doStartActivity(getContext(), ProfileActivity.class);
            }
        });
        imagewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.doStartActivity(getContext(), WalletActivity.class);
            }
        });
        imagebantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.doStartActivity(getContext(), BantuanActivity.class);
            }
        });
        imageketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.doStartActivity(getContext(), KetentuanActivity.class);
            }
        });
        imagelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Logout", Toast.LENGTH_SHORT).show();
                MainActivity.doChangeActivity(getContext(), AuthActivity.class);
            }
        });

        return _view;
    }
}
