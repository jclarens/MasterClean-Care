package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentProfile extends Fragment{
    private ImageView image;
    private RatingBar ratingBar;
    private TextView nama, profesi, keterangan;
    private Switch aSwitch;
    private User user;
    private StaticData staticData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_profile, container, false);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        staticData = ((MasterCleanApplication)getActivity().getApplication()).globalStaticData;

        image = (ImageView) _view.findViewById(R.id.prof_img);
        ratingBar = (RatingBar) _view.findViewById(R.id.prof_rate);
        nama = (TextView) _view.findViewById(R.id.prof_tv_name);
        keterangan = (TextView) _view.findViewById(R.id.prof_tv_keterangan);
        profesi = (TextView) _view.findViewById(R.id.prof_tv_prof);
        aSwitch  = (Switch) _view.findViewById(R.id.prof_switch);

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

        return _view;
    }
}
