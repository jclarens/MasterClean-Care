package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPesanMasuk;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPesanTerkirim;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class PagerAdapterPesan extends FragmentPagerAdapter {
    private Context thiscontext;
    public PagerAdapterPesan(FragmentManager fm, Context context){
        super(fm);
        thiscontext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentPesanMasuk();
            case 1:
                return new FragmentPesanTerkirim();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return thiscontext.getResources().getString(R.string.pager_pesan_masuk);
            case 1:
                return thiscontext.getResources().getString(R.string.pager_pesan_terkirim);
            default:
                return null;
        }
    }

}
