package com.mvp.mobile_art.Model.Basic;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class OfferTask {
    private Integer id;
    private Offer offer;
    private String task;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
