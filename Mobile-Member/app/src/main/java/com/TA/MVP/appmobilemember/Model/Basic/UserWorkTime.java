package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class UserWorkTime {
    private User user;
    private Integer work_time_id;
    private Waktu_Kerja work_time;
    private Integer cost;

    public Integer getWork_time_id() {
        return work_time_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Waktu_Kerja getWork_time() {
        return work_time;
    }

    public void setWork_time(Waktu_Kerja work_time) {
        this.work_time = work_time;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
