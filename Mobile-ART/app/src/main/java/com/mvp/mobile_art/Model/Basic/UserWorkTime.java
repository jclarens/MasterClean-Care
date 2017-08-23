package com.mvp.mobile_art.Model.Basic;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class UserWorkTime {
    private User user;
    private Integer user_id;
    private Integer work_time_id;
    private Integer cost;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getWork_time_id() {
        return work_time_id;
    }

    public void setWork_time_id(Integer work_time_id) {
        this.work_time_id = work_time_id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
