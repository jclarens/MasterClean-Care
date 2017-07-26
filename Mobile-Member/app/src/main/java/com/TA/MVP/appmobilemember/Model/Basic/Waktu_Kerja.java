package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 09/07/2017.
 */

public class Waktu_Kerja {
    private Integer id;
    private String work_time;

    public Integer getId() {
        return id;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String toString() {
        return (work_time);
    }
}