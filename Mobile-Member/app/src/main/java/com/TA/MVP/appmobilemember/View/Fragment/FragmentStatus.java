package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TA.MVP.appmobilemember.Model.Adapter.PagerAdapterStatus;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;

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

        viewPagerstatus.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        SharedPref.save(ConstClass.PAGER_TRANSAKSI_POS, "penawaran");
                        break;
                    case 1:
                        SharedPref.save(ConstClass.PAGER_TRANSAKSI_POS, "pending");
                        break;
                    case 2:
                        SharedPref.save(ConstClass.PAGER_TRANSAKSI_POS, "disetujui");
                        break;
                    case 3:
                        SharedPref.save(ConstClass.PAGER_TRANSAKSI_POS, "riwayat");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (SharedPref.getValueString(ConstClass.PAGER_TRANSAKSI_POS).equals("pending")){
            viewPagerstatus.setCurrentItem(1);
        }
        else if (SharedPref.getValueString(ConstClass.PAGER_TRANSAKSI_POS).equals("disetujui")){
            viewPagerstatus.setCurrentItem(2);
        }
        else if (SharedPref.getValueString(ConstClass.PAGER_TRANSAKSI_POS).equals("riwayat")){
            viewPagerstatus.setCurrentItem(3);
        }
        else{
            viewPagerstatus.setCurrentItem(0);
        }

        return _view;
    }
}
