package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class RecyclerAdapterListKerjaShow extends RecyclerView.Adapter<RecyclerAdapterListKerjaShow.ViewHolder> {
    private List<OrderTask> taskList = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;

        public ViewHolder(final View itemview){
            super(itemview);
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
        //set nama task
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setList(List<OrderTask> tasklist){
        this.taskList = tasklist;
    }
}
