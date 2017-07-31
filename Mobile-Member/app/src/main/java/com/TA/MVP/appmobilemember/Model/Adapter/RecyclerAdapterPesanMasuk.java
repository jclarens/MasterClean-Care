package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Responses.MyMessageResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.View.Activity.BacaPesanMasukActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 07/07/2017.
 */

public class RecyclerAdapterPesanMasuk extends RecyclerView.Adapter<RecyclerAdapterPesanMasuk.ViewHolder> {
    private DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private List<MyMessage> myMessages = new ArrayList<>();

    private Context context;
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama,itemtanggal,itemjam,itemsubject;
        public ImageView imageView;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_pesan_nama);
            itemtanggal = (TextView) itemview.findViewById(R.id.card_pesan_tanggal);
            itemjam = (TextView) itemview.findViewById(R.id.card_pesan_jam);
            itemsubject = (TextView) itemview.findViewById(R.id.card_pesan_subject);
            imageView = (ImageView) itemview.findViewById(R.id.card_pesan_img);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(itemview.getContext(), BacaPesanMasukActivity.class);
                    i.putExtra("msg", GsonUtils.getJsonFromObject(myMessages.get(position)));
                    if (myMessages.get(position).getStatus_member() == 0)
                        openmessage(myMessages.get(position).getId(), i);
                    else ((MainActivity)context).startActivityForResult(i,MainActivity.REQUEST_PESAN);
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
        holder.itemnama.setText(myMessages.get(position).getSender_id().getName());
        try{
            holder.itemtanggal.setText(dateFormat.format(getdateFormat.parse(myMessages.get(position).getCreated_at())));
            holder.itemjam.setText(timeFormat.format(getdateFormat.parse(myMessages.get(position).getCreated_at())));
        }
        catch (ParseException pe){

        }

        holder.itemsubject.setText(myMessages.get(position).getSubject());
        if (myMessages.get(position).getStatus_member() == 0){
            holder.imageView.setImageResource(R.drawable.ic_closed_msg);
        } else holder.imageView.setImageResource(R.drawable.ic_opened_msg);
    }

    @Override
    public int getItemCount() {
        return myMessages.size();
    }

    public void setPesan(List<MyMessage> myMessages){
        this.myMessages = myMessages;
        //removing status hapus
        for (int n=0;n<myMessages.size();n++){
            if (myMessages.get(n).getStatus_member() == 2){
                myMessages.remove(myMessages.get(n));
            }
        }
        doshorting();
        notifyDataSetChanged();
    }
    public void setcontext(Context context){
        this.context = context;
    }
    public void doshorting(){
        Collections.sort(myMessages, new Comparator<MyMessage>(){
            public int compare(MyMessage obj1, MyMessage obj2) {
            return obj2.getCreated_at().compareToIgnoreCase(obj1.getCreated_at());
        }
        });
    }
    public void openmessage(Integer id, final Intent intent){
        Call<MyMessageResponse> caller = APIManager.getRepository(MessageRepo.class).patchmessage(id.toString(), 1);
        caller.enqueue(new APICallback<MyMessageResponse>() {
            @Override
            public void onSuccess(Call<MyMessageResponse> call, Response<MyMessageResponse> response) {
                super.onSuccess(call, response);
                ((MainActivity)context).startActivityForResult(intent,MainActivity.REQUEST_PESAN);
            }

            @Override
            public void onFailure(Call<MyMessageResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(context,"Gagal membuka pesan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//Collections.sort(myList, new Comparator<EmployeeClass>(){
//public int compare(EmployeeClass obj1, EmployeeClass obj2) {
//        // ## Ascending order
//        return obj1.firstName.compareToIgnoreCase(obj2.firstName); // To compare string values
//        // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values
//
//        // ## Descending order
//        // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
//        // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
//        }
//        });
