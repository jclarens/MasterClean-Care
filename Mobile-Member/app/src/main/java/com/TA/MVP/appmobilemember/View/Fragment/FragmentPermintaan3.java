package com.TA.MVP.appmobilemember.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.Model.Responses.OfferResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OfferRepo;
import com.TA.MVP.appmobilemember.View.Activity.KetentuanActivity;
import com.TA.MVP.appmobilemember.View.Activity.MainActivity;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class FragmentPermintaan3 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerjaShow rec_Adapter;
    private TextView linkketentuan;
    private Bundle b = new Bundle();
    private User member = new User();
    private Offer offer = new Offer();
    private List<Waktu_Kerja> defaultWK = new ArrayList<>();
    private TextView namaasis, usiaasis, agamaasis, estimasitext;
    private EditText worktime, estimasi, mulai, selesai, total;
    private LinearLayout layoutlistpekerjaan;
    private ImageView fotoasis;
    private RatingBar ratingasis;
    private CheckBox ketentuan;
    private Button prev, pesan, selengkapnya;
    private RelativeLayout layoutasisten;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private ArrayBulan arrayBulan = new ArrayBulan();
    private List<MyTask> myTasks = new ArrayList<>();
    private List<MyTask> defaulttask = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_permintaan3, container, false);
        member = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        offer = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.OFFER_EXTRA), Offer.class);
        defaultWK = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getWaktu_kerjas();
        defaulttask = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getMyTasks();
//        myTasks = (List<MyTask>) GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.MYTASK_EXTRA), new TypeToken<List<MyTask>>(){}.getType());

        worktime = (EditText) _view.findViewById(R.id.pmr3_et_worktime);
        linkketentuan = (TextView) _view.findViewById(R.id.pmr3_tv_ketentuan);
        prev = (Button) _view.findViewById(R.id.pmr3_btn_prev);
        pesan = (Button) _view.findViewById(R.id.pmr3_btn_pesan);
//        estimasitext = (TextView) _view.findViewById(R.id.pmr3_tv_estimasiwaktu);
        mulai = (EditText) _view.findViewById(R.id.pmr3_et_mulai);
        selesai = (EditText) _view.findViewById(R.id.pmr3_et_selesai);
        total = (EditText) _view.findViewById(R.id.pmr3_et_total);
        ketentuan = (CheckBox) _view.findViewById(R.id.pmr3_cb_kttn);

        worktime.setText(defaultWK.get(offer.getWork_time_id()-1).getWork_time());

        //listkerja
        recyclerView = (RecyclerView) _view.findViewById(R.id.pmr3_rec_listkerja);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaShow();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaulttask(defaulttask);
        rec_Adapter.setList(offer.getOffer_task_list());
        switch (offer.getWork_time_id()){
            case 1:
//                estimasitext.setText("Jam");
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case 2:
//                estimasitext.setText("Hari");
                recyclerView.setVisibility(View.GONE);
                break;
            case 3:
//                estimasitext.setText("Bulan");
                recyclerView.setVisibility(View.GONE);
                break;
        }
        try {
            mulai.setText(costumedateformat(fixFormat.parse(offer.getStart_date())));
            selesai.setText(costumedateformat(fixFormat.parse(offer.getEnd_date())));
        }catch (ParseException e){

        }
        total.setText(setRP(offer.getCost()));

        linkketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), KetentuanActivity.class);
                startActivity(intent);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PermintaanActivity)getActivity()).doChangeFragment(2);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ketentuan.isChecked()){
                    postpemesanan(offer);
                }
                else Toast.makeText(getContext(), "Anda tidak menyetujui ketentuan yang berlaku", Toast.LENGTH_SHORT).show();
            }
        });

        return _view;
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date)));
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date) + " " + timeFormat.format(date);
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void postpemesanan(Offer offer){
        ((PermintaanActivity)getActivity()).initProgressDialog("Pemesanan sedang diperoses");
        ((PermintaanActivity)getActivity()).showDialog();
        Calendar calendar = Calendar.getInstance();


        HashMap<String,Object> map = new HashMap<>();
        map.put("member_id", member.getId().toString());
        map.put("work_time_id", offer.getWork_time_id().toString());
        map.put("cost", offer.getCost().toString());
        map.put("start_date", offer.getStart_date());
        map.put("end_date", offer.getEnd_date());
        if (offer.getRemark() == null)
            map.put("remark", " ");
        else map.put("remark", offer.getRemark());
        map.put("status", "0");
        map.put("contact", offer.getContact());
        map.put("created_at", fixFormat.format(calendar.getTime()));
        map.put("offerTaskList", offer.getOffer_task_list());
        Call<OfferResponse> caller = APIManager.getRepository(OfferRepo.class).postoffer(map);
        caller.enqueue(new APICallback<OfferResponse>() {
            @Override
            public void onSuccess(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onSuccess(call, response);
                ((PermintaanActivity)getActivity()).dismissDialog();
//                getActivity().setResult(MainActivity.RESULT_SUCCESS);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((PermintaanActivity)getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Call<OfferResponse> call, Response<OfferResponse> response) {
                super.onError(call, response);
                Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
