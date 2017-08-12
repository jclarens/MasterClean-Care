package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvp.mobile_art.Model.Array.ArrayBulan;
import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.View.Activity.PemesananActiveActivity;
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
 * Created by jcla123ns on 31/07/17.
 */

public class RecyclerAdapterRiwayat extends RecyclerView.Adapter<RecyclerAdapterRiwayat.ViewHolder> {
    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private List<Order> orders = new ArrayList<>();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private Context context;
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama, itemprofesi, itemmulai, itemstatus;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_pms_nama);
            itemprofesi = (TextView) itemview.findViewById(R.id.card_pms_prof);
            itemmulai = (TextView) itemview.findViewById(R.id.card_pms_mulai);
            itemstatus = (TextView) itemview.findViewById(R.id.card_pms_status);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
//                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemview.getContext(), PemesananActiveActivity.class);
                    i.putExtra(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(orders.get(position)));
//                    itemview.getContext().startActivity(i);
                    ((MainActivity)context).startActivityForResult(i, MainActivity.REQUEST_ORDER);
                }
            });
        }
    }

    @Override
    public RecyclerAdapterRiwayat.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pemesanan_riwayat,parent,false);
        RecyclerAdapterRiwayat.ViewHolder viewHolder = new RecyclerAdapterRiwayat.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterRiwayat.ViewHolder holder, int position) {
        switch (orders.get(position).getStatus()){
            case 2:
                holder.itemstatus.setText("Dibatalkan");
                break;
            case 3:
                holder.itemstatus.setText("Selesai");
                break;
            case 4:
                holder.itemstatus.setText("Ditolak");
                break;
            case 5:
                holder.itemstatus.setText("Belum Selesai");
                break;
        }
        holder.itemnama.setText(orders.get(position).getArt().getName());
        holder.itemprofesi.setText(orders.get(position).getWork_time().getWork_time());
        try{
            holder.itemmulai.setText(costumedateformat(fixFormat.parse(orders.get(position).getStart_date())));
        }catch (ParseException e) {

        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
        doshorting();
        notifyDataSetChanged();
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date) + " " + timeFormat.format(date);
    }
    public void setcontext(Context context){
        this.context = context;
    }
    public void doshorting(){
        Collections.sort(orders, new Comparator<Order>(){
            public int compare(Order obj1, Order obj2) {
                return obj2.getStart_date().compareToIgnoreCase(obj1.getStart_date());
            }
        });
    }
}

