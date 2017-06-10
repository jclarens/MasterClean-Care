package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TA.MVP.appmobilemember.Model.Adapter.PagerAdapterCari;
import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentCari extends Fragment {
    private TabLayout tabLayoutcari;
    private ViewPager viewPagercari;
    private PagerAdapter pagerAdaptercari;
    public FragmentCari(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_cari, container, false);

        tabLayoutcari = (TabLayout) _view.findViewById(R.id.tablayout_cari);
        viewPagercari = (ViewPager) _view.findViewById(R.id.viewpager_cari);

        pagerAdaptercari = new PagerAdapterCari(getChildFragmentManager(), getContext());
        viewPagercari.setAdapter(pagerAdaptercari);
        tabLayoutcari.setupWithViewPager(viewPagercari);

        return _view;
    }
}
