package com.mvp.mobile_art.View.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mvp.mobile_art.CropSquareTransformation;
import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.UserContact;
import com.mvp.mobile_art.Model.Basic.UserLanguage;
import com.mvp.mobile_art.Model.Basic.UserWorkTime;
import com.mvp.mobile_art.Model.Responses.UploadResponse;
import com.mvp.mobile_art.Model.Responses.UserResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.RoundedTransformation;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 11/08/17.
 */

public class EditProfileActivity extends ParentActivity {
    private static final int PERMS_REQUEST_CODE = 123;
    public static int PICK_PHOTO_FOR_AVATAR = 1;
    private EditText nama, notelp, notelp2, cttn, norek;
    private Button simpan;
    private User user = new User();
    private Spinner spinnerkota;
    private ArrayAdapter arrayAdapterkota;
    private ImageView foto;
    private BufferedInputStream bufferedInputStream;
    private CropSquareTransformation cropSquareTransformation = new CropSquareTransformation();
    private RoundedTransformation roundedTransformation = new RoundedTransformation(10000, 0);
    private String selectedimagepath;
    private boolean imagechanged = false;
    private String newimage;
    private Toolbar toolbar;
    private CheckBox cb1,cb2,cb3,indo,eng;
    private EditText nominal1,nominal2,nominal3;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private TextWatcher textWatcher1 = null;
    private TextWatcher textWatcher2 = null;
    private TextWatcher textWatcher3 = null;
    private StaticData staticData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);
        staticData = ((MasterCleanApplication)getApplication()).getGlobalStaticData();

        foto = (ImageView) findViewById(R.id.foto);
        nama = (EditText) findViewById(R.id.et_nama);
        notelp = (EditText) findViewById(R.id.notelp);
        notelp2 = (EditText) findViewById(R.id.notelp2);
        simpan = (Button) findViewById(R.id.simpan);
        cttn = (EditText) findViewById(R.id.keterangan);
        norek = (EditText) findViewById(R.id.norek);
        spinnerkota = (Spinner) findViewById(R.id.spinnerkota);
        nominal1 = (EditText) findViewById(R.id.nominal1);
        nominal2 = (EditText) findViewById(R.id.nominal2);
        nominal3 = (EditText) findViewById(R.id.nominal3);
        cb1 = (CheckBox) findViewById(R.id.checkbox1);
        cb2 = (CheckBox) findViewById(R.id.checkbox2);
        cb3 = (CheckBox) findViewById(R.id.checkbox3);
        indo = (CheckBox) findViewById(R.id.indo);
        eng = (CheckBox) findViewById(R.id.eng);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_editpass);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));

        arrayAdapterkota = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, ((MasterCleanApplication)getApplication()).getGlobalStaticData().getPlaces());
        spinnerkota.setAdapter(arrayAdapterkota);

        for (int n=0;n<user.getUser_language().size();n++){
            switch (user.getUser_language().get(n).getLanguage_id()){
                case 1:
                    indo.setChecked(true);
                    break;
                case 2:
                    eng.setChecked(true);
                    break;
            }
        }

        setcheckboxonclick(cb1,nominal1);
        setcheckboxonclick(cb2,nominal2);
        setcheckboxonclick(cb3,nominal3);

        textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nominal1.removeTextChangedListener(textWatcher1);
                if (nominal1.getText().toString().equals("")){
                    nominal1.clearFocus();
                }else {
                    Integer total = getinttotal(nominal1.getText().toString());
                    if (total == null)
                        nominal1.setText(setRP(0));
                    else nominal1.setText(setRP(total));
                    nominal1.setSelection(nominal1.getText().length());
                }
                nominal1.addTextChangedListener(textWatcher1);
            }
        };
        nominal1.setText("");
        nominal1.addTextChangedListener(textWatcher1);

        textWatcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nominal2.removeTextChangedListener(textWatcher2);
                if (nominal2.getText().toString().equals("")){
                    nominal2.clearFocus();
                }else {
                    Integer total = getinttotal(nominal2.getText().toString());
                    if (total == null)
                        nominal2.setText(setRP(0));
                    else nominal2.setText(setRP(total));
                    nominal2.setSelection(nominal2.getText().length());
                }
                nominal2.addTextChangedListener(textWatcher2);
            }
        };
        nominal2.setText("");
        nominal2.addTextChangedListener(textWatcher2);

        textWatcher3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nominal3.removeTextChangedListener(textWatcher3);
                if (nominal3.getText().toString().equals("")){
                    nominal3.clearFocus();
                }else {
                    Integer total = getinttotal(nominal3.getText().toString());
                    if (total == null)
                        nominal3.setText(setRP(0));
                    else nominal3.setText(setRP(total));
                    nominal3.setSelection(nominal3.getText().length());
                }
                nominal3.addTextChangedListener(textWatcher3);
            }
        };
        nominal3.setText("");
        nominal3.addTextChangedListener(textWatcher3);

        for (int n=0;n<user.getUser_work_time().size();n++){
            switch (user.getUser_work_time().get(n).getWork_time_id()){
                case 1:
                    cb1.setChecked(true);
                    nominal1.setEnabled(true);
                    nominal1.setText(setRP(user.getUser_work_time().get(n).getCost()));
                    break;
                case 2:
                    cb2.setChecked(true);
                    nominal2.setEnabled(true);
                    nominal2.setText(setRP(user.getUser_work_time().get(n).getCost()));
                    break;
                case 3:
                    cb3.setChecked(true);
                    nominal3.setEnabled(true);
                    nominal3.setText(setRP(user.getUser_work_time().get(n).getCost()));
                    break;
            }
        }

        nama.setText(user.getName());
        notelp.setText(user.getContact().getPhone());
        notelp2.setText(user.getContact().getEmergency_numb());
        try {
            norek.setText(user.getContact().getAcc_no());
        }catch (NullPointerException e){
            norek.setText("");
        }
        spinnerkota.setSelection(user.getContact().getCity() - 1);

        Picasso.with(getApplicationContext())
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+user.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .fit().centerCrop()
                .transform(new RoundedTransformation(1000, 0))
                .into(foto);

        final Activity activity = this;
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMS_REQUEST_CODE);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initProgressDialog("Sedang Memperoses ...");
                showDialog();
                if (nama.getText().toString().equals("") || notelp.getText().toString().equals("") || notelp2.getText().toString().equals("") || norek.getText().toString().equals("")) {
                    dismissDialog();
                    Toast.makeText(getApplicationContext(), "Nama, No.telp dan Nomor Rekening harus diisi.", Toast.LENGTH_SHORT).show();
                }
                else if ((!indo.isChecked()) && (!eng.isChecked())){
                    dismissDialog();
                    Toast.makeText(getApplicationContext(), "Harus memilih minimal satu bahasa.", Toast.LENGTH_SHORT).show();
                } else if (imagechanged){
                    upload(selectedimagepath);
                } else {
                    updateprofile();
                }
            }
        });
        setupleavefocus(findViewById(R.id.editprof_inner_layout), this);
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
    public void setcheckboxonclick(final CheckBox cb, final EditText et){
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hidekeyboard();
                clearfocus();
                if (cb.isChecked()){
                    et.setEnabled(true);
                    et.setText(setRP(0));
                    et.setSelection(et.getText().length());
                }else {
                    et.setEnabled(false);
                    et.setText("");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                bufferedInputStream = new BufferedInputStream(inputStream);
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                //geturi
                Uri imageuri = data.getData();
                selectedimagepath = getrealpathURI(imageuri);
                imagechanged = true;

                foto.setImageBitmap(roundedTransformation.transform(cropSquareTransformation.transform(bmp)));
            } catch (FileNotFoundException e) {

            }
        }
    }
    public String getrealpathURI(Uri contentURI){
        Cursor cursor = getApplicationContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
            return contentURI.getPath();
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    public void updateprofile(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", nama.getText().toString());
        map.put("description", cttn.getText().toString());
        UserContact userContact = user.getContact();
        userContact.setAddress(user.getContact().getAddress());
        userContact.setCity((spinnerkota.getSelectedItemPosition() + 1));
        userContact.setPhone(notelp.getText().toString());
        userContact.setAcc_no(norek.getText().toString());
        map.put("contact", userContact);
        if (imagechanged){
            map.put("avatar", newimage);
        }

        List<UserWorkTime> userWorkTimes = new ArrayList<>();
        if (cb1.isChecked()){
            UserWorkTime userWorkTime = new UserWorkTime();
            userWorkTime.setUser_id(user.getId());
            userWorkTime.setWork_time_id(1);
            userWorkTime.setCost(getinttotal(nominal1.getText().toString()));
            userWorkTimes.add(userWorkTime);
        }
        if (cb2.isChecked()){
            UserWorkTime userWorkTime = new UserWorkTime();
            userWorkTime.setUser_id(user.getId());
            userWorkTime.setWork_time_id(2);
            userWorkTime.setCost(getinttotal(nominal2.getText().toString()));
            userWorkTimes.add(userWorkTime);
        }
        if (cb3.isChecked()){
            UserWorkTime userWorkTime = new UserWorkTime();
            userWorkTime.setUser_id(user.getId());
            userWorkTime.setWork_time_id(3);
            userWorkTime.setCost(getinttotal(nominal3.getText().toString()));
            userWorkTimes.add(userWorkTime);
        }
        map.put("user_work_time", userWorkTimes);

        List<UserLanguage> userLanguages = new ArrayList<>();
        UserLanguage userLanguage = new UserLanguage();
        if (indo.isChecked()) {
            userLanguage.setLanguage_id(1);
            userLanguage.setUser_id(user.getId());
            userLanguages.add(userLanguage);
        }
        if (eng.isChecked()) {
            userLanguage.setLanguage_id(2);
            userLanguage.setUser_id(user.getId());
            userLanguages.add(userLanguage);
        }
        map.put("user_language", userLanguages);

        user.setName(nama.getText().toString());
        user.setContact(userContact);
        user.setUser_work_time(userWorkTimes);
        user.setUser_language(userLanguages);
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(),map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
                Intent i = new Intent();
                setResult(ProfileActivity.RESULT_SUCCESS, i);
                Toast.makeText(getApplicationContext(),"Edit profile berhasil.",Toast.LENGTH_SHORT).show();
                dismissDialog();
                finish();
            }

            @Override
            public void onUnprocessableEntity(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnprocessableEntity(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnauthorized(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnauthorized(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void upload(String path){
        File file = new File(path);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        Call<UploadResponse> caller = APIManager.getRepository(UserRepo.class).uploadimg(filePart);
        caller.enqueue(new APICallback<UploadResponse>() {
            @Override
            public void onSuccess(Call<UploadResponse> call, Response<UploadResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
            }

            @Override
            public void onCreated(Call<UploadResponse> call, Response<UploadResponse> response) {
                super.onCreated(call, response);
                newimage = response.body().getImage();
                updateprofile();
            }

            @Override
            public void onError(Call<UploadResponse> call, Response<UploadResponse> response) {
                super.onError(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();

                } else {
                    Toast.makeText(getApplicationContext(),"Fitur tidak dapat dijalankan tanpa izin pengguna", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
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
    public String setRP(Integer number){
        String tempp = "Rp. ";
        tempp = tempp + numberFormat.format(number);
        return tempp;
    }
}
