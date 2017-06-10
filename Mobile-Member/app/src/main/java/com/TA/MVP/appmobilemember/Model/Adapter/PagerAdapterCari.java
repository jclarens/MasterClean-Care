package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentCariList;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentCariMap;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class PagerAdapterCari extends FragmentPagerAdapter {
    private Context thiscontext;
    public PagerAdapterCari(FragmentManager fm, Context context){
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
                return new FragmentCariMap();
            case 1:
                return new FragmentCariList();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return thiscontext.getResources().getString(R.string.pageer_cari_map);
            case 1:
                return thiscontext.getResources().getString(R.string.pageer_cari_list);
            default:
                return null;
        }
    }
}
