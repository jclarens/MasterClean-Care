package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerja2;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Array.ArrayHari;
import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTime;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.MyTaskRepo;
import com.TA.MVP.appmobilemember.View.Activity.PermintaanActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 25/07/17.
 */

public class FragmentPermintaan2 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerja2 rec_Adapter;
    private List<MyTask> myTasks = new ArrayList<>();
    private Offer offer = new Offer();
    private Bundle bundle = new Bundle();

    private Spinner prof, waktukrj;
    private LinearLayout layoutlistkerja;
    private EditText mulaitime, mulaidate, selesaitime, selesaidate, estimasi, cttntmbhn, totalbiaya;
    private TextView estimasiwaktutext;
    private Button prev,next;
    private ArrayAdapter arrayAdapterWaktu;
    private ArrayAdapter arrayAdapterProfesi;
    private List<Waktu_Kerja> defaultWK = new ArrayList<>();
    private List<Job> defaultProf = new ArrayList<>();
    private TextWatcher textWatcher = null;

    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private int minestimasi = 1;
    private int maxestimasi = 2;
    private int tmp;

    private DateFormat fixFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
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
    private OrderTime waktumulai = new OrderTime();
    private OrderTime waktuselesai = new OrderTime();
    private ArrayHari arrayHari = new ArrayHari();
    private ArrayBulan arrayBulan = new ArrayBulan();
    private Date startdate, enddate;

    private int status = 1;
    private boolean valid = false;
    private Date tempdate;
    private String fixstart, fixend;
    private Integer artcost = 0;
    private Integer total = 0;
    private boolean asistenrt = false;
    private boolean perjam = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_permintaan2, container, false);
        offer = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.OFFER_EXTRA), Offer.class);
        defaultWK = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getWaktu_kerjas();
        defaultProf = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getJobs();

        waktukrj = (Spinner) _view.findViewById(R.id.pmr2_spinner_waktukerja);
        prof = (Spinner) _view.findViewById(R.id.pmr2_spinner_profesi);
        recyclerView = (RecyclerView) _view.findViewById(R.id.pmr2_rec_listkerja);
        mulaitime = (EditText) _view.findViewById(R.id.pmr2_et_mulaitime);
        mulaidate = (EditText) _view.findViewById(R.id.pmr2_et_mulaidate);
        selesaitime = (EditText) _view.findViewById(R.id.pmr2_et_selesaitime);
        selesaidate = (EditText) _view.findViewById(R.id.pmr2_et_selesaidate);
        estimasi = (EditText) _view.findViewById(R.id.pmr2_et_estimasi);
        cttntmbhn = (EditText) _view.findViewById(R.id.pmr2_et_cttn);
        totalbiaya = (EditText) _view.findViewById(R.id.pmr2_et_total);
        estimasiwaktutext = (TextView) _view.findViewById(R.id.pmr2_tv_estimasiwaktu);
        prev = (Button) _view.findViewById(R.id.pmr2_btn_prev);
        next = (Button) _view.findViewById(R.id.pmr2_btn_next);

        setwaktusekarang();
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mulaidate.setText(i + "-" + i2 + "-" + i1);
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
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

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                totalbiaya.removeTextChangedListener(textWatcher);
                total = getinttotal(totalbiaya.getText().toString());
                if (total == null)
                    totalbiaya.setText(setRP(0));
                else totalbiaya.setText(setRP(total));
                totalbiaya.setSelection(totalbiaya.getText().length());
                totalbiaya.addTextChangedListener(textWatcher);
            }
        };
        totalbiaya.addTextChangedListener(textWatcher);

        //spinner WK
        arrayAdapterWaktu = new ArrayAdapter(getContext(), R.layout.spinner_item, defaultWK);
        waktukrj.setAdapter(arrayAdapterWaktu);
        waktukrj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i+1){
                    case 1:
                        //jam
                        status = 1;
                        mulaitime.setEnabled(true);
                        estimasiwaktutext.setText("Jam");
                        minestimasi = 2;
                        maxestimasi = 8;
                        estimasi.setEnabled(false);
                        estimasi.setText(String.valueOf(minestimasi));
                        rec_Adapter.setshowtask(1);
                        offer.setWork_time_id(1);
                        perjam = true;
                        break;
                    case 2:
                        //hari
                        status = 2;
                        mulaitime.setEnabled(false);
                        estimasiwaktutext.setText("Hari");
                        minestimasi = 1;
                        maxestimasi = 14;
                        estimasi.setEnabled(true);
                        estimasi.setText(String.valueOf(minestimasi));
                        offer.setWork_time_id(2);
                        perjam = false;
                        break;
                    case 3:
                        //bulan
                        status = 3;
                        mulaitime.setEnabled(false);
                        estimasiwaktutext.setText("Bulan");
                        minestimasi = 1;
                        maxestimasi = 12;
                        estimasi.setEnabled(true);
                        estimasi.setText(String.valueOf(minestimasi));
                        offer.setWork_time_id(3);
                        perjam = false;
                        break;
                }
                setRecyclerViewvisibility();
                settanggal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //listkerja
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerja2(FragmentPermintaan2.this);
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setFullTasks(myTasks);
        loadlist();

        //spinner prof
        arrayAdapterProfesi = new ArrayAdapter(getContext(), R.layout.spinner_item, defaultProf);
        prof.setAdapter(arrayAdapterProfesi);
        prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i+1){
                    case 1:
                        rec_Adapter.setshowtask(1);
                        asistenrt = true;
                        offer.setJob_id(1);
                        break;
                    case 2:
                        rec_Adapter.setshowtask(2);
                        asistenrt = false;
                        offer.setJob_id(2);
                        break;
                    case 3:
                        rec_Adapter.setshowtask(3);
                        asistenrt = false;
                        offer.setJob_id(3);
                        break;
                    case 4:
                        rec_Adapter.setshowtask(4);
                        asistenrt = false;
                        offer.setJob_id(4);
                        break;
                }
                setRecyclerViewvisibility();
                settanggal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.save(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offer));
                ((PermintaanActivity)getActivity()).doChangeFragment(1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validasi()){
                    //save data from this fragment ----------------------------------------------------------------------------------------------------
                    offer.setCost(total);
                    offer.setStart_date(fixstart);
                    offer.setEnd_date(fixend);
                    try {
                        offer.setRemark(cttntmbhn.getText().toString());
                    }catch (NullPointerException e){
                        offer.setRemark("");
                    }
                    offer.setStatus(0);
                    List<MyTask> templist = rec_Adapter.getselectedtasklist();
                    List<OrderTask> newlist = new ArrayList<>();
                    for (int n=0; n < templist.size(); n++){
                        OrderTask orderTask = new OrderTask();
                        orderTask.setTask_list_id(templist.get(n).getId());
                        Log.d("ADD list",orderTask.getTask_list_id() + " and "+ templist.get(n).getId());
                        newlist.add(orderTask);
                        orderTask.setStatus(0);
                    }
                    offer.setOffer_task_list(newlist);
                    SharedPref.save(ConstClass.OFFER_EXTRA, GsonUtils.getJsonFromObject(offer));
                    ((PermintaanActivity)getActivity()).doChangeFragment(3);
                }
            }
        });

        estimasi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    tmp = Integer.valueOf(estimasi.getText().toString());
                    if (tmp > maxestimasi)
                        estimasi.setText(String.valueOf(maxestimasi));
                    else if (tmp < minestimasi)
                        estimasi.setText(String.valueOf(minestimasi));
                    settanggal();
                }
            }
        });


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
        switch (estimasiwaktutext.getText().toString()){
            case "Jam":
                break;
            case "Hari":
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                break;
            case "Bulan":
                calendar.set(Calendar.HOUR_OF_DAY,8);
                calendar.set(Calendar.MINUTE,0);
                break;
        }
        setwaktutemp();
        startdate = calendar.getTime();
        switch (estimasiwaktutext.getText().toString()){
            case "Jam":
                calendar.add(Calendar.HOUR_OF_DAY, Integer.valueOf(estimasi.getText().toString()));
                break;
            case "Hari":
                calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(estimasi.getText().toString())-1);
                calendar.add(Calendar.HOUR_OF_DAY, 9);
                break;
            case "Bulan":
                calendar.add(Calendar.MONTH, Integer.valueOf(estimasi.getText().toString()));
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
        if (offer.getJob_id().equals(1) && offer.getWork_time_id().equals(1) && rec_Adapter.getselectedtasklist().size() < 1){
            Toast.makeText(getContext(), "Pilih 1 list kerja atau lebih", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startdate.before(getbatassekarang())){
            Toast.makeText(getContext(), "Harap memesan untuk 2 jam kedepan", Toast.LENGTH_SHORT).show();
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
        if (total < 10000){
            Toast.makeText(getContext(), "Biaya terlalu rendah.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public String costumedateformat(Date date){
//        String hari = arrayHari.getArrayList().get(Integer.parseInt(hariFormat.format(date)));
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date)));
        // Senin, Januari 30
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
    public Integer getinttotal(String string){
        string = string.replace("R", "");
        string = string.replace("p", "");
        string = string.replace(".", "");
        string = string.replace(" ", "");
//        string = string.replace(".00", "");
        string = string.replace(",", "");
        Integer result = 0;
        try{
            result = Integer.valueOf(string);
        }catch (NumberFormatException e){
            return 0;
        }
        return result;
    }
    public void loadlist(){
        ((PermintaanActivity)getActivity()).initProgressDialog("Sedang memuat data . . ");
        ((PermintaanActivity)getActivity()).showDialog();
        Call<List<MyTask>> caller = APIManager.getRepository(MyTaskRepo.class).gettasks();
        caller.enqueue(new APICallback<List<MyTask>>() {
            @Override
            public void onSuccess(Call<List<MyTask>> call, Response<List<MyTask>> response) {
                super.onSuccess(call, response);
                myTasks = response.body();
                rec_Adapter.setFullTasks(myTasks);
                rec_Adapter.setshowtask(1);
                ((PermintaanActivity)getActivity()).dismissDialog();
            }

            @Override
            public void onFailure(Call<List<MyTask>> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getContext(),"Koneksi bermasalah.", Toast.LENGTH_SHORT).show();
                ((PermintaanActivity)getActivity()).dismissDialog();
                //dosomething here pls
            }
        });

    }

    public void setbobot(){
        List<MyTask> selectedtasks = rec_Adapter.getselectedtasklist();
        Integer tmpbobot = 0;
        for(int n=0; n<selectedtasks.size();n++){
            tmpbobot += selectedtasks.get(n).getPoint();
        }
//        Toast.makeText(getContext(), "Bobot :" + tmpbobot, Toast.LENGTH_SHORT).show();
        Integer bobot = 0;
        if (tmpbobot > 20) {
            bobot = tmpbobot / 10;
            estimasi.setText(bobot.toString());
        } else if (tmpbobot <= 20)
            estimasi.setText(String.valueOf(2));
        settanggal();
    }
    public void setRecyclerViewvisibility(){
        if (perjam && asistenrt){
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            rec_Adapter.clearselectedlist();
        }
    }
}
