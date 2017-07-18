package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zackzack on 18/07/2017.
 */

public class RecyclerAdapterLogWallet extends RecyclerView.Adapter<RecyclerAdapterLogWallet.ViewHolder> {
    private List<Wallet> wallets = new ArrayList<>();
//    private ArrayWallet wallets = new ArrayWallet();

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(final View itemview) {
            super(itemview);
        }
    }

    @Override
    public RecyclerAdapterLogWallet.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_wallet, parent, false);
        RecyclerAdapterLogWallet.ViewHolder viewHolder = new RecyclerAdapterLogWallet.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterLogWallet.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (wallets == null)
            return 0;
        else
            return wallets.size();
    }

    public void setLogWallets(List<Wallet> wallets) {
        this.wallets = wallets;
        notifyDataSetChanged();
    }
}