package com.TA.MVP.appmobilemember.Model.Array;

import java.util.ArrayList;

/**
 * Created by jcla123ns on 14/08/17.
 */

public class ArrayGender {
    private ArrayList<String> arrayList;
    public ArrayGender() {
        arrayList = new ArrayList<>();
//        arrayList.add("Semua");
        arrayList.add("Pria");
        arrayList.add("Wanita");
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public ArrayList<String> getArrayList2() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Semua");
        for (int i=0; i<arrayList.size(); i++){
            temp.add(arrayList.get(i));
        }
        return temp;
    }
}
