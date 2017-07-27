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
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerja;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerjaShow;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.View.Activity.KetentuanActivity;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

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
 * Created by Zackzack on 18/06/2017.
 */

public class FragmentPemesanan3 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerjaShow rec_Adapter;
    private TextView linkketentuan;
    private Bundle b = new Bundle();
    private User art = new User();
    private User member = new User();
    private Order order = new Order();
    private List<Waktu_Kerja> defaultWK = new ArrayList<>();
    private FragmentAsistenmini fragmentAsistenmini;
    private TextView namaasis, usiaasis, agamaasis, estimasitext;
    private EditText prof, estimasi, mulai, selesai, total;
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
        View _view = inflater.inflate(R.layout.fragment_pemesanan3, container, false);
        member = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        art = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.ART_EXTRA), User.class);
        order = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.ORDER_EXTRA), Order.class);
        defaultWK = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getWaktu_kerjas();
        defaulttask = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getMyTasks();
//        myTasks = (List<MyTask>) GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.MYTASK_EXTRA), new TypeToken<List<MyTask>>(){}.getType());

        prof = (EditText) _view.findViewById(R.id.pms3_et_worktime);
        recyclerView = (RecyclerView) _view.findViewById(R.id.pms3_rec_listkerja);
        linkketentuan = (TextView) _view.findViewById(R.id.pms3_tv_ketentuan);
        prev = (Button) _view.findViewById(R.id.pms3_btn_prev);
        pesan = (Button) _view.findViewById(R.id.pms3_btn_pesan);
        estimasitext = (TextView) _view.findViewById(R.id.pms3_tv_estimasiwaktu);
//        layoutasisten = (RelativeLayout) _view.findViewById(R.id.layout_asisten);
//        estimasi = (EditText) _view.findViewById(R.id.pms3_et_estimate);
        mulai = (EditText) _view.findViewById(R.id.pms3_et_mulai);
        selesai = (EditText) _view.findViewById(R.id.pms3_et_selesai);
        total = (EditText) _view.findViewById(R.id.pms3_et_total);
//        layoutlistpekerjaan = (LinearLayout) _view.findViewById(R.id.pms3_layout_listpekerjaan);
//        fotoasis = (ImageView) _view.findViewById(R.id.asism_img);
        ketentuan = (CheckBox) _view.findViewById(R.id.pms3_cb_kttn);



        fragmentAsistenmini = new FragmentAsistenmini();
        Bundle b = new Bundle();
        b.putString(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
        fragmentAsistenmini.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.layout_asisten, fragmentAsistenmini).commit();

        prof.setText(defaultWK.get(order.getWork_time_id()-1).getWork_time());

        //listkerja
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerjaShow();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setDefaulttask(defaulttask);
        rec_Adapter.setList(order.getOrder_task_list());
        switch (order.getWork_time_id()){
            case 1:
                estimasitext.setText("Jam");
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case 2:
                estimasitext.setText("Hari");
                recyclerView.setVisibility(View.GONE);
                break;
            case 3:
                estimasitext.setText("Bulan");
                recyclerView.setVisibility(View.GONE);
                break;
        }
        try {
            mulai.setText(costumedateformat(fixFormat.parse(order.getStart_date())));
            selesai.setText(costumedateformat(fixFormat.parse(order.getEnd_date())));
        }catch (ParseException e){

        }
        total.setText(setRP(order.getCost()));

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
                ((PemesananActivity)getActivity()).doChangeFragment(2);
            }
        });

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ketentuan.isChecked()){
                    postpemesanan(order);
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
    public void postpemesanan(Order order){
        ((PemesananActivity)getActivity()).initProgressDialog("Pemesanan sedang diperoses");
        ((PemesananActivity)getActivity()).showDialog();
        Calendar calendar = Calendar.getInstance();


        HashMap<String,Object> map = new HashMap<>();
        map.put("member_id", member.getId().toString());
        map.put("art_id", art.getId().toString());
        map.put("work_time_id", order.getWork_time_id().toString());
        map.put("cost", order.getCost().toString());
        map.put("start_date", order.getStart_date());
        map.put("end_date", order.getEnd_date());
        map.put("remark", order.getRemark());
        map.put("status", "0");
        map.put("contact", order.getContact());
        map.put("created_at", fixFormat.format(calendar.getTime()));
        map.put("orderTaskList", order.getOrder_task_list());
        Call<OrderResponse> caller = APIManager.getRepository(OrderRepo.class).postorder(map);
        caller.enqueue(new APICallback<OrderResponse>() {
            @Override
            public void onSuccess(Call<OrderResponse> call, Response<OrderResponse> response) {
                super.onSuccess(call, response);
                ((PemesananActivity)getActivity()).dismissDialog();
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((PemesananActivity)getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
