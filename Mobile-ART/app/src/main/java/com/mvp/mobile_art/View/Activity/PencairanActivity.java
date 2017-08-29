package com.mvp.mobile_art.View.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.WalletTransaction;
import com.mvp.mobile_art.Model.Responses.WalletTransactionResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.WalletTransactionRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 17/08/17.
 */

public class PencairanActivity extends ParentActivity {
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private EditText norek, nominal;
    private Button tarik;
    private Toolbar toolbar;
    private TextWatcher textWatcher = null;
    private Integer total = 0;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencairan);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        norek = (EditText) findViewById(R.id.norek);
        nominal = (EditText) findViewById(R.id.nominal);
        tarik = (Button) findViewById(R.id.tarik);

        if (user.getContact().getAcc_no() != null)
            norek.setText(user.getContact().getAcc_no());
        else {
            Toast.makeText(getApplicationContext(), "Anda belum mendaftarkan nomor rekening.", Toast.LENGTH_SHORT).show();
            finish();
        }

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nominal.removeTextChangedListener(textWatcher);
                total = getinttotal(nominal.getText().toString());
                if (total == null)
                    nominal.setText(setRP(0));
                else nominal.setText(setRP(total));
                nominal.setSelection(nominal.getText().length());
                nominal.addTextChangedListener(textWatcher);
            }
        };
        nominal.setText(setRP(0));
        nominal.addTextChangedListener(textWatcher);

        tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidekeyboard();
                if (total < 50.000){
                    Toast.makeText(getApplicationContext(), "Jumlah penarikan harus lebih besar dari "+setRP(50000), Toast.LENGTH_SHORT).show();
                }else if (total > user.getUser_wallet().getAmt()){
                    Toast.makeText(getApplicationContext(), "Tidak dapat melakukan penarikan. Jumlah wallet anda "+setRP(user.getUser_wallet().getAmt()), Toast.LENGTH_SHORT).show();
                }
                else {
                    abuildermessage("Penarikan " + setRP(total) + " akan dikirim ke rekening:" + user.getContact().getAcc_no(),"Konfirmasi");
                    abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            posttransaksi();
                        }
                    });
                    abuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    showalertdialog();
                }
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Penarikan Wallet");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));
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
        string = string.replace(",", "");
        Integer result = 0;
        try{
            result = Integer.valueOf(string);
        }catch (NumberFormatException e){
            return 0;
        }
        return result;
    }
    public void posttransaksi(){
        initProgressDialog("Loading...");
        showDialog();
        HashMap<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        DateFormat getdateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm", Locale.ENGLISH);
        map.put("user_id", user.getId());
        map.put("amount", total);
        map.put("trc_type", 1);
        map.put("trc_time", getdateFormat.format(calendar.getTime()));
        map.put("acc_no", user.getContact().getAcc_no());
        map.put("status", 0);
        Call<WalletTransactionResponse> caller = APIManager.getRepository(WalletTransactionRepo.class).postwallettransaction(map);
        caller.enqueue(new APICallback<WalletTransactionResponse>() {
            @Override
            public void onSuccess(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                abuildermessage("Penarikan anda sedang kami peroses. Silahkan lihat status penarikan anda di halaman Status Transaksi.","Pemberitahuan");
                abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                showalertdialog();
            }

            @Override
            public void onError(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WalletTransactionResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
