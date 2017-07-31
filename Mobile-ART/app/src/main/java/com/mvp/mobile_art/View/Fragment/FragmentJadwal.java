package com.mvp.mobile_art.View.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.mobile_art.Model.Adapter.PagerAdapterJadwal;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;

/**
 * Created by Zackzack on 03/07/2017.
 */

public class FragmentJadwal extends Fragment {
    private TabLayout tabLayoutjadwal;
    private ViewPager viewPagerjadwal;
    private PagerAdapter pagerAdapterjadwal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_jadwal, container, false);

        tabLayoutjadwal = (TabLayout) _view.findViewById(R.id.tablayout_jadwal);
        viewPagerjadwal = (ViewPager) _view.findViewById(R.id.viewpager_jadwal);

        pagerAdapterjadwal = new PagerAdapterJadwal(getChildFragmentManager(), getContext());
        viewPagerjadwal.setAdapter(pagerAdapterjadwal);
        tabLayoutjadwal.setupWithViewPager(viewPagerjadwal);

        viewPagerjadwal.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        SharedPref.save(ConstClass.PAGER_JADWAL_POS, "penawaran");
                        break;
                    case 1:
                        SharedPref.save(ConstClass.PAGER_JADWAL_POS, "masuk");
                        break;
                    case 2:
                        SharedPref.save(ConstClass.PAGER_JADWAL_POS, "diterima");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (SharedPref.getValueString(ConstClass.PAGER_JADWAL_POS).equals("masuk")){
            viewPagerjadwal.setCurrentItem(1);
        }
        else if (SharedPref.getValueString(ConstClass.PAGER_JADWAL_POS).equals("diterima")){
            viewPagerjadwal.setCurrentItem(2);
        }
        else{
            viewPagerjadwal.setCurrentItem(0);
        }

        return _view;
    }
}
