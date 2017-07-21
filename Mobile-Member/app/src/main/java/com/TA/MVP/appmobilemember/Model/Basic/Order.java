package com.TA.MVP.appmobilemember.Model.Basic;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.List;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class Order {
    private Integer id;
    private User member;
    private User art;
    private Integer cost;
    private Waktu_Kerja work_time;
    private String start_date;
    private String end_date;
    private String remark;
    private Integer status;
    private List<ReviewOrder> review_order;
    private List<Contact> contact;

    public Integer getId() {
        return id;
    }

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

    public List<ReviewOrder> getReview_order() {
        return review_order;
    }

    public void setReview_order(List<ReviewOrder> review_order) {
        this.review_order = review_order;
    }

    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
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
