package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class Place {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String toString(){
        return name;
    }
}
