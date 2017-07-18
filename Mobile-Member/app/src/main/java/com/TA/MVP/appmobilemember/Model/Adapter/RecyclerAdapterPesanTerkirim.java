package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Message;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.BacaPesanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 18/07/17.
 */

public class RecyclerAdapterPesanTerkirim extends RecyclerView.Adapter<RecyclerAdapterPesanTerkirim.ViewHolder> {
    //    private String[] nama = {"nama1", "nama2"};
//    private String[] subject = {"sub1", "sub2"};
    private List<Message> messages = new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama,itemtanggal,itemsubject;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_wallet_nominal);
            itemsubject = (TextView) itemview.findViewById(R.id.card_pesan_subject);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    doStartActivity(itemview.getContext(), BacaPesanActivity.class);

                }
            });
        }
    }

    @Override
    public RecyclerAdapterPesanTerkirim.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pesan,parent,false);
        RecyclerAdapterPesanTerkirim.ViewHolder viewHolder = new RecyclerAdapterPesanTerkirim.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterPesanTerkirim.ViewHolder holder, int position) {
//        holder.itemnama.setText(nama[position]);
//        holder.itemsubject.setText(subject[position]);
    }

    @Override
    public int getItemCount() {
        return messages.size();
//        return nama.length;
    }

    public static void doStartActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        context.startActivity(_intent);
    }
    public void setPesan(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }
}