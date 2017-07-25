package com.TA.MVP.appmobilemember.Model.Array;

import java.util.ArrayList;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class ArrayHari {
    private ArrayList<String> arrayList;
    public ArrayHari() {
        arrayList = new ArrayList<>();
//        arrayList.add("Semua");
        arrayList.add("Senin");
        arrayList.add("Selasa");
        arrayList.add("Rabu");
        arrayList.add("Kamis");
        arrayList.add("Jumat");
        arrayList.add("Sabtu");
        arrayList.add("Minggu");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
