package com.TA.MVP.appmobilemember.View.Activity;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.CropSquareTransformation;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.UserContact;
import com.TA.MVP.appmobilemember.Model.Responses.UploadResponse;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.RoundedTransformation;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;
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
 * Created by Zackzack on 09/06/2017.
 */

public class EditProfileActivity extends ParentActivity {
    private static final int PERMS_REQUEST_CODE = 123;
    public static int PICK_PHOTO_FOR_AVATAR = 1;
    private User user;
    private Toolbar toolbar;
    private ImageView imgfoto;
    private EditText nama, alamat, notelp, notelp2;
    private Button btnsimpan, btnbatal;
    private BufferedInputStream bufferedInputStream;
    private CropSquareTransformation cropSquareTransformation = new CropSquareTransformation();
    private String selectedimagepath;
    private boolean imagechanged = false;
    private String newimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER), User.class);

        imgfoto = (ImageView) findViewById(R.id.eprof_iv_foto);
        nama = (EditText) findViewById(R.id.eprof_et_nama);
        alamat = (EditText) findViewById(R.id.eprof_et_alamat);
        notelp = (EditText) findViewById(R.id.eprof_et_notelp);
        notelp2 = (EditText) findViewById(R.id.eprof_et_notelp2);
        btnsimpan = (Button) findViewById(R.id.eprof_btn_simpan);
        btnbatal = (Button) findViewById(R.id.eprof_btn_batal);

        final Activity activity = this;
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMS_REQUEST_CODE);
            }
        });

        nama.setText(user.getName());
        try{
            alamat.setText(user.getContact().getAddress());
            notelp.setText(user.getContact().getPhone());
            notelp2.setText(user.getContact().getEmergency_numb());
        }
        catch (NullPointerException e){
            alamat.setText("-");
            notelp.setText("-");
            notelp2.setText("-");
        }

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initProgressDialog("Sedang Memperoses ...");
                showDialog();
                if (imagechanged){
                    upload(selectedimagepath);
                }
                else updateprofile();
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Picasso.with(getApplicationContext())
                .load(Settings.getRetrofitAPIUrl()+"image/small/"+user.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .transform(new RoundedTransformation(10, 0))
                .into(imgfoto);


        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_editprofile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
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

                imgfoto.setImageBitmap(cropSquareTransformation.transform(bmp));
            } catch (FileNotFoundException e) {

            }
        }
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
                dismissDialog();
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
        UserContact userContact = user.getContact();
        userContact.setAddress(alamat.getText().toString());
        userContact.setPhone(notelp.getText().toString());
        map.put("contact", userContact);
        user.setName(nama.getText().toString());
        user.getContact().setAddress(alamat.getText().toString());
        user.getContact().setPhone(notelp.getText().toString());
        user.getContact().setEmergency_numb(notelp2.getText().toString());
        if (imagechanged){
            map.put("avatar", newimage);
        }
        SharedPref.save(ConstClass.USER, GsonUtils.getJsonFromObject(user));


        Call<UserResponse> caller = APIManager.getRepository(UserRepo.class).updateuser(String.valueOf(user.getId()),map);
        caller.enqueue(new APICallback<UserResponse>() {
            @Override
            public void onSuccess(Call<UserResponse> call, Response<UserResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                Intent i = new Intent();
                setResult(ProfileActivity.RESULT_SUCCESS, i);
                finish();
            }

            @Override
            public void onError(Call<UserResponse> call, Response<UserResponse> response) {
                super.onError(call, response);
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }

            @Override
            public void onUnprocessableEntity(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnprocessableEntity(call, response);
                dismissDialog();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(getApplicationContext(),"Koneksi bermasalah.", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }

            @Override
            public void onUnauthorized(Call<UserResponse> call, Response<UserResponse> response) {
                super.onUnauthorized(call, response);
                Toast.makeText(getApplicationContext(),"Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                dismissDialog();
            }
        });
    }
}