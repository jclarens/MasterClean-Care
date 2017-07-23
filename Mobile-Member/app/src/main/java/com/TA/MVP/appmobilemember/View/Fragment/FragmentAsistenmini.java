package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.AsistenActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Zackzack on 18/07/2017.
 */

public class FragmentAsistenmini extends Fragment {
    private TextView nama,usia,agama;
    private Button btn_moreinfo;
    private ImageView imageView;
    private RatingBar ratingBar;
    private User art;
    private int thisYear, artbornyear;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat yearformat = new SimpleDateFormat("yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_asistenmini, container, false);
        art = GsonUtils.getObjectFromJson(getArguments().getString(ConstClass.ART_EXTRA), User.class);

        nama = (TextView) _view.findViewById(R.id.asism_nama);
        usia = (TextView) _view.findViewById(R.id.asism_usia);
        agama = (TextView) _view.findViewById(R.id.asism_agama);
        imageView = (ImageView) _view.findViewById(R.id.asism_img);
        ratingBar = (RatingBar) _view.findViewById(R.id.asism_rating);
        btn_moreinfo = (Button) _view.findViewById(R.id.asism_btninfo);

        nama.setText(art.getName());

        thisYear = calendar.get(Calendar.YEAR);
        artbornyear = Integer.valueOf(yearformat.format(art.getBorn_date()));
        usia.setText(thisYear - artbornyear + " Thn");

        ratingBar.setRating(art.getRate());

        btn_moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AsistenActivity.class);
                i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                startActivity(i);
            }
        });

        return _view;
    }
}
