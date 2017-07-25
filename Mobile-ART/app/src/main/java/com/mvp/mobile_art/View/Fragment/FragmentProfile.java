package com.mvp.mobile_art.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Activity.LoginActivity;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentProfile extends Fragment{
    private Button logout;
    private ImageView image;
    private RatingBar ratingBar;
    private TextView nama, profesi, keterangan;
    private Switch aSwitch;
    private User user = new User();
    private StaticData staticData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_profile, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        Log.d("temporary","onCreate:"+ SharedPref.getValueString(ConstClass.USER));
//        Toast.makeText(getContext(),SharedPref.getValueString(ConstClass.USER), Toast.LENGTH_SHORT).show();
        staticData = ((MasterCleanApplication)getActivity().getApplication()).globalStaticData;

        image = (ImageView) _view.findViewById(R.id.prof_img);
        ratingBar = (RatingBar) _view.findViewById(R.id.prof_rate);
        nama = (TextView) _view.findViewById(R.id.prof_tv_name);
        keterangan = (TextView) _view.findViewById(R.id.prof_tv_keterangan);
        profesi = (TextView) _view.findViewById(R.id.prof_tv_prof);
        aSwitch  = (Switch) _view.findViewById(R.id.prof_switch);
//        logout = (Button) _view.findViewById(R.id.prof_logout);


        getallinfo(user.getId());
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        return _view;
    }
    public void settampilan(){
        ratingBar.setRating(user.getRate());
        nama.setText(user.getName());
        String temp = "Profesi : ";
        for(int n=0;n<user.getUser_job().size();n++){
            if (n != 0)
                temp = temp + ", ";
            temp = temp + staticData.getJobs().get(user.getUser_job().get(n).getJob_id()-1);
        }
        profesi.setText(temp);
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
}
