package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.MyMessage;
import com.mvp.mobile_art.Model.Responses.MyMessageResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.MessageRepo;
import com.mvp.mobile_art.View.Activity.BacaPesanMasukActivity;
import com.mvp.mobile_art.View.Activity.MainActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 07/07/2017.
 */

public class RecyclerAdapterPesanMasuk extends RecyclerView.Adapter<RecyclerAdapterPesanMasuk.ViewHolder> {
    private List<MyMessage> myMessages = new ArrayList<>();
    private Context context;
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama,itemtanggal,itemsubject;
        public ImageView imageView;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnama = (TextView) itemview.findViewById(R.id.card_pesan_nama);
            itemtanggal = (TextView) itemview.findViewById(R.id.card_pesan_tanggal);
            itemsubject = (TextView) itemview.findViewById(R.id.card_pesan_subject);
            imageView = (ImageView) itemview.findViewById(R.id.card_pesan_img);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent i = new Intent(itemview.getContext(), BacaPesanMasukActivity.class);
                    i.putExtra("msg", GsonUtils.getJsonFromObject(myMessages.get(position)));
                    if (myMessages.get(position).getStatus() == 0)
                        openmessage(myMessages.get(position).getId(), i);
                    else ((MainActivity)context).startActivityForResult(i, MainActivity.REQUEST_PESAN);
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
        holder.itemnama.setText(myMessages.get(position).getSender().getName());
        holder.itemtanggal.setText(myMessages.get(position).getCreated_at());
        holder.itemsubject.setText(myMessages.get(position).getSubject());
        if (myMessages.get(position).getStatus() == 0){
            holder.imageView.setImageResource(R.drawable.ic_closed_msg);
        } else holder.imageView.setImageResource(R.drawable.ic_opened_msg);
    }

    @Override
    public int getItemCount() {
        return myMessages.size();
    }

    public void setPesan(List<MyMessage> myMessages){
        this.myMessages = myMessages;
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
