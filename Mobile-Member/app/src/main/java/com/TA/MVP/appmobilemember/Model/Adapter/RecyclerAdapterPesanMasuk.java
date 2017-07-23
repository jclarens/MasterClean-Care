package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.BacaPesanMasukActivity;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Zackzack on 07/07/2017.
 */

public class RecyclerAdapterPesanMasuk extends RecyclerView.Adapter<RecyclerAdapterPesanMasuk.ViewHolder> {
    private List<MyMessage> myMessages = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama,itemusia,itemsubject;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_pesan_nama);
//            itemusia = (TextView) itemview.findViewById(R.id.card_asis_umur);
            itemsubject = (TextView) itemview.findViewById(R.id.card_pesan_subject);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(itemview.getContext(), BacaPesanMasukActivity.class);
                    i.putExtra("msg", GsonUtils.getJsonFromObject(myMessages.get(position)));
                    itemview.getContext().startActivity(i);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pesan,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemnama.setText(myMessages.get(position).getSender().getName());
        holder.itemsubject.setText(myMessages.get(position).getSubject());
//        holder.itemnama.setText(nama[position]);
//        holder.itemsubject.setText(subject[position]);
    }

    @Override
    public int getItemCount() {
        return myMessages.size();
//        return nama.length;
    }

    public void setPesan(List<MyMessage> myMessages){
        this.myMessages = myMessages;
        notifyDataSetChanged();
    }
}
