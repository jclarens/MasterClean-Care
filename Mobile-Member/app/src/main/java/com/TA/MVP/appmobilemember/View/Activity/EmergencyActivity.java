package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 19/06/2017.
 */

public class EmergencyActivity extends ParentActivity {
    private Button tutup;
    private EditText code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        code = (EditText) findViewById(R.id.sos_et_code);
        tutup = (Button) findViewById(R.id.sos_btn_tutup);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validasi code
                finish();
            }
        });
    }
}
