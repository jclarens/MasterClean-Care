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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mvp.mobile_art.CropSquareTransformation;
import com.mvp.mobile_art.MasterCleanApplication;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.UserContact;
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
import java.util.HashMap;

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
    private String selectedimagepath;
    private boolean imagechanged = false;
    private String newimage;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        foto = (ImageView) findViewById(R.id.foto);
        nama = (EditText) findViewById(R.id.et_nama);
        notelp = (EditText) findViewById(R.id.notelp);
        notelp2 = (EditText) findViewById(R.id.notelp2);
        simpan = (Button) findViewById(R.id.simpan);
        cttn = (EditText) findViewById(R.id.keterangan);
        norek = (EditText) findViewById(R.id.norek);
        spinnerkota = (Spinner) findViewById(R.id.spinnerkota);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_editpass);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbartitle));

        arrayAdapterkota = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, ((MasterCleanApplication)getApplication()).getGlobalStaticData().getPlaces());
        spinnerkota.setAdapter(arrayAdapterkota);

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
                .resize(200, 200)
                .transform(new RoundedTransformation(100, 0))
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
                    Toast.makeText(getApplicationContext(), "Nama dan No.telp harus diisi.", Toast.LENGTH_SHORT).show();
                }
                else if (imagechanged){
                    upload(selectedimagepath);
                } else {
                    updateprofile();
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

                foto.setImageBitmap(cropSquareTransformation.transform(bmp));
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
        user.setName(nama.getText().toString());
        user.getContact().setAddress(user.getContact().getAddress());
        user.getContact().setPhone(notelp.getText().toString());
        user.getContact().setEmergency_numb(notelp2.getText().toString());
        SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));
        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(user.getId(),map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
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
}
