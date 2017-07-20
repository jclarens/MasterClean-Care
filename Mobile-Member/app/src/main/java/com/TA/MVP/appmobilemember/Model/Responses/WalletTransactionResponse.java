package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.WalletTransaction;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
