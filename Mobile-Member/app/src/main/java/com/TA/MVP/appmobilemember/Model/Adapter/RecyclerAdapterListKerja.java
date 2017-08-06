package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPemesanan2;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 21/07/17.
 */

public class RecyclerAdapterListKerja extends RecyclerView.Adapter<RecyclerAdapterListKerja.ViewHolder> {
    private List<MyTask> fullTasks = new ArrayList<>();//list semua pilihan default
    private List<MyTask> showTasks = new ArrayList<>();//list sesuai profefsi
    private List<MyTask> selectedTasks = new ArrayList<>();//list yang sudah terpilih
    private FragmentPemesanan2 fragment;

    public RecyclerAdapterListKerja(FragmentPemesanan2 fragment){
        this.fragment = fragment;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int darkergrey, lightgrey;
        public CheckBox checkBox;
        public LinearLayout linearLayout;

        public ViewHolder(View itemview){
            super(itemview);
            darkergrey = itemview.getResources().getColor(R.color.colorDarkerGrey);
            lightgrey = itemview.getResources().getColor(R.color.colorunselected);
            linearLayout = (LinearLayout) itemview.findViewById(R.id.card_listkerja_layout);
            checkBox = (CheckBox) itemview.findViewById(R.id.card_listkerja_item);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    if (checkBox.isChecked()){
                        selectedTasks.add(showTasks.get(position));
//                        Log.d("Selectedid - showid",selectedTasks.get(0).getId() + " - "+ showTasks.get(0).getId() + " - "+ fullTasks.get(0).getId());
                        linearLayout.setBackgroundColor(darkergrey);
                    }
                    else {
                        selectedTasks.remove(showTasks.get(position));
                        linearLayout.setBackgroundColor(lightgrey);
                    }
                    fragment.setbobot();
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

    public void setFullTasks(List<MyTask> fulltasks){
        this.fullTasks = fulltasks;
        notifyDataSetChanged();
    }

    public void setshowtask(Integer integer){
        showTasks.clear();
        clearselectedlist();
        for (int n=0; n<fullTasks.size();n++){
            if (fullTasks.get(n).getJob_id().getId().equals(integer))
                showTasks.add(fullTasks.get(n));
        }
        notifyDataSetChanged();
    }
    public List<MyTask> getselectedtasklist(){
        return selectedTasks;
    }
    public void clearselectedlist(){
        selectedTasks.clear();
    }
    public void addallshown(){
        selectedTasks.add(showTasks.get(0));
    }
}

