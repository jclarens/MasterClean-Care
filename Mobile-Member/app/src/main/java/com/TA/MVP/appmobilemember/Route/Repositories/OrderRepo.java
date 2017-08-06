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
    @GET("api/order/{id}")
    Call<Order> getorderById(@Path("id") Integer id);

    @Headers("Accept: application/json")
    @PATCH("api/order/{id}")
    Call<OrderResponse> patchorderById(@Path("id") Integer id, @Body HashMap map);

    @Headers("Accept: application/json")
    @POST("api/order/")
    Call<OrderResponse> postorder(@Body HashMap map);

    @Headers("Accept: application/json")
    @DELETE("api/order/{id}")
    Call<OrderResponse> deleteorderById(@Path("id") String id);

    @Headers("Accept: application/json")
    @GET("api/order/review/{art}")
    Call<List<Order>> getorderreviewByArt(@Path("art") Integer art);

    @Headers("Accept: application/json")
    @GET("api/order/art/{art}/status/{status}")
    Call<List<Order>> getordersByArtstatus(@Path("art") Integer art, @Path("status") Integer status);

    @Headers("Accept: application/json")
    @GET("api/order/member/{member}")
    Call<List<Order>> getorderByMember(@Path("member") String member);
}


