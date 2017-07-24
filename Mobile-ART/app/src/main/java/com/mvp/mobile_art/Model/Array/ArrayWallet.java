package com.mvp.mobile_art.Model.Array;

import com.mvp.mobile_art.Model.Basic.Wallet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class ArrayWallet {
    public List<Wallet> wallets;

    public List<Wallet> getWallets() {
        return wallets;
    }

    public ArrayWallet() {
        this.wallets = new ArrayList<>();
        addWallet(50000,55000);
        addWallet(100000,105000);
        addWallet(200000,205000);
        addWallet(500000,505000);
        addWallet(1000000,1000000);
    }
    public void addWallet(Integer amt, Integer price){
        Wallet wallet= new Wallet();
        wallet.setAmt(amt);
        wallet.setPrice(price);
        wallets.add(wallet);
    }
}
