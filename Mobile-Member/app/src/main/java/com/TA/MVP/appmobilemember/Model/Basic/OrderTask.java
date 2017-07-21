package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class OrderTask {
    private Integer id;
    private Order order;
    private String task;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
