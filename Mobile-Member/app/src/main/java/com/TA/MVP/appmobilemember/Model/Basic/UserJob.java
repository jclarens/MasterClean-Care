package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class UserJob {
    private User user;
    private Integer job_id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }
}
