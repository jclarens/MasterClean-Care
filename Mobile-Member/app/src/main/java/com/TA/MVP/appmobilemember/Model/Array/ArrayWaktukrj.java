package com.TA.MVP.appmobilemember.Model.Array;

import java.util.ArrayList;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArrayWaktukrj {
    private ArrayList<String> arrayList;
    public ArrayWaktukrj() {
        arrayList = new ArrayList<String>();
        arrayList.add("Jam");
        arrayList.add("Hari");
        arrayList.add("Bulan");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
