package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Order;
import com.TA.MVP.appmobilemember.Model.Basic.OrderTask;
import com.TA.MVP.appmobilemember.Model.Responses.OrderResponse;
import com.TA.MVP.appmobilemember.Model.Responses.TaskResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Zackzack on 15/07/2017.
 */

public interface OrderRepo {
    @Headers("Accept: application/json")
    @GET("api/order/{order_id}/task_list")
    Call<List<OrderTask>> gettasklistinorder(@Path("order_id") Integer order_id);

    @Headers("Accept: application/json")
    @POST("api/order/{order_id}/task_list")
    Call<TaskResponse> posttasklistinorder(@Path("order_id") Integer order_id);

    @Headers("Accept: application/json")
    @GET("api/order/{order_id}/task_list/{task_list_id}")
    Call<TaskResponse> gettaskinorder(@Path("order_id") Integer order_id, @Path("task_list_id") Integer task_list_id);

    @Headers("Accept: application/json")
    @PATCH("api/order/{order_id}/task_list/{task_list_id}")
    Call<TaskResponse> patchtaskinorder(@Path("order_id") Integer order_id, @Path("task_list_id") Integer task_list_id);

    @Headers("Accept: application/json")
    @DELETE("api/order/{order_id}/task_list/{task_list_id}")
    Call<TaskResponse> deletetaskinorder(@Path("order_id") Integer order_id, @Path("task_list_id") Integer task_list_id);

//    @Headers("Accept: application/json")
//    @GET("api/{order_id}/review_order")
//    Call<TaskResponse> gettaskinorder(@Path("order_id") Integer order_id);

    @Headers("Accept: application/json")
    @GET("api/order/")
    Call<List<Order>> getorders();

    @Headers("Accept: application/json")
    @POST("api/order/")
    Call<OrderResponse> postorder(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/order/{task_list_id}")
    Call<OrderResponse> showtasklist(@Path("task_list_id") Integer order_id);

    @Headers("Accept: application/json")
    @PATCH("api/order/{task_list_id}")
    Call<OrderResponse> updatetasklist(@Path("task_list_id") Integer order_id);

    @Headers("Accept: application/json")
    @DELETE("api/order/{task_list_id}")
    Call<OrderResponse> destroytasklist(@Path("task_list_id") Integer order_id);

    @Headers("Accept: application/json")
    @GET("api/order/search/{param}/{text}")
    Call<List<Order>> searchorderByParam(@Path("param") Integer param, @Path("text") Integer text);
}
//    Route::get('/{order_id}/review_order', 'ReviewOrderController@index')->where('order_id', '[0-9]+');
//
//    Route::post('/{order_id}/review_order', 'ReviewOrderController@store')->where('order_id', '[0-9]+');
//
//    Route::get('/{order_id}/review_order/{task_list_id}', 'ReviewOrderController@show')->where('order_id', '[0-9]+')->where('task_list_id', '[0-9]+');
//
//    Route::patch('/{order_id}/review_order/{task_list_id}', 'ReviewOrderController@update')->where('order_id', '[0-9]+')->where('task_list_id', '[0-9]+');
//
//    Route::delete('/{order_id}/review_order/{task_list_id}', 'ReviewOrderController@destroy')->where('order_id', '[0-9]+')->where('task_list_id', '[0-9]+');





// +++ ++ + + ++ + + + order link