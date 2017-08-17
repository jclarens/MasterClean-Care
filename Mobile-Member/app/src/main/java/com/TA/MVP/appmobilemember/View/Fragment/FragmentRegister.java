package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.MasterCleanApplication;
import com.TA.MVP.appmobilemember.Model.Adapter.SpinnerAdapter;
import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Model.Array.ArrayBulan;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTime;
import com.TA.MVP.appmobilemember.Model.Basic.Place;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.UserContact;
import com.TA.MVP.appmobilemember.Model.Responses.LoginResponse;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.View.Activity.AuthActivity;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Zackzack on 08/06/2017.
 */

public class FragmentRegister extends Fragment {
    private EditText nama, email, katasandi, konfkatasandi, notelp, alamat, tgl;
    private Spinner spinnergender, spinnerkota, spinnerkotakelahiran, spinneragama;
    private Button btndaftar;
    private TextView tvlogin;
    private ArrayAdapter arrayAdaptergender, arrayAdapterkota;
    private SpinnerAdapter spinnerAdapteragama;
    private ArrayAgama arrayAgama=new ArrayAgama();
    private String[] genders = new String[]{"Pria","Wanita"};
    private DatePickerDialog datePickerDialog1;
    private OrderTime now = new OrderTime();
    private User user = new User();
    private Calendar calendar = Calendar.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH);
    private DateFormat tahunFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private DateFormat bulanFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
    private DateFormat tglFormat = new SimpleDateFormat("d", Locale.ENGLISH);
    private ArrayBulan arrayBulan = new ArrayBulan();
    private List<Place> places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_register, container, false);
        places = ((MasterCleanApplication) getActivity().getApplication()).getGlobalStaticData().getPlaces();

        nama = (EditText) _view.findViewById(R.id.reg_et_nama);
        email = (EditText) _view.findViewById(R.id.reg_et_email);
        katasandi = (EditText) _view.findViewById(R.id.reg_et_katasandi);
        konfkatasandi = (EditText) _view.findViewById(R.id.reg_et_konfkatasandi);
        tgl = (EditText) _view.findViewById(R.id.reg_et_tgl);
        spinnergender = (Spinner) _view.findViewById(R.id.reg_spinner_gender);
        spinneragama = (Spinner) _view.findViewById(R.id.reg_spinner_agama);
        notelp = (EditText) _view.findViewById(R.id.reg_et_notelp);
        spinnerkotakelahiran = (Spinner) _view.findViewById(R.id.reg_spinner_kotakelahiran);
        spinnerkota = (Spinner) _view.findViewById(R.id.reg_spinner_kota);
        alamat = (EditText) _view.findViewById(R.id.reg_et_alamat);
        btndaftar = (Button) _view.findViewById(R.id.reg_btn_daftar);
        tvlogin = (TextView) _view.findViewById(R.id.reg_tv_login);

        setwaktusekarang();
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                tgl.setText(i2 + " - " + i1 + " - " + i);
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                tgl.setText(costumedateformat(calendar.getTime()));
            }
        }, now.year-27, now.month, now.day);
        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog1.show();
            }
        });

        arrayAdaptergender = new ArrayAdapter(getContext(), R.layout.spinner_item, genders);
        spinnergender.setAdapter(arrayAdaptergender);
        arrayAdapterkota = new ArrayAdapter(getContext(), R.layout.spinner_item, places);
        spinnerkota.setAdapter(arrayAdapterkota);
        arrayAdapterkota = new ArrayAdapter(getContext(), R.layout.spinner_item, places);
        spinnerkotakelahiran.setAdapter(arrayAdapterkota);
        spinnerAdapteragama = new SpinnerAdapter(getContext(), arrayAgama.getArrayList());
        spinneragama.setAdapter(spinnerAdapteragama.getArrayAdapter());

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()) {
                    ((AuthActivity)getActivity()).initProgressDialog("Mendaftarkan");
                    ((AuthActivity)getActivity()).showDialog();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", nama.getText().toString());
                    map.put("email", email.getText().toString());
                    map.put("password", katasandi.getText().toString());
                    map.put("gender", String.valueOf((spinnergender.getSelectedItemPosition() + 1)));
                    map.put("born_place", places.get((spinnergender.getSelectedItemPosition() + 1)).getName());
                    map.put("born_date", dateFormat.format(calendar.getTime()));
                    map.put("religion", String.valueOf(spinneragama.getSelectedItemPosition() + 1));
                    map.put("role_id", String.valueOf(2));
                    map.put("status", String.valueOf(1));
                    map.put("activation", String.valueOf(1));
                    if ((spinnergender.getSelectedItemPosition() + 1) == 1){
                        map.put("avatar", "users/profile3.png");
                    } else map.put("avatar", "users/profile1.png");
//                    map.put("user_wallet", 0);
                    UserContact userContact = new UserContact();
                    userContact.setAddress(alamat.getText().toString());
                    userContact.setCity((spinnerkota.getSelectedItemPosition() + 1));
                    userContact.setPhone(notelp.getText().toString());
                    userContact.setEmergency_numb("082168360303");
                    userContact.setAcc_no("");
                    userContact.setLocation("3.584949, 98.672400");//harusnya get location
                    map.put("contact", userContact);//cek lg
                    Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).registeruser(map);
                    caller.enqueue(new APICallback<UserResponse>() {
                        @Override
                        public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                            super.onSuccess(call, response);
                            getToken(email.getText().toString(), katasandi.getText().toString());
                        }

                        @Override
                        public void onUnprocessableEntity(Call<UserResponse> call, Response<UserResponse> response) {
                            super.onUnprocessableEntity(call, response);
                            ((AuthActivity)getActivity()).dismissDialog();
                        }

                        @Override
                        public void onError(Call<UserResponse> call, Response<UserResponse> response) {
                            super.onError(call, response);
                            ((AuthActivity)getActivity()).dismissDialog();
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            super.onFailure(call, t);
                            ((AuthActivity)getActivity()).dismissDialog();
                        }
                    });
                }
            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)getActivity()).doChangeFragmentlogin();
            }
        });

        return _view;
    }
    public void getToken(String email, String pass){
        ((AuthActivity) getActivity()).showDialog("Logging in");
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", pass);
        Call<LoginResponse> caller = APIManager.getRepository(UserRepo.class).loginmember(map);
        caller.enqueue(new APICallback<LoginResponse>() {
            @Override
            public void onSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onSuccess(call, response);
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                SharedPref.save(SharedPref.ACCESS_TOKEN, response.body().getToken().getAccess_token());
                Intent i = new Intent();
                i.putExtra(ConstClass.USER, GsonUtils.getJsonFromObject(response.body().getUser()));
                ((AuthActivity) getActivity()).dismissDialog();
                ((AuthActivity)getActivity()).dofinishActivity(i);
            }

            @Override
            public void onUnauthorized(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onUnauthorized(call, response);
                ((AuthActivity) getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Email atau katasandi salah.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onError(call, response);
                ((AuthActivity) getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                super.onFailure(call, t);
                ((AuthActivity) getActivity()).dismissDialog();
                Toast.makeText(getContext(), "Koneksi bermasalah silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setwaktusekarang(){
        calendar = Calendar.getInstance();
        now.year = calendar.get(Calendar.YEAR);
        now.month = calendar.get(Calendar.MONTH);
        now.day = calendar.get(Calendar.DAY_OF_MONTH);
        now.hour = calendar.get(Calendar.HOUR);
        now.minute = calendar.get(Calendar.MINUTE);
    }
    public boolean valid(){
        if (nama.getText().toString().equals("") || email.getText().toString().equals("") || notelp.getText().toString().equals("") || alamat.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Data tidak lengkap.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidEmail(email.getText().toString())){
            Toast.makeText(getContext(), "Input Email tidak benar.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (katasandi.getText().toString().equals("") || konfkatasandi.getText().toString().equals("")){
            Toast.makeText(getContext(), "Katasandi belum diidsi.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!katasandi.getText().toString().equals(konfkatasandi.getText().toString())){
            Toast.makeText(getContext(), "Konfirmasi katasandi tidak sesuai."+katasandi.getText().toString()+konfkatasandi.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public String costumedateformat(Date date){
        String bulan = arrayBulan.getArrayList().get(Integer.parseInt(bulanFormat.format(date))-1);
        return tglFormat.format(date) + " " + bulan + " " + tahunFormat.format(date);
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
