package com.mvp.mobile_art.Model.Array;

import java.util.ArrayList;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArrayKota {
    private ArrayList<String> arrayList;
    public ArrayKota() {
        arrayList = new ArrayList<String>();
        arrayList.add("Medan");
        arrayList.add("kota1");
        arrayList.add("kota2");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
