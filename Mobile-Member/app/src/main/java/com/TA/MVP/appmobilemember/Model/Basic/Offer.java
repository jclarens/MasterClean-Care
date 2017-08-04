package com.TA.MVP.appmobilemember.Model.Basic;

import java.util.Date;
import java.util.List;

/**
 * Created by Zackzack on 19/07/2017.
 */

public class Offer {
    private Integer id;
    private Integer member_id;
    private Integer work_time_id;
    private Integer job_id;
    private User member;
    private Waktu_Kerja work_time;
    private Job job;
    private Integer cost;
    private String start_date;
    private String end_date;
    private String remark;
    private Integer status;
    private OfferContact contact;
    private List<OrderTask> offer_task_list;
    private List<User> offer_art;

    public Integer getId() {
        return id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
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

    public List<User> getOffer_art() {
        return offer_art;
    }

    public void setOffer_art(List<User> offer_art) {
        this.offer_art = offer_art;
    }

    public OfferContact getContact() {
        return contact;
    }

    public void setContact(OfferContact contact) {
        this.contact = contact;
    }

    public List<OrderTask> getOffer_task_list() {
        return offer_task_list;
    }

    public void setOffer_task_list(List<OrderTask> offer_task_list) {
        this.offer_task_list = offer_task_list;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
