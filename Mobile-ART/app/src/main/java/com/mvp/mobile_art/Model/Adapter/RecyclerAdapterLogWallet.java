package com.mvp.mobile_art.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Array.ArrayBulan;
import com.mvp.mobile_art.Model.Basic.WalletTransaction;
import com.mvp.mobile_art.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zackzack on 18/07/2017.
 */

public class RecyclerAdapterLogWallet extends RecyclerView.Adapter<RecyclerAdapterLogWallet.ViewHolder> {
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private List<WalletTransaction> walletTransactions = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder {
        public int lightgreen, lightred, lightyellow, lightgrey;
        public TextView status, nominal, date;
        public LinearLayout cardwallettransactionlayout;
        public ViewHolder(final View itemview) {
            super(itemview);
            lightgreen = itemview.getResources().getColor(R.color.colorLightGreen);
            lightred = itemview.getResources().getColor(R.color.colorLightRed);
            lightyellow = itemview.getResources().getColor(R.color.colorLightYellow);
            lightgrey = itemview.getResources().getColor(R.color.colorLightGrey);
            status = (TextView) itemview.findViewById(R.id.card_wallettransaction_status);
            nominal = (TextView) itemview.findViewById(R.id.card_wallettransaction_nominal);
            date = (TextView) itemview.findViewById(R.id.card_wallettransaction_date);
            cardwallettransactionlayout = (LinearLayout) itemview.findViewById(R.id.card_wallettransaction_layout);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_wallettransaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (walletTransactions.get(position).getTrc_type() == 0){//uang masuk
            holder.status.setText("+ ");
            if (walletTransactions.get(position).getStatus() == 0){//masuk pending
                holder.cardwallettransactionlayout.setBackgroundColor(holder.lightyellow);
                //tambah text
            }else if (walletTransactions.get(position).getStatus() == 1){//masuk berhasil
                holder.cardwallettransactionlayout.setBackgroundColor(holder.lightgreen);
            }else if(walletTransactions.get(position).getStatus() == 2){
                holder.cardwallettransactionlayout.setBackgroundColor(holder.lightgrey);
            }
        }
        else if (walletTransactions.get(position).getTrc_type() == 1){//uang keluar
            holder.status.setText("- ");
            if (walletTransactions.get(position).getStatus() == 0){//keluar pending
                holder.cardwallettransactionlayout.setBackgroundColor(holder.lightyellow);
            }else if (walletTransactions.get(position).getStatus() == 1){//keluar berhasil
                holder.cardwallettransactionlayout.setBackgroundColor(holder.lightred);
            }else if(walletTransactions.get(position).getStatus() == 2){
                holder.cardwallettransactionlayout.setBackgroundColor(holder.lightgrey);
            }
        }
        holder.nominal.setText(setRP(walletTransactions.get(position).getAmount()));
        try{
            holder.date.setText(costumedateformat(getdateFormat.parse(walletTransactions.get(position).getTrc_time())) + " "
                    + timeFormat.format(getdateFormat.parse(walletTransactions.get(position).getTrc_time())));
        }catch (ParseException e) {

        }
    }

    @Override
    public int getItemCount() {
        if (walletTransactions == null)
            return 0;
        return walletTransactions.size();
    }

    public void setLogWallets(List<WalletTransaction> walletTransactions) {
        this.walletTransactions = walletTransactions;
        doshorting();
        notifyDataSetChanged();
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date)) - 1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void doshorting(){
        Collections.sort(walletTransactions, new Comparator<WalletTransaction>(){
            public int compare(WalletTransaction obj1, WalletTransaction obj2) {
                return obj2.getTrc_time().compareToIgnoreCase(obj1.getTrc_time());
            }
        });
    }
}