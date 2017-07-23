package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.Wallet;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class WalletResponse extends Response {
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
