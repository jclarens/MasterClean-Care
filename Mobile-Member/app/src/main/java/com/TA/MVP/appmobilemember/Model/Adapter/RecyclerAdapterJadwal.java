package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class RecyclerAdapterJadwal extends RecyclerView.Adapter<RecyclerAdapterJadwal.ViewHolder> {
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private ArrayBulan arrayBulan = new ArrayBulan();
    private List<Order> orders = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout layoutjadwal;
        public TextView waktumulai, waktuselesai;
        public int colorwhite;

        public ViewHolder(final View itemview){
            super(itemview);
            colorwhite = itemview.getResources().getColor(R.color.colorWhite);
            waktumulai = (TextView) itemview.findViewById(R.id.card_jadwalasisten_tv1);
            waktuselesai = (TextView) itemview.findViewById(R.id.card_jadwalasisten_tv2);
            layoutjadwal = (LinearLayout) itemview.findViewById(R.id.card_jadwalasisten_layoutjadwal);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_jadwalasisten,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.waktumulai.setText(costumedateformat(getdateFormat.parse(orders.get(position).getStart_date())));
            holder.waktuselesai.setText(costumedateformat(getdateFormat.parse(orders.get(position).getEnd_date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (position % 2 == 1)
            holder.layoutjadwal.setBackgroundColor(holder.colorwhite);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    public void setOrders(List<Order> orders){
        List<Order> temporder = new ArrayList<>();
        for (int n=0;n<orders.size();n++){
            if (orders.get(n).getStatus() == 1){
                temporder.add(orders.get(n));
            }
        }
        this.orders = temporder;
        notifyDataSetChanged();
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date)) - 1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date) + "\n" + timeFormat.format(date);
    }
}
