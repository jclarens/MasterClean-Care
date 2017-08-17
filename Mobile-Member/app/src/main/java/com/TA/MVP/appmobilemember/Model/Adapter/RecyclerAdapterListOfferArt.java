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
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.OfferResponse;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OfferRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AsistenActivity;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActiveActivity;
import com.TA.MVP.appmobilemember.View.Activity.WalletActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private Calendar calendar = Calendar.getInstance();
    private Calendar waktumulai = new GregorianCalendar();
    private Calendar waktuselesai = new GregorianCalendar();
    private Calendar batasmulai = new GregorianCalendar();
    private Calendar batasselesai = new GregorianCalendar();
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
            nama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemview.getContext(), AsistenActivity.class);
                    intent.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(offerArts.get(position).getArt()));
                    intent.putExtra("minidetail", "y");
                    itemview.getContext().startActivity(intent);
                }
            });
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemview.getContext(), AsistenActivity.class);
                    intent.putExtra(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(offerArts.get(position).getArt()));
                    intent.putExtra("minidetail", "y");
                    itemview.getContext().startActivity(intent);
                }
            });
            terima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    ((PermintaanActiveActivity)context).abuildermessage("Menerima " + offerArts.get(position).getArt().getName() +" untuk bekerja?", "Konfirmasi");
                    ((PermintaanActiveActivity)context).abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            postpemesanan(offerArts.get(position).getId(),offerArts.get(position).getArt_id());
                            offerArts.get(position).setStatus(1);
                            loaduser(offerArts.get(position).getArt().getId());
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
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
    public void loaduser(final Integer id){
        ((PermintaanActiveActivity)context).initProgressDialog("Pemesanan sedang diperoses");
        ((PermintaanActiveActivity)context).showDialog();
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(offer.getMember_id().toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                if (response.body().getUser_wallet().getAmt() >= offer.getCost()){
                    getjadwal(id);
                }else{
                    ((PermintaanActiveActivity)context).dismissDialog();
                    ((PermintaanActiveActivity)context).abuildermessage("Wallet anda tidak mencukupi untuk melakukan pemesanan\nWallet anda:" + setRP(response.body().getUser_wallet().getAmt()), "Pemberitahuan");
                    ((PermintaanActiveActivity)context).abuilder.setPositiveButton("Top up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, WalletActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    ((PermintaanActiveActivity)context).abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ((PermintaanActiveActivity)context).showalertdialog();
                }
            }

            @Override
            public void onError(Call<User> call, Response<User> response) {
                super.onError(call, response);
                ((PermintaanActiveActivity)context).dismissDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                ((PermintaanActiveActivity)context).dismissDialog();
            }
        });
    }
    public void getjadwal(Integer id){
        Call<List<Order>> callerjadwal = APIManager.getRepository(OrderRepo.class).getordersByArtstatus( id , 1);
        callerjadwal.enqueue(new APICallback<List<Order>>() {
            @Override
            public void onSuccess(Call<List<Order>> call, Response<List<Order>> response) {
                super.onSuccess(call, response);
                //check jadwal bentrok
                if (validasijadwal(response.body())){
                    confirmoffer();
                }
                else {
                    ((PermintaanActiveActivity) context).dismissDialog();
                    Toast.makeText(context, "Asisten sudah tidak dapat menerima pemesanan pada jam ini.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                super.onFailure(call, t);
                ((PermintaanActiveActivity)context).dismissDialog();
                Toast.makeText(context, "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }

        });
    }
    public boolean validasijadwal(List<Order> orders){
        try {
            waktumulai.setTime(fixFormat.parse(offer.getStart_date()));
            waktuselesai.setTime(fixFormat.parse(offer.getEnd_date()));
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        for (int n=0;n<orders.size();n++){
            try {
                batasmulai.setTime(fixFormat.parse(orders.get(n).getStart_date()));
                batasselesai.setTime(fixFormat.parse(orders.get(n).getEnd_date()));
                batasmulai.add(Calendar.HOUR_OF_DAY, -1);
                batasselesai.add(Calendar.HOUR_OF_DAY, 1);
            } catch (ParseException e) {
//                e.printStackTrace();
            }
            if (waktumulai.after(batasmulai) && waktumulai.before(batasselesai))
                return false;
            if (waktuselesai.after(batasmulai) && waktuselesai.before(batasselesai))
                return false;
        }
        return true;
    }
    public void confirmoffer(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("offer_art", offerArts);
        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).patchoffer(offer.getId(), map);
        caller.enqueue(new APICallback<OfferResponse>() {
            @Override
            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onSuccess(call, response);
                ((PermintaanActiveActivity)context).dismissDialog();
                ((PermintaanActiveActivity)context).abuildermessage("Penawaran ini sudah menjadi pemesanan aktif. silahkan lihat info lengkap pada menu Pemesanan.", "Pemberitahuan");
                ((PermintaanActiveActivity)context).abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((PermintaanActiveActivity)context).finish();
                    }
                });
                ((PermintaanActiveActivity)context).showalertdialog();
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((PermintaanActiveActivity)context).dismissDialog();
                Toast.makeText(context,"Koneksi bermasalah.", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    public void postpemesanan(final Integer offerartid, Integer artid){
//        ((PermintaanActiveActivity)context).initProgressDialog("Pemesanan sedang diperoses");
//        ((PermintaanActiveActivity)context).showDialog();
//        Calendar calendar = Calendar.getInstance();
//
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("member_id", offer.getMember_id().toString());
//        map.put("art_id", artid.toString());
//        map.put("work_time_id", offer.getWork_time_id().toString());
//        map.put("job_id", offer.getJob_id().toString());
//        map.put("cost", offer.getCost().toString());
//        map.put("start_date", offer.getStart_date());
//        map.put("end_date", offer.getEnd_date());
//        map.put("remark", offer.getRemark());
//        map.put("status", "1");
//        map.put("status_member", "0");
//        map.put("status_art", "0");
//        map.put("contact", offer.getContact());
//        map.put("created_at", fixFormat.format(calendar.getTime()));
//        map.put("orderTaskList", offer.getOffer_task_list());
//        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).postorder(map);
//        caller.enqueue(new APICallback<OrderResponse>() {
//            @Override
//            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
//                super.onSuccess(call, response);
//                gantistatusoffer();
//                gantistatusart(offerartid);
//            }
//
//            @Override
//            public void onFailure(Call<OrderResponse> call, Throwable t) {
//                super.onFailure(call, t);
//                ((PermintaanActiveActivity)context).dismissDialog();
////                Toast.makeText(getContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public void gantistatusart(Integer offerartid){
//        HashMap<String,Object> map = new HashMap<>();
////        map.put("offer_id", offer.getId().toString());
//        map.put("status", "1");
//        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).patchofferart(offerartid, map);
//        caller.enqueue(new APICallback<OfferResponse>() {
//            @Override
//            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
//                super.onSuccess(call, response);
//            }
//
//            @Override
//            public void onError(Call<OfferResponse> call, Response<OfferResponse> response) {
//                super.onError(call, response);
//            }
//
//            @Override
//            public void onFailure(Call<OfferResponse> call, Throwable t) {
//                super.onFailure(call, t);
//            }
//        });
//    }

}