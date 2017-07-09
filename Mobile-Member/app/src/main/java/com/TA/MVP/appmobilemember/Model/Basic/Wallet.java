package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 09/07/2017.
 */

public class Wallet {
    private String id;
    private Integer amt;
    private Integer price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
