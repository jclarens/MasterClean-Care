package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.View.Activity.BantuanActivity;
import com.TA.MVP.appmobilemember.View.Activity.KetentuanActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.ProfileActivity;
import com.TA.MVP.appmobilemember.View.Activity.SaranActivity;
import com.TA.MVP.appmobilemember.View.Activity.TulisPesanActivity;
import com.TA.MVP.appmobilemember.View.Activity.WalletActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentLainnya extends Fragment {

    private ImageView imageprofile, imagewallet, imagebantuan, imageketentuan, imagelogout, contactus;
    private TextView txtprofile, txtwallet, txtbantuan, txtketentuan, txtlogout;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_lainnya, container, false);
        imageprofile = (ImageView) _view.findViewById(R.id.iv_profile);
        imagewallet = (ImageView) _view.findViewById(R.id.iv_wallet);
        imageketentuan = (ImageView) _view.findViewById(R.id.iv_ketentuan);
        contactus = (ImageView) _view.findViewById(R.id.iv_contactus);
        imagelogout = (ImageView) _view.findViewById(R.id.iv_logout);
        txtprofile = (TextView) _view.findViewById(R.id.tv_profile);
        txtwallet = (TextView) _view.findViewById(R.id.tv_wallet);
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
                if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
                    Toast.makeText(context,"Silahkan login terlebih dahulu.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getContext(), SaranActivity.class);
                    startActivity(intent);
                }
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
        imagewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPref.getValueString(SharedPref.ACCESS_TOKEN) == ""){
                    Toast.makeText(context,"Silahkan login terlebih dahulu.", Toast.LENGTH_SHORT).show();
                }
                else{
                    MainActivity.doStartActivity(getContext(), WalletActivity.class);
                }
            }
        });
        imageketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.doStartActivity(getContext(), KetentuanActivity.class);
                Uri uri = Uri.parse("http://masterclean-care.tk/term-mobile");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

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
                    ((MainActivity)getActivity()).abuildermessage("Anda akan melakukan logout?","Konfirmasi Logout");
                    ((MainActivity)getActivity()).abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ((MainActivity)getActivity()).abuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPref.save(SharedPref.ACCESS_TOKEN, "");
                            SharedPref.save(ConstClass.USER, "");
                            Toast.makeText(context,"Logout", Toast.LENGTH_SHORT).show();
                            txtlogout.setText("Login");
                            getActivity().invalidateOptionsMenu();
                        }
                    });
                    ((MainActivity)getActivity()).showalertdialog();
                }
            }
        });

        return _view;
    }
}
