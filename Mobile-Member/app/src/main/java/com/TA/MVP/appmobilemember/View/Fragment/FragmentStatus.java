package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TA.MVP.appmobilemember.Model.Adapter.PagerAdapterStatus;
import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 07/06/2017.
 */

public class FragmentStatus extends Fragment {
    private TabLayout tabLayoutstatus;
    private ViewPager viewPagerstatus;
    private PagerAdapter pagerAdapterstatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_status, container, false);

        tabLayoutstatus = (TabLayout) _view.findViewById(R.id.tablayout_status);
        viewPagerstatus = (ViewPager) _view.findViewById(R.id.viewpager_status);

        pagerAdapterstatus = new PagerAdapterStatus(getChildFragmentManager(), getContext());
        viewPagerstatus.setAdapter(pagerAdapterstatus);
        tabLayoutstatus.setupWithViewPager(viewPagerstatus);

        return _view;
    }
}
