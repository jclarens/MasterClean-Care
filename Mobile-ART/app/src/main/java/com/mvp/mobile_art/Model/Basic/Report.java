package com.mvp.mobile_art.Model.Basic;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class Report {
    private Integer id;
    private Integer user_id;
    private Integer reporter_id;
    private User user;
    private User reporter;
    private String remark;

    public Integer getId() {
        return id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(Integer reporter_id) {
        this.reporter_id = reporter_id;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
}
