package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mvp.mobile_art.R;
import com.mvp.mobile_art.View.Fragment.FragmentPenawaranAccepted;
import com.mvp.mobile_art.View.Fragment.FragmentPenawaranAll;
import com.mvp.mobile_art.View.Fragment.FragmentPenawaranHistory;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class PagerAdapterPenawaran extends FragmentPagerAdapter {
    private Context thiscontext;
    public PagerAdapterPenawaran(FragmentManager fm, Context context){
        super(fm);
        thiscontext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentPenawaranAll();
            case 1:
                return new FragmentPenawaranAccepted();
            case 2:
                return new FragmentPenawaranHistory();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return thiscontext.getResources().getString(R.string.pager_penawaran_all);
            case 1:
                return thiscontext.getResources().getString(R.string.pager_penawaran_accepted);
            case 2:
                return thiscontext.getResources().getString(R.string.pager_penawaran_history);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
