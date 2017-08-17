package com.TA.MVP.appmobilemember.Model.Array;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class FilterArrays {
    private ArrayKota arrayKota;
    private ArrayAgama arrayAgama;
    private  ArrayProfesi arrayProfesi;
    private ArraySuku arraySuku;
    private ArrayWaktukrj arrayWaktukrj;
    private ArrayGender arrayGender;

    public FilterArrays() {
        arrayAgama = new ArrayAgama();
        arrayGender = new ArrayGender();
        arrayKota = new ArrayKota();
        arrayProfesi = new ArrayProfesi();
        arraySuku = new ArraySuku();
        arrayWaktukrj = new ArrayWaktukrj();
    }

    public ArrayGender getArrayGender() {
        return arrayGender;
    }

    public ArrayKota getArrayKota() {
        return arrayKota;
    }

    public ArrayAgama getArrayAgama() {
        return arrayAgama;
    }

    public ArrayProfesi getArrayProfesi() {
        return arrayProfesi;
    }

    public ArraySuku getArraySuku() {
        return arraySuku;
    }

    public ArrayWaktukrj getArrayWaktukrj() {
        return arrayWaktukrj;
    }
}
