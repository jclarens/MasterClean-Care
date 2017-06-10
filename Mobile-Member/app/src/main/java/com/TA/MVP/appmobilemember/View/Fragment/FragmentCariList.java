package com.TA.MVP.appmobilemember.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 10/06/2017.
 */

public class FragmentCariList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_cari_list, container, false);
        return _view;
    }
}
