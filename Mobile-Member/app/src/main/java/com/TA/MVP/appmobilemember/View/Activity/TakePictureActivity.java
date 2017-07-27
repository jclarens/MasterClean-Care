package com.TA.MVP.appmobilemember.View.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by jcla123ns on 26/07/17.
 */

public class TakePictureActivity extends ParentActivity{
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView imageView;
    private Button btnkonfirm, btnambil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        imageView = (ImageView) findViewById(R.id.take_img);
        btnkonfirm = (Button) findViewById(R.id.take_konf);
        btnambil = (Button) findViewById(R.id.take_takepic);


        btnambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        btnkonfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Fitur masih dlm pengembangan", Toast.LENGTH_SHORT).show();
            }
        });

        dispatchTakePictureIntent();

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
