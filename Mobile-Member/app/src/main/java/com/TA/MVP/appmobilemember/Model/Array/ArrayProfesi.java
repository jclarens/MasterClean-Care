package com.TA.MVP.appmobilemember.Model.Array;

import java.util.ArrayList;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArrayProfesi {
    private ArrayList<String> arrayList;
    public ArrayProfesi() {
        arrayList = new ArrayList<String>();
        arrayList.add("Pengurus Rumah Tangga");
        arrayList.add("Baby Sitter");
        arrayList.add("Nanny");
        arrayList.add("Perawat Lansia");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}
