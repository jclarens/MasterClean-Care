package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 09/07/2017.
 */

public class Job {
    private Integer id;
    private String job;

    public Integer getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String toString(){
        return (job);
    }
}
