package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.ReviewOrder;
import com.TA.MVP.appmobilemember.Model.Responses.ReviewOrderResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jcla123ns on 28/07/17.
 */

public interface ReviewOrderRepo {
    @Headers("Accept: application/json")
    @POST("api/order/{order_id}/review_order")
    Call<ReviewOrderResponse> postriview(@Body HashMap map, @Path("order_id") Integer orderid);

    @Headers("Accept: application/json")
    @GET("api/order/{order_id}/review_order")
    Call<ReviewOrder> getriview(@Path("order_id") Integer orderid);

//    Route::get('/{order_id}/review_order', 'ReviewOrderController@index')->where('order_id', '[0-9]+');
//
//    Route::post('/{order_id}/review_order', 'ReviewOrderController@store')->where('order_id', '[0-9]+');
}
