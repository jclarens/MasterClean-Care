package com.TA.MVP.appmobilemember.Model.Basic;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.List;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class Order {
    private Integer id;
    private Integer member_id;
    private Integer art_id;
    private Integer work_time_id;
    private User member;
    private User art;
    private ReviewOrder review_order;
    private Waktu_Kerja work_time;
    private Integer cost;
    private String start_date;
    private String end_date;
    private String remark;
    private Integer status;
    private OrderContact contact;
    private List<OrderTask> order_task_list;

    public Integer getId() {
        return id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getArt_id() {
        return art_id;
    }

    public void setArt_id(Integer art_id) {
        this.art_id = art_id;
    }

    public Integer getWork_time_id() {
        return work_time_id;
    }

    public void setWork_time_id(Integer work_time_id) {
        this.work_time_id = work_time_id;
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

    public ReviewOrder getReview_order() {
        return review_order;
    }

    public void setReview_order(ReviewOrder review_order) {
        this.review_order = review_order;
    }

    public Waktu_Kerja getWork_time() {
        return work_time;
    }

    public void setWork_time(Waktu_Kerja work_time) {
        this.work_time = work_time;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
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

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }

    public List<OrderTask> getOrder_task_list() {
        return order_task_list;
    }

    public void setOrder_task_list(List<OrderTask> order_task_list) {
        this.order_task_list = order_task_list;
    }
}
