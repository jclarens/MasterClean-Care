package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class ReviewOrder {
    private Integer id;
    private Integer order_id;
    private Order order;
    private Float rate;
    private String remark;
    private String created_at;

    public Integer getId() {
        return id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
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
}
