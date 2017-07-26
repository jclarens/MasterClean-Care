package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by jcla123ns on 26/07/17.
 */

public class MyTask {
    private Integer Id;
    private Job job;
    private Integer Point;

    public Integer getId() {
        return Id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Integer getPoint() {
        return Point;
    }

    public void setPoint(Integer point) {
        Point = point;
    }
}
