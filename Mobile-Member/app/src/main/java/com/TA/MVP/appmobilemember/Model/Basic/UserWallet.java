package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class UserWallet {
    private User user;
    private Integer amount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
