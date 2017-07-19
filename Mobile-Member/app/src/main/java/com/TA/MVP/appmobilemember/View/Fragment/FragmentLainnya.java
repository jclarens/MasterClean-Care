package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Context;
import android.content.Intent;
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
import com.TA.MVP.appmobilemember.View.Activity.ProfileActivity;
import com.TA.MVP.appmobilemember.View.Activity.WalletActivity;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentLainnya extends Fragment {

    private ImageView imageprofile, imagewallet, imagebantuan, imageketentuan, imagelogout;
    private TextView txtprofile, txtwallet, txtbantuan, txtketentuan, txtlogout;
    private Context context;

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

        if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
            txtlogout.setText("Login");
        }
        else
            txtlogout.setText("Logout");

        context = getContext();

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
                    Toast.makeText(context,"Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
                else{
//                    Toast.makeText(context,"Profile", Toast.LENGTH_SHORT).show();
                    MainActivity.doStartActivity(getContext(), ProfileActivity.class);
                }

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
                if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
                    Intent i = new Intent(getContext(), AuthActivity.class);
                    i.putExtra(ConstClass.LOGIN_EXTRA, true);
                    i.putExtra(ConstClass.REGISTER_EXTRA, true);
                    ((MainActivity)getActivity()).doStartActivityForResult(i);

                    if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
                        txtlogout.setText("Login");
                    }
                    else
                        txtlogout.setText("Logout");
                }
                else{
                    SharedPref.save(SharedPref.ACCESS_TOKEN, "");
                    SharedPref.save(ConstClass.USER, "");
                    Toast.makeText(context,"Logout", Toast.LENGTH_SHORT).show();
                    txtlogout.setText("Login");
                }
//                Toast.makeText(context,"Logout", Toast.LENGTH_SHORT).show();
            }
        });

        return _view;
    }
}
