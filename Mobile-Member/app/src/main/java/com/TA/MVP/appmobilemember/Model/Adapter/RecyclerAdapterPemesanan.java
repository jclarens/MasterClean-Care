package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActiveActivity;

import java.util.List;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class RecyclerAdapterPemesanan extends RecyclerView.Adapter<RecyclerAdapterPemesanan.ViewHolder> {
    private String[] nama = {"nama1", "nama2", "nama3", "nama4", "nama5"};
    private String[] profesi = {"Nanny", "Baby Sitter", "Nanny", "Baby Sitter", "Pengurus Rumah Tangga"};
    private String[] mulai = {"01 Januari 2017", "01 Januari 2017", "01 Januari 2017", "01 Januari 2017", "01 Januari 2017"};
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama, itemprofesi, itemmulai;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_pms_nama);
            itemprofesi = (TextView) itemview.findViewById(R.id.card_pms_prof);
            itemmulai = (TextView) itemview.findViewById(R.id.card_pms_mulai);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    doStartActivity(itemview.getContext(), PemesananActiveActivity.class);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pemesanan,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemnama.setText(nama[position]);
        holder.itemprofesi.setText(profesi[position]);
        holder.itemmulai.setText(mulai[position]);
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
