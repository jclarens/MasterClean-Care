package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.Order;

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
    @GET("api/order/art/{art}")
    Call<List<Order>> getorderByArt(@Path("art") String art);

    @Headers("Accept: application/json")
    @GET("api/order/member/{member}")
    Call<List<Order>> getorderByMember(@Path("member") String member);
}