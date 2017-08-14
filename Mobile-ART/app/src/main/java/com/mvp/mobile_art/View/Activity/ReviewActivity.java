package com.mvp.mobile_art.View.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mvp.mobile_art.Model.Basic.Order;
import com.mvp.mobile_art.Model.Basic.ReviewOrder;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Responses.ReviewOrderResponse;
import com.mvp.mobile_art.R;
import com.mvp.mobile_art.Route.Repositories.ReviewOrderRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.View.Fragment.FragmentMembermini;
import com.mvp.mobile_art.lib.api.APICallback;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.utils.ConstClass;
import com.mvp.mobile_art.lib.utils.GsonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class ReviewActivity extends ParentActivity{
//    private FragmentAsistenmini fragmentAsistenmini;
    private RatingBar ratingBar;
    private EditText editText;
    private Button kembali;
    private Order order;
    private Toolbar toolbar;
    private FragmentMembermini fragmentMembermini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Intent intent = getIntent();
        order = GsonUtils.getObjectFromJson(intent.getStringExtra(ConstClass.ORDER_EXTRA), Order.class);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        editText = (EditText) findViewById(R.id.editText);
        kembali = (Button) findViewById(R.id.button_kembali);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Review");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getreview();
    }
    public void getreview(){
        initProgressDialog("Sedang memperoses...");
        showDialog();
        Call<ReviewOrder> caller = APIManager.getRepository(ReviewOrderRepo.class).getriview(order.getId());
        caller.enqueue(new APICallback<ReviewOrder>() {
            @Override
            public void onSuccess(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onSuccess(call, response);
                ratingBar.setIsIndicator(true);
                editText.setEnabled(false);
                ratingBar.setRating(response.body().getRate());
                editText.setText(response.body().getRemark());
                getuser(order.getMember_id());
            }

            @Override
            public void onNotFound(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onNotFound(call, response);
                dismissDialog();

            }

            @Override
            public void onError(Call<ReviewOrder> call, Response<ReviewOrder> response) {
                super.onError(call, response);
                dismissDialog();
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ReviewOrder> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
//                Toast.makeText(getApplicationContext(),"Koneksi bermasalah, silahkan coba lagi", Toast.LENGTH_SHORT).show();
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
    public void getuser(Integer id){
        Call<User> caller = APIManager.getRepository(UserRepo.class).getuser(id.toString());
        caller.enqueue(new APICallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                loadmini(response.body());
                dismissDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                dismissDialog();
                abuildermessage("Koneksi bermasalah. Coba lagi?","Pemberitahuan");
                abuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getuser(order.getMember_id());
                    }
                });
                abuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }
    public void loadmini(User member){
        fragmentMembermini = new FragmentMembermini();
        Bundle b = new Bundle();
        b.putString(ConstClass.MEMBER_EXTRA, GsonUtils.getJsonFromObject(member));
        fragmentMembermini.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_member, fragmentMembermini).commit();
    }
}
