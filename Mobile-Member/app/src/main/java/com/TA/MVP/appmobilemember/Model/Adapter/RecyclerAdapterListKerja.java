package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 21/07/17.
 */

public class RecyclerAdapterListKerja extends RecyclerView.Adapter<RecyclerAdapterListKerja.ViewHolder> {
    private List<OrderTask> orderTasks = new ArrayList<>();//list semua pilihan default
    private List<OrderTask> orderTasksselected = new ArrayList<>();//list yang sudah terpilih
    class ViewHolder extends RecyclerView.ViewHolder{
        public int lightgreen, lightgrey;
        public CheckBox checkBox;
        public LinearLayout linearLayout;
        //itemview

        public ViewHolder(final View itemview){
            super(itemview);
            lightgreen = itemview.getResources().getColor(R.color.colorLightGreen);
            lightgrey = itemview.getResources().getColor(R.color.colorunselected);
            linearLayout = (LinearLayout) itemview.findViewById(R.id.card_listkerja_layout);
            checkBox = (CheckBox) itemview.findViewById(R.id.card_listkerja_item);
            final int position = getAdapterPosition();
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (checkBox.isChecked()){
                        orderTasksselected.add(orderTasks.get(position));
                        linearLayout.setBackgroundColor(lightgreen);
                    }
                    else {
                        orderTasksselected.remove(orderTasks.get(position));
                        linearLayout.setBackgroundColor(lightgrey);
                    }
                }
            });
            //view
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
        //holder set
    }

    @Override
    public int getItemCount() {
        return orderTasks.size();
    }

    public void setList(List<OrderTask> orderTasks){

        notifyDataSetChanged();
    }
}

