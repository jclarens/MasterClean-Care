package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerja;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Array.ArrayHari;
import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTime;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MyTaskRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 18/06/2017.
 */

public class FragmentPemesanan2 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerja rec_Adapter;
    private List<MyTask> myTasks = new ArrayList<>();
    private User art = new User();
    private Order order = new Order();

    private Spinner prof, waktukrj;
    private EditText mulaitime, mulaidate, selesaitime, selesaidate, estimasi, cttntmbhn, totalbiaya;
    private TextView estimasiwaktutext;
    private Button prev,next, up, down;
    private ArrayAdapter arrayAdapterWaktu;
    private ArrayAdapter arrayAdapterProfesi;
    private List<Waktu_Kerja> defaultWK = new ArrayList<>();
    private List<Job> defaultProf = new ArrayList<>();
    private TextWatcher textWatcher = null;

    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private int minestimasi = 1;
    private int maxestimasi = 8;
    private int tmp;

    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private DatePickerDialog datePickerDialog1;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar = Calendar.getInstance();
    private OrderTime now = new OrderTime();
    private OrderTime temp = new OrderTime();
    private OrderTime endtemp = new OrderTime();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private Date startdate, enddate;

    private String fixstart, fixend;
    private Integer artcost = 0;
    private Integer total = 0;
    private boolean asistenrt = false;
    private boolean perjam = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan2, container, false);
        art = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.ART_EXTRA), User.class);
        order = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.ORDER_EXTRA), Order.class);
        defaultWK = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getWaktu_kerjas();
        defaultProf = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getJobs();

        waktukrj = (Spinner) _view.findViewById(R.id.pms2_spinner_waktukerja);
        prof = (Spinner) _view.findViewById(R.id.pms2_spinner_profesi);
        recyclerView = (RecyclerView) _view.findViewById(R.id.pms2_rec_listkerja);
        mulaitime = (EditText) _view.findViewById(R.id.pms2_et_mulaitime);
        mulaidate = (EditText) _view.findViewById(R.id.pms2_et_mulaidate);
        selesaitime = (EditText) _view.findViewById(R.id.pms2_et_selesaitime);
        selesaidate = (EditText) _view.findViewById(R.id.pms2_et_selesaidate);
        estimasi = (EditText) _view.findViewById(R.id.pms2_et_estimasi);
        cttntmbhn = (EditText) _view.findViewById(R.id.pms2_et_cttn);
        totalbiaya = (EditText) _view.findViewById(R.id.pms2_et_total);
        estimasiwaktutext = (TextView) _view.findViewById(R.id.pms2_tv_estimasiwaktu);
        prev = (Button) _view.findViewById(R.id.pms2_btn_prev);
        next = (Button) _view.findViewById(R.id.pms2_btn_next);
        up = (Button) _view.findViewById(R.id.btn_up);
        down = (Button) _view.findViewById(R.id.btn_down);

        order.setWork_time_id(art.getUser_work_time().get(0).getWork_time_id());
        order.setJob_id(art.getUser_job().get(0).getJob_id());

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = Integer.valueOf(estimasi.getText().toString());
                if (tmp < maxestimasi)
                    estimasi.setText(String.valueOf(tmp + 1));
                estimasi.clearFocus();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = Integer.valueOf(estimasi.getText().toString());
                if (tmp > minestimasi)
                    estimasi.setText(String.valueOf(tmp - 1));
                estimasi.clearFocus();
            }
        });

        setwaktusekarang();
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mulaidate.setText(i + "-" + i2 + "-" + i1);
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
//                Log.d("Pick waktu","Tanggal " + i + " Bulan "+ i1 + " Tahun " + i2);
                settanggal();
            }
        }, now.year, now.month, now.day);
        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mulaitime.setText(i + ":" + i1);
                calendar.set(Calendar.HOUR_OF_DAY,i);
                calendar.set(Calendar.MINUTE,i1);
                calendar.set(Calendar.MILLISECOND, 0);
                settanggal();
            }
        },0, 0, true);

        mulaidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog1.show();
            }
        });
        mulaitime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        //spinner WK
        List<Waktu_Kerja> tempWK = new ArrayList<>();
        for (int i=0;i<art.getUser_work_time().size(); i++){
            tempWK.add(defaultWK.get(art.getUser_work_time().get(i).getWork_time_id()-1));
        }
        arrayAdapterWaktu = new ArrayAdapter(getContext(), R.layout.spinner_item, tempWK);
        waktukrj.setAdapter(arrayAdapterWaktu);
        waktukrj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (art.getUser_work_time().get(i).getWork_time_id()){
                    case 1:
                        //jam
                        mulaitime.setEnabled(true);
                        estimasiwaktutext.setText("(Jam)");
                        minestimasi = 2;
                        maxestimasi = 8;
                        estimasi.setText(String.valueOf(minestimasi));
                        rec_Adapter.setshowtask(1);
                        artcost = art.getUser_work_time().get(i).getCost();
                        order.setWork_time_id(1);
                        perjam = true;
                        break;
                    case 2:
                        //hari
                        mulaitime.setEnabled(false);
                        estimasiwaktutext.setText("(Hari)");
                        minestimasi = 1;
                        maxestimasi = 30;
                        estimasi.setText(String.valueOf(minestimasi));
                        artcost = art.getUser_work_time().get(i).getCost();
                        order.setWork_time_id(2);
                        perjam = false;
                        break;
                    case 3:
                        //bulan
                        mulaitime.setEnabled(false);
                        estimasiwaktutext.setText("(Bulan)");
                        minestimasi = 1;
                        maxestimasi = 12;
                        estimasi.setText(String.valueOf(minestimasi));
                        artcost = art.getUser_work_time().get(i).getCost();
                        order.setWork_time_id(3);
                        perjam = false;
                        break;
                }
                setRecyclerViewvisibility();
                settotal();
                settanggal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //listkerja
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerja(FragmentPemesanan2.this);
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setFullTasks(myTasks);
        loadlist();

        //spinner prof
        List<Job> tempprof = new ArrayList<>();
        for (int i=0;i<art.getUser_job().size(); i++){
            tempprof.add(defaultProf.get(art.getUser_job().get(i).getJob_id()-1));
        }
        arrayAdapterProfesi = new ArrayAdapter(getContext(), R.layout.spinner_item, tempprof);
        prof.setAdapter(arrayAdapterProfesi);
        prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (art.getUser_job().get(i).getJob_id()){
                    case 1:
                        //Asisten Rumah Tangga
                        rec_Adapter.setshowtask(1);
                        asistenrt = true;
                        order.setJob_id(1);
                        break;
                    case 2:
                        //Perawat Lansia
                        rec_Adapter.setshowtask(2);
                        asistenrt = false;
                        if (order.getWork_time_id().equals(1)) {
                            rec_Adapter.addallshown();
                        }
                        order.setJob_id(2);
                        break;
                    case 3:
                        //Babysitter
                        rec_Adapter.setshowtask(3);
                        asistenrt = false;
                        if (order.getWork_time_id().equals(1)){
                            rec_Adapter.addallshown();
                        }
                        order.setJob_id(3);
                        break;
                    case 4:
                        //Perawat Balita
//                        rec_Adapter.setList(orderTasks);
                        rec_Adapter.setshowtask(4);
                        asistenrt = false;
                        if (estimasiwaktutext.getText().toString().equals("Jam")) {
                            estimasi.setEnabled(true);
                            rec_Adapter.addallshown();
                        }
                        order.setJob_id(4);
                        break;
                }
                setRecyclerViewvisibility();
                settotal();
                settanggal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.save(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                SharedPref.save(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(order));
                ((PemesananActivity)getActivity()).doChangeFragment(1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((PemesananActivity)getActivity()) ///loading pls
                if (validasi()){
                    //save data from this fragment ----------------------------------------------------------------------------------------------------
                    order.setCost(total);
                    order.setStart_date(fixstart);
                    order.setEnd_date(fixend);
                    try {
                        order.setRemark(cttntmbhn.getText().toString());
                    }catch (NullPointerException e){
                        order.setRemark("");
                    }
                    order.setStatus(0);
                    List<MyTask> templist = rec_Adapter.getselectedtasklist();
                    List<OrderTask> newlist = new ArrayList<>();
                    for (int n=0;n<templist.size();n++){
                        OrderTask orderTask = new OrderTask();
                        orderTask.setTask_list_id(templist.get(n).getId());
                        orderTask.setStatus(0);
                        newlist.add(orderTask);
                    }
                    order.setOrder_task_list(newlist);

                    SharedPref.save(ConstClass.ART_EXTRA, GsonUtils.getJsonFromObject(art));
                    SharedPref.save(ConstClass.ORDER_EXTRA, GsonUtils.getJsonFromObject(order));
                    ((PemesananActivity)getActivity()).doChangeFragment(3);
                }
            }
        });

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                estimasi.removeTextChangedListener(textWatcher);
                if (estimasi.getText().toString().equals(""))
                    estimasi.setText(String.valueOf(minestimasi));
                else {
                    tmp = Integer.valueOf(estimasi.getText().toString().replace(".",""));
                    if (tmp > maxestimasi)
                        estimasi.setText(String.valueOf(maxestimasi));
                    else if (tmp < minestimasi)
                        estimasi.setText(String.valueOf(minestimasi));
                }
                settanggal();
                settotal();
                estimasi.addTextChangedListener(textWatcher);
            }
        };
        estimasi.addTextChangedListener(textWatcher);


        return _view;
    }
    public void setwaktusekarang(){
        calendar = Calendar.getInstance();
        now.year = calendar.get(Calendar.YEAR);
        now.month = calendar.get(Calendar.MONTH);
        now.day = calendar.get(Calendar.DAY_OF_MONTH);
        now.hour = calendar.get(Calendar.HOUR_OF_DAY);
        now.minute = calendar.get(Calendar.MINUTE);
    }
    public void setwaktutemp(){
        temp.year = calendar.get(Calendar.YEAR);
        temp.month = calendar.get(Calendar.MONTH);
        temp.day = calendar.get(Calendar.DAY_OF_MONTH);
        temp.hour = calendar.get(Calendar.HOUR_OF_DAY);
        temp.minute = calendar.get(Calendar.MINUTE);
    }
    public void getwaktutemp(){
        calendar.set(Calendar.YEAR,temp.year);
        calendar.set(Calendar.MONTH,temp.month);
        calendar.set(Calendar.DAY_OF_MONTH,temp.day);
        calendar.set(Calendar.HOUR_OF_DAY,temp.hour);
        calendar.set(Calendar.MINUTE,temp.minute);
    }
    public void setwaktuendtemp(){
        endtemp.year = calendar.get(Calendar.YEAR);
        endtemp.month = calendar.get(Calendar.MONTH);
        endtemp.day = calendar.get(Calendar.DAY_OF_MONTH);
        endtemp.hour = calendar.get(Calendar.HOUR_OF_DAY);
        endtemp.minute = calendar.get(Calendar.MINUTE);
    }
    public void getwaktuendtemp(){
        calendar.set(Calendar.YEAR,endtemp.year);
        calendar.set(Calendar.MONTH,endtemp.month);
        calendar.set(Calendar.DAY_OF_MONTH,endtemp.day);
        calendar.set(Calendar.HOUR_OF_DAY,endtemp.hour);
        calendar.set(Calendar.MINUTE,endtemp.minute);
    }
    public  void settanggal(){
        switch (order.getWork_time_id()){
            case 1:
                break;
            case 2:
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                break;
            case 3:
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                break;
        }
        setwaktutemp();
        startdate = calendar.getTime();
        Integer temp = Integer.valueOf(estimasi.getText().toString().replace(".",""));
        switch (order.getWork_time_id()){
            case 1:
                calendar.add(Calendar.HOUR_OF_DAY, temp);
                break;
            case 2:
                calendar.add(Calendar.DAY_OF_MONTH, temp-1);
                calendar.add(Calendar.HOUR_OF_DAY, 9);
                break;
            case 3:
                calendar.add(Calendar.MONTH, temp);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                calendar.add(Calendar.HOUR_OF_DAY, 9);
                break;
        }
        setwaktuendtemp();
        enddate = calendar.getTime();
        getwaktutemp();
        settampilan();
    }
    public void settampilan(){
        fixstart = fixFormat.format(startdate.getTime());
        fixend = fixFormat.format(enddate.getTime());
        mulaidate.setText(costumedateformat(startdate));
        mulaitime.setText(timeFormat.format(startdate.getTime()));
        selesaidate.setText(costumedateformat(enddate));
        selesaitime.setText(timeFormat.format(enddate.getTime()));
    }
    public Date getbatassekarang(){
        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,2);
        Date ddate = calendar.getTime();
        getwaktutemp();
        return ddate;
    }
    public Date getbatasmulai(){
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 59);
        Date ddate = calendar.getTime();
        getwaktutemp();
        return ddate;
    }
    public Date getbatasselesai(){
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 1);
        Date ddate = calendar.getTime();
        getwaktutemp();
        return ddate;
    }
    public Date getbatasselesai2(){
        getwaktuendtemp();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 1);
        Date ddate = calendar.getTime();
        getwaktutemp();
        return ddate;
    }
    public boolean validasi(){
        if (order.getJob_id().equals(1) && order.getWork_time_id().equals(1) && rec_Adapter.getselectedtasklist().size() < 1){
            Toast.makeText(getContext(), "Pilih 1 list kerja atau lebih", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startdate.before(getbatasmulai())){
            Toast.makeText(getContext(), "Tidak dapat menerima pesanan sebelum jam 8 Pagi", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startdate.after(getbatasselesai())){
            Toast.makeText(getContext(), "Tidak dapat menerima pesanan setelah jam 5 Sore", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (enddate.after(getbatasselesai2())){
            Toast.makeText(getContext(), "Tidak dapat menerima pesanan setelah jam 5 Sore", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startdate.before(getbatassekarang())){
            Toast.makeText(getContext(), "Harap memesan untuk 2 jam kedepan", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public String costumedateformat(Date date){
//        String hari = arrayHari.getArrayList().get(Integer.parseInt(hariFormat.format(date)));
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        // Senin, Januari 30
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public void loadlist(){
        ((PemesananActivity)getActivity()).initProgressDialog("Sedang memuat data . . ");
        ((PemesananActivity)getActivity()).showDialog();
        Call<List<MyTask>> caller = APIManager.getRepository(MyTaskRepo.class).gettasks();
        caller.enqueue(new APICallback<List<MyTask>>() {
            @Override
            public void onSuccess(Call<List<MyTask>> call, Response<List<MyTask>> response) {
                super.onSuccess(call, response);
                myTasks = response.body();
                rec_Adapter.setFullTasks(myTasks);

                //load awal recview
                try{
                    if (art.getUser_job().get(0).getJob_id() == 1)
                        rec_Adapter.setshowtask(1);
                    else recyclerView.setVisibility(View.GONE);
                }catch (NullPointerException e){
                    Toast.makeText(getContext(),"Asisten ini tidak memilih pekerjaan", Toast.LENGTH_SHORT).show();
                }
                ((PemesananActivity)getActivity()).dismissDialog();
            }

            @Override
            public void onFailure(Call<List<MyTask>> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getContext(),"Koneksi bermasalah.", Toast.LENGTH_SHORT).show();
                ((PemesananActivity)getActivity()).dismissDialog();
                //dosomething here pls
            }
        });
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number) + ".00";
        return tempp;
    }
    public void settotal(){
        tmp = Integer.valueOf(estimasi.getText().toString());
        total = artcost * tmp;
        totalbiaya.setText(setRP(total));
    }

    public void setbobot(){
        List<MyTask> selectedtasks = rec_Adapter.getselectedtasklist();
        Integer tmpbobot = 0;
        tmp = Integer.valueOf(estimasi.getText().toString());
        for(int n=0; n<selectedtasks.size();n++){
            tmpbobot += selectedtasks.get(n).getPoint();
        }
        Integer bobot = 0;
        if (tmpbobot > 20) {
            bobot = tmpbobot / 10;
            minestimasi = bobot;
            if (tmp < minestimasi)
                estimasi.setText(String.valueOf(minestimasi));
        } else if (tmpbobot <= 20)
            estimasi.setText(String.valueOf(2));
        settanggal();
        settotal();
    }
    public void setRecyclerViewvisibility(){
        if (perjam && asistenrt){
            rec_Adapter.clearselectedlist();
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
        }
    }
}
