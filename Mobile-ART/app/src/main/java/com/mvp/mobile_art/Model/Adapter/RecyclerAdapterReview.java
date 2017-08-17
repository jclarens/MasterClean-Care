package com.mvp.mobile_art.Model.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.RoundedTransformation;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Activity.MemberActivity;
import com.mvp.mobile_art.View.Activity.ProfileActivity;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class RecyclerAdapterReview extends RecyclerView.Adapter<RecyclerAdapterReview.ViewHolder> {
    private List<Order> orders = new ArrayList<>();
    private Context context;
    public RecyclerAdapterReview(Context context){
        this.context = context;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nama,remark;
        public RatingBar ratingBar;
        public ImageView imageView;
        public Integer imgheigh, imgwidth;
        //
        public ViewHolder(final View itemview){
            super(itemview);
            nama = (TextView) itemview.findViewById(R.id.nama);
            remark = (TextView) itemview.findViewById(R.id.remark);
            ratingBar = (RatingBar) itemview.findViewById(R.id.rating);
            imageView = (ImageView) itemview.findViewById(R.id.img);
            imgheigh = imageView.getHeight();
            imgwidth = imageView.getWidth();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ((ProfileActivity)context).getuser(orders.get(position).getMember_id());
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_review,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nama.setText(orders.get(position).getMember().getName());
        holder.remark.setText(orders.get(position).getReview_order().getRemark());
        holder.ratingBar.setRating(orders.get(position).getReview_order().getRate());

        Picasso.with(context)
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+orders.get(position).getMember().getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .fit().centerCrop()
                .transform(new RoundedTransformation(1000, 0))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    public void setlist(List<Order> orders){
        this.orders = orders;
        doshorting();
        notifyDataSetChanged();
    }
    public void doshorting(){
        Collections.sort(orders, new Comparator<Order>(){
            public int compare(Order obj1, Order obj2) {
                return obj2.getReview_order().getCreated_at().compareToIgnoreCase(obj1.getReview_order().getCreated_at());
            }
        });
    }
}
