package com.mvp.mobile_art.Model.Basic;

/**
 * Created by jcla123ns on 26/07/17.
 */

public class MyTask {
    private Integer id;
    private Job job_id;
    private String task;
    private Integer point;

    public Integer getId() {
        return id;
    }

    public Job getJob_id() {
        return job_id;
    }

    public void setJob_id(Job job_id) {
        this.job_id = job_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
