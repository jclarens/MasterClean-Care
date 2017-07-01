package com.TA.MVP.appmobilemember.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.TA.MVP.appmobilemember.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private ImageView imgmaininfo;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_home, container, false);
        return _view;
    }

}
