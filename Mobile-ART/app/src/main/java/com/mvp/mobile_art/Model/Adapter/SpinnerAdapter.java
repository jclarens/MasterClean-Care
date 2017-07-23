package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class SpinnerAdapter {
    private ArrayAdapter<String> arrayAdapter;

    public SpinnerAdapter(Context context, ArrayList<String> arrayList) {
        arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public ArrayAdapter<String> getArrayAdapter() {
        return arrayAdapter;
    }
}
