package com.TA.MVP.appmobilemember.Model.Basic;

import android.content.Intent;
import android.support.annotation.IntegerRes;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class MyMessage {
    private Integer id;
    private User sender_id;
    private User receiver_id;
    private String subject;
    private String message;
    private Integer status_member;
    private Integer status_art;
    private String created_at;
    private String update_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public Integer getId() {
        return id;
    }

    public User getSender_id() {
        return sender_id;
    }

    public void setSender_id(User sender_id) {
        this.sender_id = sender_id;
    }

    public User getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(User receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_member() {
        return status_member;
    }

    public void setStatus_member(Integer status_member) {
        this.status_member = status_member;
    }

    public Integer getStatus_art() {
        return status_art;
    }

    public void setStatus_art(Integer status_art) {
        this.status_art = status_art;
    }
}
