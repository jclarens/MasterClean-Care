package com.TA.MVP.appmobilemember.Model.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 07/07/2017.
 */

public class RecyclerAdapterPesan extends RecyclerView.Adapter<RecyclerAdapterPesan.ViewHolder> {
    private String[] nama = {"nama1", "nama2"};
    private String[] subject = {"sub1", "sub2"};
    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentitem;
        public ImageView itemimgfoto;
        public TextView itemnama,itemtanggal,itemsubject;
        public CheckBox itemcheckBox;

        public ViewHolder(final View itemview){
            super(itemview);
//            itemimgfoto = (ImageView) itemview.findViewById(R.id.card_pesan_img_foto);
            itemnama = (TextView) itemview.findViewById(R.id.card_pesan_nama);
            itemsubject = (TextView) itemview.findViewById(R.id.card_pesan_subject);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
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
//        holder.itemimgfoto.setImageResource(R.drawable.ic_status);
        holder.itemnama.setText(nama[position]);
        holder.itemsubject.setText(subject[position]);
    }

    @Override
    public int getItemCount() {
        return nama.length;
    }
}
