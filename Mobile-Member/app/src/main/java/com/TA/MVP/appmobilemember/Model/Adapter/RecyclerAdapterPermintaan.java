package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActiveActivity;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActiveActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jcla123ns on 24/07/17.
 */

public class RecyclerAdapterPermintaan extends RecyclerView.Adapter<RecyclerAdapterPermintaan.ViewHolder> {
    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private ArrayBulan arrayBulan = new ArrayBulan();
    private List<Offer> offers = new ArrayList<>();
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemjumlah, itemprofesi, itemmulai, itemjob;

        public ViewHolder(final View itemview) {
            super(itemview);
            itemjumlah = (TextView) itemview.findViewById(R.id.jumlah_penerima);
            itemprofesi = (TextView) itemview.findViewById(R.id.work_time);
            itemjob = (TextView) itemview.findViewById(R.id.job);
            itemmulai = (TextView) itemview.findViewById(R.id.waktu_mulai);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(itemview.getContext(), PermintaanActiveActivity.class);
                    i.putExtra(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offers.get(position)));
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
        holder.itemjob.setText(offers.get(position).getJob().getJob());
        try {
            holder.itemmulai.setText(costumedateformat(fixFormat.parse(offers.get(position).getStart_date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
        doshorting();
        notifyDataSetChanged();
    }

    public void setcontext(Context context) {
        this.context = context;
    }
    public void doshorting(){
        Collections.sort(offers, new Comparator<Offer>(){
            public int compare(Offer obj1, Offer obj2) {
                return obj2.getStart_date().compareToIgnoreCase(obj1.getStart_date());
            }
        });
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date) + " " + timeFormat.format(date);
    }
}