package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.WalletTransaction;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class WalletTransactionResponse extends Response {
    private WalletTransaction walletTransaction;

    public WalletTransaction getWalletTransaction() {
        return walletTransaction;
    }

    public void setWalletTransaction(WalletTransaction walletTransaction) {
        this.walletTransaction = walletTransaction;
    }
}
