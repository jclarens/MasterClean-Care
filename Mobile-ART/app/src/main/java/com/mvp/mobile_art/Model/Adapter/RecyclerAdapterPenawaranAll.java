package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvp.mobile_art.Model.Array.ArrayBulan;
import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.Waktu_Kerja;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.View.Activity.OfferActivity;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class RecyclerAdapterPenawaranAll extends RecyclerView.Adapter<RecyclerAdapterPenawaranAll.ViewHolder> {
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private ArrayBulan arrayBulan = new ArrayBulan();
    private List<Offer> offers = new ArrayList<>();
    private Context context;
    private List<Waktu_Kerja> defaultwk;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemhonor, itemprofesi, itemmulai, itemselesai;

        public ViewHolder(final View itemview) {
            super(itemview);
            itemhonor = (TextView) itemview.findViewById(R.id.honor);
            itemprofesi = (TextView) itemview.findViewById(R.id.work_time);
            itemmulai = (TextView) itemview.findViewById(R.id.waktu_mulai);
            itemselesai = (TextView) itemview.findViewById(R.id.waktu_selesai);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(itemview.getContext(), OfferActivity.class);
                    i.putExtra(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offers.get(position)));
                    ((MainActivity) context).startActivityForResult(i, MainActivity.REQUEST_OFFER);
                }
            });
        }
    }

    @Override
    public RecyclerAdapterPenawaranAll.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_penawaran_all, parent, false);
        RecyclerAdapterPenawaranAll.ViewHolder viewHolder = new RecyclerAdapterPenawaranAll.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterPenawaranAll.ViewHolder holder, int position) {
        holder.itemhonor.setText(setRP(offers.get(position).getCost()));
        holder.itemprofesi.setText(defaultwk.get(offers.get(position).getWork_time_id()-1).getWork_time());
        try {
            holder.itemmulai.setText(costumedateformat(getdateFormat.parse(offers.get(position).getStart_date())));
            holder.itemselesai.setText(costumedateformat(getdateFormat.parse(offers.get(position).getEnd_date())));
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
    public void setDefaultwk(List<Waktu_Kerja> defaultwk){
        this.defaultwk = defaultwk;
    }
    public void doshorting(){
        Collections.sort(offers, new Comparator<Offer>(){
            public int compare(Offer obj1, Offer obj2) {
                return obj2.getStart_date().compareToIgnoreCase(obj1.getStart_date());
            }
        });
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date) + " " + timeFormat.format(date);
    }
}