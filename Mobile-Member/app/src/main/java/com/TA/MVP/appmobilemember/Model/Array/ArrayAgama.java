package com.TA.MVP.appmobilemember.Model.Array;

import java.util.ArrayList;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArrayAgama {
    private ArrayList<String> arrayList;
    public ArrayAgama() {
        arrayList = new ArrayList<>();
//        arrayList.add("Semua");
        arrayList.add("Islam");
        arrayList.add("Kristen Protestan");
        arrayList.add("Kristen Katolik");
        arrayList.add("Hindu");
        arrayList.add("Buddha");
        arrayList.add("Konghucu");
        arrayList.add("Atheist");
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
