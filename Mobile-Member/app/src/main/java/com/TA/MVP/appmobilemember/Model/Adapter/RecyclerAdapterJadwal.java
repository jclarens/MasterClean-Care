package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class RecyclerAdapterJadwal extends RecyclerView.Adapter<RecyclerAdapterJadwal.ViewHolder> {
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
        holder.waktumulai.setText(orders.get(position).getStart_date().toString());
        holder.waktuselesai.setText(orders.get(position).getEnd_date().toString());
        if (position % 2 == 1)
            holder.layoutjadwal.setBackgroundColor(holder.colorwhite);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    public void setOrders(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }
}
