package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.RoundedTransformation;
import com.TA.MVP.appmobilemember.View.Activity.AsistenActivity;
import com.TA.MVP.appmobilemember.View.Fragment.FragmentHome;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class RecyclerAdapterAsisten extends RecyclerView.Adapter<RecyclerAdapterAsisten.ViewHolder> {
    private int thisYear, artbornyear;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat yearformat = new SimpleDateFormat("yyyy");
    private List<User> users = new ArrayList<>();
    private Context context;
    private FragmentHome fragmentHome;
    public RecyclerAdapterAsisten(Context context, FragmentHome fragmentHome){
        this.context = context;
        this.fragmentHome = fragmentHome;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnama,itemumur;
        public RatingBar itemrate;
        public ImageView imageView;

        public ViewHolder(final View itemview){
            super(itemview);
            imageView = (ImageView) itemview.findViewById(R.id.img);
            itemnama = (TextView) itemview.findViewById(R.id.card_wallet_nominal);
            itemumur = (TextView) itemview.findViewById(R.id.card_asis_umur);
            itemrate = (RatingBar) itemview.findViewById(R.id.card_asis_rating);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
//                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemview.getContext(), AsistenActivity.class);
                    i.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(users.get(position)));
                    itemview.getContext().startActivity(i);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_asisten,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == users.size()-2){
            fragmentHome.loadmore();
        }
        Log.d("Listposition",GsonUtils.getJsonFromObject(users.get(position)));
        Log.d("photo path",Settings.getRetrofitAPIUrl()+"image/medium/"+users.get(position).getAvatar());
        Picasso.with(context)
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+users.get(position).getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .fit().centerCrop()
                .transform(new RoundedTransformation(1000, 0))
                .into(holder.imageView);

        holder.itemnama.setText(users.get(position).getName());
        thisYear = calendar.get(Calendar.YEAR);
        artbornyear = Integer.valueOf(yearformat.format(users.get(position).getBorn_date()));
        holder.itemumur.setText(thisYear - artbornyear + " Thn");
        holder.itemrate.setRating(users.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public void setART(List<User> users){
        this.users = users;
        doshorting();
        Log.d("List size","" + users.size());
        notifyDataSetChanged();
        Log.d("List","" + GsonUtils.getJsonFromObject(this.users));
    }
    public void doshorting(){
        Collections.sort(users, new Comparator<User>(){
            public int compare(User obj1, User obj2) {
                return Float.toString(obj2.getRate()).compareTo(Float.toString(obj1.getRate()));
            }
        });
    }
    public void addmore(List<User> arts){
        Integer start = users.size();
        users.addAll(arts);
        notifyItemRangeInserted(start,arts.size());
    }
}
