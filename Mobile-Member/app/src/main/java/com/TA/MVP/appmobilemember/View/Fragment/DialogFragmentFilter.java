package com.TA.MVP.appmobilemember.View.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class DialogFragmentFilter extends DialogFragment {
    private Button btntutup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.dialog_fragment_filter, container, false);
        getDialog().setTitle("Filter");
        btntutup = (Button) _view.findViewById(R.id.filter_btn_batal);
        btntutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return  _view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null)
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
