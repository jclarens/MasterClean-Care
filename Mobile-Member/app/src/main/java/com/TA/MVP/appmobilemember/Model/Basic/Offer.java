package com.TA.MVP.appmobilemember.Model.Basic;

import java.util.Date;

/**
 * Created by Zackzack on 19/07/2017.
 */

public class Offer {
    private Integer id;
    private User member;
    private Integer cost;
    private Waktu_Kerja work_time;
    private Date start_date;
    private Date end_date;
    private String remark;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Waktu_Kerja getWork_time() {
        return work_time;
    }

    public void setWork_time(Waktu_Kerja work_time) {
        this.work_time = work_time;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
