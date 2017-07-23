package com.mvp.mobile_art.Model.Basic;

/**
 * Created by jcla123ns on 21/07/17.
 */

public class UserOrderRate {
    private Integer id;
    private Integer order_id;
    private Integer art_id;
    private float rate;
    private String remark;
    private String created_at;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public Integer getArt_id() {
        return art_id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
