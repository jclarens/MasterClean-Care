package com.TA.MVP.appmobilemember.Model.Array;

import java.util.ArrayList;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class ArrayBulan {
    private ArrayList<String> arrayList;
    public ArrayBulan() {
        arrayList = new ArrayList<>();
//        arrayList.add("Semua");
        arrayList.add("Januari");
        arrayList.add("Februari");
        arrayList.add("Maret");
        arrayList.add("April");
        arrayList.add("Mei");
        arrayList.add("Juni");
        arrayList.add("July");
        arrayList.add("September");
        arrayList.add("Oktober");
        arrayList.add("November");
        arrayList.add("Desember");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
