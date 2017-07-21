package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.RecyclerAdapterListKerja;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.View.Activity.PemesananActivity;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zackzack on 18/06/2017.
 */

public class FragmentPemesanan2 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rec_LayoutManager;
    private RecyclerAdapterListKerja rec_Adapter;
    private List<OrderTask> orderTasks = new ArrayList<>();

    private Spinner prof, waktukrj;
    private LinearLayout layoutlistkerja;
    private EditText mulaitime, mulaidate, selesaitime, selesaidate, estimasi, cttntmbhn, totalbiaya;
    private TextView estimasiwaktutext;
    private Button prev,next;
    private User art;
    private ArrayAdapter arrayAdapterWaktu;
    private List<Waktu_Kerja> defaultWK = new ArrayList<>();

    private int minestimasi = 1;
    private int maxestimasi = 2;
    private int tmp;
    private DatePickerDialog datePickerDialog1;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar = Calendar.getInstance();
//    private Calendar startcalendar = Calendar.getInstance();
//    private Calendar endcalendar = Calendar.getInstance();
    int mYear, mMounth, mDay, mHour, mMinute;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-d-MM", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private Date startdate, enddate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_pemesanan2, container, false);
        art = GsonUtils.getObjectFromJson(getArguments().getString(ConstClass.ART_EXTRA), User.class);
        defaultWK = ((MasterCleanApplication)getActivity().getApplication()).getGlobalStaticData().getWaktu_kerjas();

        waktukrj = (Spinner) _view.findViewById(R.id.pms2_spinner_waktukerja);
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


        mYear = calendar.get(Calendar.YEAR);
        mMounth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mulaidate.setText(i + "-" + i1 + "-" + i2);
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                renderendtime();
            }
        }, mYear, mMounth, mDay);
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mulaitime.setText(i + ":" + i1);
                calendar.set(Calendar.HOUR,i);
                calendar.set(Calendar.MINUTE,i1);
                renderendtime();
            }
        },mHour, mMinute, false);

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
                        estimasiwaktutext.setText("Jam");
                        minestimasi = 2;
                        maxestimasi = 8;
                        estimasi.setText(String.valueOf(minestimasi));
                        break;
                    case 2:
                        //hari
                        mulaitime.setEnabled(false);
                        calendar.set(Calendar.HOUR,8);
                        calendar.set(Calendar.MINUTE,0);
                        estimasiwaktutext.setText("Hari");
                        minestimasi = 1;
                        maxestimasi = 14;
                        estimasi.setText(String.valueOf(minestimasi));
                        break;
                    case 3:
                        //bulan
                        mulaitime.setEnabled(false);
                        calendar.set(Calendar.HOUR,8);
                        calendar.set(Calendar.MINUTE,0);
                        estimasiwaktutext.setText("Bulan");
                        minestimasi = 1;
                        maxestimasi = 12;
                        estimasi.setText(String.valueOf(minestimasi));
                        break;
                }
                renderendtime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //listkerja
        recyclerView = (RecyclerView) _view.findViewById(R.id.pms2_rec_listkerja);
        rec_LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rec_LayoutManager);
        rec_Adapter = new RecyclerAdapterListKerja();
        recyclerView.setAdapter(rec_Adapter);
        rec_Adapter.setList(orderTasks);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PemesananActivity)getActivity()).doChangeFragment(1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi dulu
                ((PemesananActivity)getActivity()).doChangeFragment(3);
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
                    renderendtime();
                }
            }
        });

        return _view;
    }
    public void renderendtime(){
        startdate = calendar.getTime();
        switch (estimasiwaktutext.getText().toString()){
            case "Jam":
                calendar.add(Calendar.HOUR, Integer.valueOf(estimasi.getText().toString()));
                break;
            case "Hari":
                calendar.add(Calendar.HOUR, 8);
                break;
            case "Bulan":
                calendar.add(Calendar.MONTH, Integer.valueOf(estimasi.getText().toString()));
                calendar.add(Calendar.HOUR, 8);
                break;
        }
        enddate = calendar.getTime();
        mulaidate.setText(dateFormat.format(startdate.getTime()));
        mulaitime.setText(timeFormat.format(startdate.getTime()));
        selesaidate.setText(dateFormat.format(enddate.getTime()));
        selesaitime.setText(timeFormat.format(enddate.getTime()));

        calendar.set(Calendar.YEAR,mYear);
        calendar.set(Calendar.MONTH,mMounth);
        calendar.set(Calendar.DAY_OF_MONTH,mDay);
        calendar.set(Calendar.HOUR,mHour);
        calendar.set(Calendar.MINUTE,mMinute);
    }
}
