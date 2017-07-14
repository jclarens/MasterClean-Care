package com.TA.MVP.appmobilemember.Model.Basic;

import java.util.Date;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class WalletTransaction {
    private User user;
    private Wallet wallet;
    private Integer trc_type;
    private Date trc_time;
    private String wallet_code;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Integer getTrc_type() {
        return trc_type;
    }

    public void setTrc_type(Integer trc_type) {
        this.trc_type = trc_type;
    }

    public Date getTrc_time() {
        return trc_time;
    }

    public void setTrc_time(Date trc_time) {
        this.trc_time = trc_time;
    }

    public String getWallet_code() {
        return wallet_code;
    }

    public void setWallet_code(String wallet_code) {
        this.wallet_code = wallet_code;
    }
}
