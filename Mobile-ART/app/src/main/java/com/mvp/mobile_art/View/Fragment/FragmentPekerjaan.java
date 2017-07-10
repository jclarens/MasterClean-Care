package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.mobile_art.Model.PagerAdapterJadwal;
import com.mvp.mobile_art.R;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentPekerjaan extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pekerjaan, container, false);

        return _view;
    }
}
