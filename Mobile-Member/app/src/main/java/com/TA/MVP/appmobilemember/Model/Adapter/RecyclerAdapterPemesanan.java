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
import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActiveActivity;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

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
 * Created by Zackzack on 08/07/2017.
 */

public class RecyclerAdapterPemesanan extends RecyclerView.Adapter<RecyclerAdapterPemesanan.ViewHolder> {
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
//                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemview.getContext(), PemesananActiveActivity.class);
                    i.putExtra(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(orders.get(position)));
//                    itemview.getContext().startActivity(i);
                    ((MainActivity)context).startActivityForResult(i,MainActivity.REQUEST_ORDER);
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

    public void setOrders(List<Order> orders, Integer status){
        List<Order> temp = new ArrayList<>();
        for (int n=0; n<orders.size();n++){
            if (orders.get(n).getStatus() == status)
                temp.add(orders.get(n));
        }
        this.orders = temp;
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

