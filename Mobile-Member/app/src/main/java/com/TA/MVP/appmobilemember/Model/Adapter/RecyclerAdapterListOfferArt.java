package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.OfferArt;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.OfferResponse;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OfferRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.View.Activity.AsistenActivity;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActiveActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 28/07/17.
 */

public class RecyclerAdapterListOfferArt extends RecyclerView.Adapter<RecyclerAdapterListOfferArt.ViewHolder> {
    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private List<OfferArt> offerArts = new ArrayList<>();
    private Offer offer;
    private Context context;
    public RecyclerAdapterListOfferArt(Offer offer, Context context){
        this.offer = offer;
        this.context = context;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        //
        public TextView nomor, nama;
        public ImageView info, tolak, terima;
        public ViewHolder(final View itemview) {
            super(itemview);
            //
            nomor = (TextView) itemview.findViewById(R.id.nomor);
            nama = (TextView) itemview.findViewById(R.id.nama);
//            tolak = (ImageView) itemview.findViewById(R.id.tolak);
            terima = (ImageView) itemview.findViewById(R.id.terima);
            info = (ImageView) itemview.findViewById(R.id.info);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemview.getContext(), AsistenActivity.class);
                    intent.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(offerArts.get(position).getArt()));
                    itemview.getContext().startActivity(intent);
                }
            });
            terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((PermintaanActiveActivity)context).abuildermessage("Terima art ini?", "Konfirmasi");
                    ((PermintaanActiveActivity)context).abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int position = getAdapterPosition();
                            postpemesanan(offerArts.get(position).getArt_id());
                        }
                    });
                    ((PermintaanActiveActivity)context).abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ((PermintaanActiveActivity)context).showalertdialog();
                }
            });
        }

    }

    @Override
    public RecyclerAdapterListOfferArt.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_offer_art, parent, false);
        RecyclerAdapterListOfferArt.ViewHolder viewHolder = new RecyclerAdapterListOfferArt.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterListOfferArt.ViewHolder holder, int position) {
        holder.nomor.setText(position+1 + ". ");
        holder.nama.setText(offerArts.get(position).getArt().getName());
    }

    @Override
    public int getItemCount() { return offerArts.size();
    }

    public void setlistart(List<OfferArt> offerArts) {
        this.offerArts = offerArts;
        notifyDataSetChanged();
    }
    public void postpemesanan(final Integer artid){
        ((PermintaanActiveActivity)context).initProgressDialog("Pemesanan sedang diperoses");
        ((PermintaanActiveActivity)context).showDialog();
        Calendar calendar = Calendar.getInstance();

        HashMap<String,Object> map = new HashMap<>();
        map.put("member_id", offer.getMember_id().toString());
        map.put("art_id", artid.toString());
        map.put("work_time_id", offer.getWork_time_id().toString());
        map.put("job_id", offer.getJob_id().toString());
        map.put("cost", offer.getCost().toString());
        map.put("start_date", offer.getStart_date());
        map.put("end_date", offer.getEnd_date());
        map.put("remark", offer.getRemark());
        map.put("status", "1");
        map.put("status_member", "0");
        map.put("status_art", "0");
        map.put("contact", offer.getContact());
        map.put("created_at", fixFormat.format(calendar.getTime()));
        map.put("orderTaskList", offer.getOffer_task_list());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).postorder(map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                gantistatusoffer();
                gantistatusart(artid);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((PermintaanActiveActivity)context).dismissDialog();
//                Toast.makeText(getContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void gantistatusoffer(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("status", "1");
        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).patchoffer(offer.getId(), map);
        caller.enqueue(new APICallback<OfferResponse>() {
            @Override
            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onSuccess(call, response);
                ((PermintaanActiveActivity)context).dismissDialog();
                ((PermintaanActiveActivity)context).finish();
            }

            @Override
            public void onNotFound(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onNotFound(call, response);
                ((PermintaanActiveActivity)context).dismissDialog();
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((PermintaanActiveActivity)context).dismissDialog();
            }
        });
    }
    public void gantistatusart(Integer artid){
        HashMap<String,Object> map = new HashMap<>();
        map.put("status", "1");
        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).patchofferart(offer.getId(), artid, map);
        caller.enqueue(new APICallback<OfferResponse>() {
            @Override
            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onSuccess(call, response);
            }

            @Override
            public void onError(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onError(call, response);
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}