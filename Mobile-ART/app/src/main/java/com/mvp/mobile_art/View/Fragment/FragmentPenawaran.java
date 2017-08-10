package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.mobile_art.Model.Adapter.PagerAdapterPenawaran;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class FragmentPenawaran extends Fragment {
    private TabLayout tabLayoutpenawaran;
    private ViewPager viewPagerpenawaran;
    private PagerAdapterPenawaran pagerAdapterpenawaran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_penawaran, container, false);

        tabLayoutpenawaran = (TabLayout) _view.findViewById(R.id.tablayout_penawaran);
        viewPagerpenawaran = (ViewPager) _view.findViewById(R.id.viewpager_penawaran);

        pagerAdapterpenawaran = new PagerAdapterPenawaran(getChildFragmentManager(), getContext());
        viewPagerpenawaran.setAdapter(pagerAdapterpenawaran);
        tabLayoutpenawaran.setupWithViewPager(viewPagerpenawaran);

        viewPagerpenawaran.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        SharedPref.save(ConstClass.PAGER_PENAWARAN_POS, "all");
                        break;
                    case 1:
                        SharedPref.save(ConstClass.PAGER_PENAWARAN_POS, "accepted");
                        break;
                    case 2:
                        SharedPref.save(ConstClass.PAGER_PENAWARAN_POS, "history");
                        break;
                }
                pagerAdapterpenawaran.notifyDataSetChanged();
                viewPagerpenawaran.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (SharedPref.getValueString(ConstClass.PAGER_PENAWARAN_POS).equals("all")){
            viewPagerpenawaran.setCurrentItem(0);
        } else if (SharedPref.getValueString(ConstClass.PAGER_PENAWARAN_POS).equals("accepted")){
            viewPagerpenawaran.setCurrentItem(1);
        } else if (SharedPref.getValueString(ConstClass.PAGER_PENAWARAN_POS).equals("history")){
            viewPagerpenawaran.setCurrentItem(2);
        }
        else {
            viewPagerpenawaran.setCurrentItem(0);
        }

        return _view;
    }
}
