package com.mvp.mobile_art.Model.Array;

import java.util.ArrayList;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArraySuku {
    private ArrayList<String> arrayList;
    public ArraySuku() {
        arrayList = new ArrayList<String>();
        arrayList.add("Semua");
        arrayList.add("Jawa");
        arrayList.add("Padang");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
