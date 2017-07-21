package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<OrderTask> orderTasks = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder{
        //itemview

        public ViewHolder(final View itemview){
            super(itemview);
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

