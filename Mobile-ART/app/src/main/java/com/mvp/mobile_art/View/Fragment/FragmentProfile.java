package com.mvp.mobile_art.View.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Array.ArrayAgama;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentProfile extends Fragment{
    private ImageView image;
    private RatingBar ratingBar;
    private TextView nama, telp, usia, agama, suku, kota, profesi, bahasa;
    private EditText keterangan;
    private Switch aSwitch;
    private User user = new User();
    private ArrayAgama arrayAgama = new ArrayAgama();
    private StaticData staticData;
    private int thisYear, bornyear;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat yearformat = new SimpleDateFormat("yyyy");
    private CompoundButton.OnCheckedChangeListener changelistener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_profile, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        Log.d("temporary","onCreate:"+ SharedPref.getValueString(ConstClass.USER));
        staticData = ((MasterCleanApplication)getActivity().getApplication()).globalStaticData;

        image = (ImageView) _view.findViewById(R.id.prof_img);
        ratingBar = (RatingBar) _view.findViewById(R.id.prof_rate);
        keterangan = (EditText) _view.findViewById(R.id.keterangan);
        nama = (TextView) _view.findViewById(R.id.nama);
        telp = (TextView) _view.findViewById(R.id.telp);
        usia = (TextView) _view.findViewById(R.id.usia);
        agama = (TextView) _view.findViewById(R.id.agama);
        suku = (TextView) _view.findViewById(R.id.suku);
        kota = (TextView) _view.findViewById(R.id.kota);
        profesi = (TextView) _view.findViewById(R.id.prof);
        bahasa = (TextView) _view.findViewById(R.id.bahasa);
        aSwitch  = (Switch) _view.findViewById(R.id.prof_switch);

        getallinfo(user.getId());
        return _view;
    }
    public void settampilan(){
        Picasso.with(getContext())
                .load(Settings.getRetrofitAPIUrl()+"image/"+user.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .resize(image.getWidth(), image.getHeight())
                .into(image);

        ratingBar.setRating(user.getRate());
        nama.setText(user.getName());
        telp.setText(user.getContact().getPhone());
        //usia
        thisYear = calendar.get(Calendar.YEAR);
        bornyear = Integer.valueOf(yearformat.format(user.getBorn_date()));
        usia.setText(thisYear - bornyear + " Thn");

        agama.setText(arrayAgama.getArrayList().get(user.getReligion()-1));
        suku.setText(user.getRace());
        kota.setText(staticData.getPlaces().get(user.getContact().getCity()-1).getName());
        String temp = "";
        for(int n=0;n<user.getUser_job().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getJobs().get(user.getUser_job().get(n).getJob_id()-1);
        }
        profesi.setText(temp);
        temp = "";
        for(int n=0;n<user.getUser_language().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getLanguages().get(user.getUser_language().get(n).getLanguage_id()-1).getLanguage();
        }
        bahasa.setText(temp);

        switch (user.getStatus()){
            case 0:
                aSwitch.setText("Tidak Aktif");
                aSwitch.setChecked(false);
                break;
            case 1:
                aSwitch.setText("Aktif");
                aSwitch.setChecked(true);
                break;
        }

        changelistener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                aSwitch.setOnCheckedChangeListener(null);
                if (aSwitch.isChecked()){
                    ((MainActivity)getActivity()).abuildermessage("Anda akan mengubah status anda menjadi aktif.","Konfirmasi");
                    ((MainActivity)getActivity()).abuilder.setPositiveButton("Aktifkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gantistatus(1);
                        }
                    });
                    ((MainActivity)getActivity()).abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aSwitch.setChecked(false);
                            aSwitch.setOnCheckedChangeListener(changelistener);
                        }
                    });
                    ((MainActivity)getActivity()).showalertdialog();
                }else {
                    ((MainActivity)getActivity()).abuildermessage("Anda tidak akan dapat menerima transaksi baru saat tidak aktif.","Konfirmasi");
                    ((MainActivity)getActivity()).abuilder.setPositiveButton("Nonaktifkan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gantistatus(0);
                        }
                    });
                    ((MainActivity)getActivity()).abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            aSwitch.setChecked(true);
                            aSwitch.setOnCheckedChangeListener(changelistener);
                        }
                    });
                    ((MainActivity)getActivity()).showalertdialog();
                }
            }
        };
        aSwitch.setOnCheckedChangeListener(changelistener);
    }
    public void getallinfo(Integer id){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                user = response.body();
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
                settampilan();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    public void gantistatus(final Integer integer){
        ((MainActivity)getActivity()).initProgressDialog("Mengubah Status");
        ((MainActivity)getActivity()).showDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", String.valueOf(integer));
        user.setStatus(integer);
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(), map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
                if (integer == 1)
                    Toast.makeText(getContext(), "Anda Sudah Aktif", Toast.LENGTH_SHORT).show();
                else if (integer == 0)
                    Toast.makeText(getContext(), "Anda Sudah Tidak Aktif", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).dismissDialog();
                aSwitch.setOnCheckedChangeListener(changelistener);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((MainActivity)getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                if (integer == 1)
                    aSwitch.setChecked(false);
                else if (integer == 0)
                    aSwitch.setChecked(true);
                aSwitch.setOnCheckedChangeListener(changelistener);
            }

            @Override
            public void onError(Call<UserResponse> call, Response<UserResponse> response) {
                super.onError(call, response);
                Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                if (integer == 1)
                    aSwitch.setChecked(false);
                else if (integer == 0)
                    aSwitch.setChecked(true);
                ((MainActivity)getActivity()).dismissDialog();
                aSwitch.setOnCheckedChangeListener(changelistener);
            }

            @Override
            public void onUnauthorized(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnauthorized(call, response);
                Toast.makeText(getContext(), "Terjadi kesalahan autentikasi", Toast.LENGTH_SHORT).show();
                if (integer == 1)
                    aSwitch.setChecked(false);
                else if (integer == 0)
                    aSwitch.setChecked(true);
                ((MainActivity)getActivity()).dismissDialog();
                aSwitch.setOnCheckedChangeListener(changelistener);
            }
        });
    }
}
