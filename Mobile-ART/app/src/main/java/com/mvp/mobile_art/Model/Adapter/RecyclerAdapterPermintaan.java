package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.Waktu_Kerja;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.View.Activity.OfferActivity;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 24/07/17.
 */

public class RecyclerAdapterPermintaan extends RecyclerView.Adapter<RecyclerAdapterPermintaan.ViewHolder> {
    private List<Offer> offers = new ArrayList<>();
    private Context context;
    private String[] status = {"Pending", "Diterima", "Ditolak", "Dibatalkan"};
    private Integer mystatus;
    private List<Waktu_Kerja> defaultwk;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemstatus, itemprofesi, itemmulai;

        public ViewHolder(final View itemview) {
            super(itemview);
            itemstatus = (TextView) itemview.findViewById(R.id.status);
            itemprofesi = (TextView) itemview.findViewById(R.id.work_time);
            itemmulai = (TextView) itemview.findViewById(R.id.waktu_mulai);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
//                    Toast.makeText(itemview.getContext(), "Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemview.getContext(), OfferActivity.class);
                    i.putExtra(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offers.get(position)));
//                    itemview.getContext().startActivity(i);
                    ((MainActivity) context).startActivityForResult(i, MainActivity.REQUEST_OFFER);
                }
            });
        }
    }

    @Override
    public RecyclerAdapterPermintaan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_offer, parent, false);
        RecyclerAdapterPermintaan.ViewHolder viewHolder = new RecyclerAdapterPermintaan.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterPermintaan.ViewHolder holder, int position) {
        if (offers.get(position).getStatus() == 0){
            holder.itemstatus.setText(status[0]);
        } else if (offers.get(position).getStatus() == 1){
            if (mystatus == 0){
                holder.itemstatus.setText(status[2]);
            }
            else if (mystatus == 1){
                holder.itemstatus.setText(status[1]);
            }
        } else if (offers.get(position).getStatus() == 2){
            holder.itemstatus.setText(status[3]);
        }
        holder.itemprofesi.setText(defaultwk.get(offers.get(position).getWork_time_id()-1).getWork_time());
        holder.itemmulai.setText(offers.get(position).getStart_date());
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void setOffers(List<Offer> offers, Integer id) {
        this.offers = offers;
        for (int n=0;n<offers.size();n++){//bug api tidak lengkap return
            for (int m=0;m<offers.get(n).getOffer_art().size();m++){
                if (offers.get(n).getOffer_art().get(m).getId().equals(id)) {
                    mystatus = offers.get(n).getOffer_art().get(m).getStatus();
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setcontext(Context context) {
        this.context = context;
    }
    public void setDefaultwk(List<Waktu_Kerja> defaultwk){
        this.defaultwk = defaultwk;
    }
}