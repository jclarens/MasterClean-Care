package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.Model.Basic.WalletTransaction;
import com.TA.MVP.appmobilemember.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zackzack on 18/07/2017.
 */

public class RecyclerAdapterLogWallet extends RecyclerView.Adapter<RecyclerAdapterLogWallet.ViewHolder> {
    private List<WalletTransaction> walletTransactions = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder {
        public int lightgreen, lightred;
        public TextView status, nominal, date;
        public LinearLayout cardwallettransactionlayout;
        public ViewHolder(final View itemview) {
            super(itemview);
            lightgreen = itemview.getResources().getColor(R.color.colorLightGreen);
            lightred = itemview.getResources().getColor(R.color.colorLightRed);
            status = (TextView) itemview.findViewById(R.id.card_wallettransaction_status);
            nominal = (TextView) itemview.findViewById(R.id.card_wallettransaction_nominal);
            date = (TextView) itemview.findViewById(R.id.card_wallettransaction_date);
            cardwallettransactionlayout = (LinearLayout) itemview.findViewById(R.id.card_wallettransaction_layout);
        }

    }

    @Override
    public RecyclerAdapterLogWallet.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_wallettransaction, parent, false);
        RecyclerAdapterLogWallet.ViewHolder viewHolder = new RecyclerAdapterLogWallet.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterLogWallet.ViewHolder holder, int position) {
        if (walletTransactions.get(position).getTrc_type() == 1){//bertambah
            holder.cardwallettransactionlayout.setBackgroundColor(holder.lightgreen);
            holder.status.setText("+ Rp.");
        }
        else{
            holder.cardwallettransactionlayout.setBackgroundColor(holder.lightred);
            holder.status.setText("- Rp.");
        }

        holder.nominal.setText(walletTransactions.get(position).getAmount());
        holder.date.setText(walletTransactions.get(position).getTrc_time().toString());
    }

    @Override
    public int getItemCount() {
        return walletTransactions.size();
    }

    public void setLogWallets(List<WalletTransaction> walletTransactions) {
        this.walletTransactions = walletTransactions;
        notifyDataSetChanged();
    }
}