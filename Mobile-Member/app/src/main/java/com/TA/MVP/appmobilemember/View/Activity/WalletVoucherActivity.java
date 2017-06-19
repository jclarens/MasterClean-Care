package com.TA.MVP.appmobilemember.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 19/06/2017.
 */

public class WalletVoucherActivity extends ParentActivity {
    private EditText code;
    private Button batal, konfirmasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_voucher);

        code = (EditText) findViewById(R.id.vou_et_code);
        batal = (Button) findViewById(R.id.vou_btn_btl);
        konfirmasi = (Button) findViewById(R.id.vou_btn_konf);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
