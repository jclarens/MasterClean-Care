package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.AsistenActivity;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class RecyclerAdapterAsisten extends RecyclerView.Adapter<RecyclerAdapterAsisten.ViewHolder> {
    private String[] nama = {"nama1", "nama2", "nama3", "nama4", "nama5"};
    private String[] umur = {"20 Thn", "25 Thn", "25 Thn", "25 Thn", "25 Thn"};
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama,itemumur;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_wallet_nominal);
            itemumur = (TextView) itemview.findViewById(R.id.card_asis_umur);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    doStartActivity(itemview.getContext(), AsistenActivity.class);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_asisten,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemnama.setText(nama[position]);
        holder.itemumur.setText(umur[position]);
    }

    @Override
    public int getItemCount() {
        return nama.length;
    }

    public static void doStartActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        context.startActivity(_intent);
    }
}
