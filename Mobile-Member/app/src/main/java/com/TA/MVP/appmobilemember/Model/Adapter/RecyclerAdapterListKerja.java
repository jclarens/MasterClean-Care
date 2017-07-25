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
    private List<OrderTask> fullTasks = new ArrayList<>();//list semua pilihan default
    private List<OrderTask> showTasks = new ArrayList<>();//list sesuai profefsi
    private List<OrderTask> selectedTasks = new ArrayList<>();//list yang sudah terpilih
    class ViewHolder extends RecyclerView.ViewHolder{
        public int lightgreen, lightgrey;
        public CheckBox checkBox;
        public LinearLayout linearLayout;

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
                        selectedTasks.add(showTasks.get(position));
                        linearLayout.setBackgroundColor(lightgreen);
                    }
                    else {
                        selectedTasks.remove(showTasks.get(position));
                        linearLayout.setBackgroundColor(lightgrey);
                    }
                }
            });
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
        //set nama task
        holder.checkBox.setText(showTasks.get(position).getTask());
    }

    @Override
    public int getItemCount() {
        return showTasks.size();
    }

    public void setFullTasks(List<OrderTask> fulltasks, Integer integer){
        this.fullTasks = fulltasks;
        setshowtask(integer);
    }
    public void setshowtask(Integer integer){
        showTasks.clear();
        selectedTasks.clear();
        for (int n=0; n<fullTasks.size();n++){
            if (fullTasks.get(n).getId() == integer)
                showTasks.add(fullTasks.get(n));
        }
        notifyDataSetChanged();
    }
    public List<OrderTask> getselectedtasklist(){
        return selectedTasks;
    }
}

