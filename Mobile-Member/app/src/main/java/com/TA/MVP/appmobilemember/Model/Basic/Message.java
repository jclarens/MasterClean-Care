package com.TA.MVP.appmobilemember.Model.Basic;

import android.content.Intent;
import android.support.annotation.IntegerRes;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class Message {
    private Integer id;
    private User sender;
    private User receiver;
    private String subject;
    private String message;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
