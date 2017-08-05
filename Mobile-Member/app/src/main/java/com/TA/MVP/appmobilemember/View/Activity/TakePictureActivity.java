package com.TA.MVP.appmobilemember.View.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.Model.Basic.WalletTransaction;
import com.TA.MVP.appmobilemember.Model.Responses.WalletTransactionResponse;
import com.TA.MVP.appmobilemember.R;
import com.TA.MVP.appmobilemember.Route.Repositories.WalletTransactionRepo;
import com.TA.MVP.appmobilemember.lib.api.APICallback;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.ConstClass;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 26/07/17.
 */

public class TakePictureActivity extends ParentActivity{
    private static final int PERMS_REQUEST_CODE = 123;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Button btnkonfirm, btnambil;
    private boolean picturetaken = false;
    private User user = new User();
    private Wallet wallet = new Wallet();
    private String selectedimagepath;
    private Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        Intent intent = getIntent();
        user = GsonUtils.getObjectFromJson(SharedPref.getValueString(ConstClass.USER),User.class);
        wallet = GsonUtils.getObjectFromJson(intent.getStringExtra("wallet"),Wallet.class);

        imageView = (ImageView) findViewById(R.id.take_img);
        btnkonfirm = (Button) findViewById(R.id.take_konf);
        btnambil = (Button) findViewById(R.id.take_takepic);

        btnambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        final Activity activity = this;
        btnkonfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMS_REQUEST_CODE);
            }
        });

        dispatchTakePictureIntent();

    }
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

//        File photo = null;
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (android.os.Environment.getExternalStorageState().equals(
//                android.os.Environment.MEDIA_MOUNTED)) {
//            photo = new File(android.os.Environment
//                    .getExternalStorageDirectory(), "filename");
//        } else {
//            photo = new File(getCacheDir(), "filename");
//        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
//        selectedImageUri = Uri.fromFile(photo);
//        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
            FileOutputStream fo;
            try {
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedimagepath = destination.getAbsolutePath();
            picturetaken = true;

//            new uploadFileToServerTask().execute(destination.getAbsolutePath());

//            Uri imageuri = selectedImageUri;
//            selectedimagepath = getrealpathURI(imageuri);
//            picturetaken = true;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (picturetaken) {
                        posttransaction(selectedimagepath);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Fitur tidak dapat dijalankan tanpa izin pengguna", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public void posttransaction(String path){
        initProgressDialog("Uploading..");
        showDialog();

        File file = new File(path);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//        MultipartBody.Part namaRequestBody description =
        RequestBody user_id = RequestBody.create(okhttp3.MultipartBody.FORM, user.getId().toString());
        RequestBody amt = RequestBody.create(okhttp3.MultipartBody.FORM, wallet.getAmt().toString());
        Call<WalletTransactionResponse> caller = APIManager.getRepository(WalletTransactionRepo.class).uploadtransaction(filePart, user_id, amt);
        caller.enqueue(new APICallback<WalletTransactionResponse>() {
            @Override
            public void onSuccess(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                super.onSuccess(call, response);
                dismissDialog();
                finish();
            }

            @Override
            public void onError(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WalletTransactionResponse> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                Toast.makeText(getApplicationContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
