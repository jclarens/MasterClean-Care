package com.TA.MVP.appmobilemember.View.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Zackzack on 09/06/2017.
 */

public class ParentActivity extends AppCompatActivity{
    public AlertDialog.Builder abuilder;
    protected ProgressDialog progressDialog;
    public void initProgressDialog(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
    }
    public void initProgressDialog(String title, String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
    }
    public void dismissDialog(){
        if (progressDialog == null) return;
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
    public void showDialog(){
        if (progressDialog == null)
            throw new NullPointerException("Progress dialog null");
        else{
            progressDialog.show();
        }
    }
    public void showDialog(String message){
        if (progressDialog == null)
            initProgressDialog(message);
        else{
            progressDialog.setMessage(message);
        }
        showDialog();
    }
    public void abuildermessage(String msg, String title){
        abuilder = new AlertDialog.Builder(this);
        abuilder.setMessage(msg).setTitle(title);
    }
    public void showalertdialog(){
        AlertDialog dialog = abuilder.create();
        if (abuilder == null)
            throw new NullPointerException("null builder");
        else
            dialog.show();
    }


    public static void doChangeActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(_intent);
    }
    public static void doStartActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        context.startActivity(_intent);
    }
    public void setupleavefocus(View view, final Activity activity){
        if (!(view instanceof EditText)){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup)
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View innerview = ((ViewGroup) view).getChildAt(i);
                setupleavefocus(innerview, activity);
            }
    }
    public static void hideSoftKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
