package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class RecyclerAdapterListKerjaShow extends RecyclerView.Adapter<RecyclerAdapterListKerjaShow.ViewHolder> {
    private List<MyTask> defaulttask = new ArrayList<>();
    private List<OrderTask> orderTasks = new ArrayList<>();
    private int black;

    class ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
//        public TextView textView;
        public ViewHolder(final View itemview){
            super(itemview);
            black = itemview.getResources().getColor(R.color.colorBlack);
            checkBox = (CheckBox) itemview.findViewById(R.id.card_listkerja_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_listkerja,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.checkBox.setEnabled(false);
//        holder.textView.setTextColor(black);
        Log.d("Holder tasklistid =", orderTasks.get(position).getTask_list_id().toString());
        holder.checkBox.setText(defaulttask.get(orderTasks.get(position).getTask_list_id()-1).getTask());

        if (orderTasks.get(position).getStatus().equals(1)){
            holder.checkBox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return orderTasks.size();
    }

    public void setList(List<OrderTask> orderTasks){
        this.orderTasks = orderTasks;
        notifyDataSetChanged();
    }

    public void setDefaulttask(List<MyTask> defaulttask){
        this.defaulttask = defaulttask;
    }
}
