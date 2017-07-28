package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPemesanan2;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentPermintaan2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 21/07/17.
 */

public class RecyclerAdapterListKerja2 extends RecyclerView.Adapter<RecyclerAdapterListKerja2.ViewHolder> {
    private List<MyTask> fullTasks = new ArrayList<>();//list semua pilihan default
    private List<MyTask> showTasks = new ArrayList<>();//list sesuai profefsi
    private List<MyTask> selectedTasks = new ArrayList<>();//list yang sudah terpilih
    private FragmentPermintaan2 fragment;

    public RecyclerAdapterListKerja2(FragmentPermintaan2 fragment){
        this.fragment = fragment;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public int lightgreen, lightgrey;
        public CheckBox checkBox;
        public LinearLayout linearLayout;

        public ViewHolder(View itemview){
            super(itemview);
            lightgreen = itemview.getResources().getColor(R.color.colorLightGreen);
            lightgrey = itemview.getResources().getColor(R.color.colorunselected);
            linearLayout = (LinearLayout) itemview.findViewById(R.id.card_listkerja_layout);
            checkBox = (CheckBox) itemview.findViewById(R.id.card_listkerja_item);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();
                    if (checkBox.isChecked()){
                        selectedTasks.add(showTasks.get(position));
                        linearLayout.setBackgroundColor(lightgreen);
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
    public List<MyTask> getselectedtasklist(){
        return selectedTasks;
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
    public void clearselectedlist(){
        selectedTasks = new ArrayList<>();
    }
}

