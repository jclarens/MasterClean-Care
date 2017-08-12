package com.mvp.mobile_art.View.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.MyMessage;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Activity.KetentuanActivity;
import com.mvp.mobile_art.View.Activity.LoginActivity;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.View.Activity.ProfileActivity;
import com.mvp.mobile_art.View.Activity.SaranActivity;
import com.mvp.mobile_art.View.Activity.TulisPesanActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentLainnya extends Fragment {
    private ImageView imageprofile, imageketentuan, imagelogout, contactus;
    private TextView txtprofile, txtketentuan, txtlogout;
    private Context context;
    private User admin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_lainnya, container, false);
        imageprofile = (ImageView) _view.findViewById(R.id.iv_profile);
        imageketentuan = (ImageView) _view.findViewById(R.id.iv_ketentuan);
        contactus = (ImageView) _view.findViewById(R.id.iv_contactus);
        imagelogout = (ImageView) _view.findViewById(R.id.iv_logout);
        txtprofile = (TextView) _view.findViewById(R.id.tv_profile);
        txtketentuan = (TextView) _view.findViewById(R.id.tv_ketentuan);
        txtlogout = (TextView) _view.findViewById(R.id.tv_logout);

        if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
            txtlogout.setText("Login");
        }
        else
            txtlogout.setText("Logout");

        context = getContext();

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SaranActivity.class);
                startActivity(intent);
            }
        });

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
                    Toast.makeText(context,"Silahkan login terlebih dahulu.", Toast.LENGTH_SHORT).show();
                }
                else{
                    MainActivity.doStartActivity(getContext(), ProfileActivity.class);
                }

            }
        });
//        imagebantuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.doStartActivity(getContext(), BantuanActivity.class);
//            }
//        });
        imageketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.doStartActivity(getContext(), KetentuanActivity.class);
            }
        });
        imagelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).abuildermessage("Anda akan melakukan logout?","Konfirmasi Logout");
                ((MainActivity)getActivity()).abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                ((MainActivity)getActivity()).abuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPref.save(ConstClass.USER, "");
                        SharedPref.save(SharedPref.ACCESS_TOKEN, "");
                        Intent intent1 = new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent1, MainActivity.REQUEST_LOGIN);
                    }
                });
                ((MainActivity)getActivity()).showalertdialog();
            }
        });

        return _view;
    }
}
