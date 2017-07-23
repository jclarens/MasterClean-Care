package com.mvp.mobile_art.Model.Basic;

import java.util.Date;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class Emergencycall {
    private User user;
    private Date init_time;
    private Integer status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInit_time() {
        return init_time;
    }

    public void setInit_time(Date init_time) {
        this.init_time = init_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
