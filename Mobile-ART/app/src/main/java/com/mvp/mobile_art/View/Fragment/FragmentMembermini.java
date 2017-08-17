package com.mvp.mobile_art.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.RoundedTransformation;
import com.mvp.mobile_art.View.Activity.MemberActivity;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;
import com.squareup.picasso.Picasso;

/**
 * Created by Zackzack on 18/07/2017.
 */

public class FragmentMembermini extends Fragment {
    private TextView nama,telp;
    private Button btn_moreinfo;
    private ImageView imageView;
    private User member;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_member_mini, container, false);
        member = GsonUtils.getObjectFromJson(getArguments().getString(ConstClass.MEMBER_EXTRA), User.class);

        nama = (TextView) _view.findViewById(R.id.nama);
        telp = (TextView) _view.findViewById(R.id.telp);
        imageView = (ImageView) _view.findViewById(R.id.img);
        btn_moreinfo = (Button) _view.findViewById(R.id.btninfo);

        nama.setText(member.getName());
        telp.setText(member.getContact().getPhone());

        Picasso.with(getContext())
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+member.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .resize(100, 100)
                .transform(new RoundedTransformation(50, 0))
                .into(imageView);

        btn_moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MemberActivity.class);
                i.putExtra(ConstClass.MEMBER_EXTRA, GsonUtils.getJsonFromObject(member));
                startActivity(i);
            }
        });

        return _view;
    }
}
