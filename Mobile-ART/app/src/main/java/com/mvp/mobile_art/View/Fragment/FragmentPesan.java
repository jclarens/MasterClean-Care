package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.mobile_art.Model.PagerAdapterPesan;
import com.mvp.mobile_art.R;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentPesan extends Fragment {
    private TabLayout tabLayoutpesan;
    private ViewPager viewPagerpesan;
    private PagerAdapter pagerAdapterpesan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pesan, container, false);

        tabLayoutpesan = (TabLayout) _view.findViewById(R.id.tablayout_pesan);
        viewPagerpesan = (ViewPager) _view.findViewById(R.id.viewpager_pesan);

        pagerAdapterpesan = new PagerAdapterPesan(getChildFragmentManager(), getContext());
        viewPagerpesan.setAdapter(pagerAdapterpesan);
        tabLayoutpesan.setupWithViewPager(viewPagerpesan);

        return _view;
    }
}
