package com.TA.MVP.appmobilemember.Model.Basic;

import java.util.Date;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class Order {
    private User member;
    private User art;
    private Integer work_time_id;
    private Date start_date;
    private Date end_date;
    private Integer province;
    private Integer city;
    private String address;
    private String location;
    private String remark;
    private Integer status;

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public User getArt() {
        return art;
    }

    public void setArt(User art) {
        this.art = art;
    }

    public Integer getWork_time_id() {
        return work_time_id;
    }

    public void setWork_time_id(Integer work_time_id) {
        this.work_time_id = work_time_id;
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

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
