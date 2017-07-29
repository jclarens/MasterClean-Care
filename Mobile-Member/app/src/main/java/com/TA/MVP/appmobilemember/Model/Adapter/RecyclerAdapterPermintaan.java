package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActiveActivity;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActiveActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 24/07/17.
 */

public class RecyclerAdapterPermintaan extends RecyclerView.Adapter<RecyclerAdapterPermintaan.ViewHolder> {
    private List<Offer> offers = new ArrayList<>();
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemjumlah, itemprofesi, itemmulai;

        public ViewHolder(final View itemview) {
            super(itemview);
            itemjumlah = (TextView) itemview.findViewById(R.id.jumlah_penerima);
            itemprofesi = (TextView) itemview.findViewById(R.id.work_time);
            itemmulai = (TextView) itemview.findViewById(R.id.waktu_mulai);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
//                    Toast.makeText(itemview.getContext(), "Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemview.getContext(), PermintaanActiveActivity.class);
                    i.putExtra(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offers.get(position)));
//                    itemview.getContext().startActivity(i);
                    ((MainActivity) context).startActivityForResult(i, MainActivity.REQUEST_OFFER);
                }
            });
        }
    }

    @Override
    public RecyclerAdapterPermintaan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_permintaan, parent, false);
        RecyclerAdapterPermintaan.ViewHolder viewHolder = new RecyclerAdapterPermintaan.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterPermintaan.ViewHolder holder, int position) {
        holder.itemjumlah.setText(String.valueOf(offers.get(position).getOffer_art().size()));
        holder.itemprofesi.setText(offers.get(position).getWork_time().getWork_time());
        holder.itemmulai.setText(offers.get(position).getStart_date());
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void setOffers(List<Offer> offers, Integer status) {
        List<Offer> temp = new ArrayList<>();
        for (int n = 0; n < offers.size(); n++) {
            if (offers.get(n).getStatus() == status)
                temp.add(offers.get(n));
        }
        this.offers = temp;
        notifyDataSetChanged();
    }

    public void setcontext(Context context) {
        this.context = context;
    }
}