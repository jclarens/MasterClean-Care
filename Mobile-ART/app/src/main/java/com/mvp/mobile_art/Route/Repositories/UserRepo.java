package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.Model.Basic.WalletTransaction;
import com.mvp.mobile_art.Model.Responses.LoginResponse;
import com.mvp.mobile_art.Model.Responses.Token;
import com.mvp.mobile_art.Model.Responses.UserResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Jay Clarens on 7/8/2017.
 */

public interface UserRepo {
    @Headers("Accept: application/json")
    @POST("api/check_login_art")
    Call<LoginResponse> loginasisten(@Body HashMap map);

    @Headers("Accept: application/json")
    @POST("api/check_login")
    Call<UserResponse> getOwnData(@Body HashMap map);

    @Headers("Accept: application/json")
    @POST("api/user/")
    Call<UserResponse> registeruser(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/user/wallet_transaction_list/{user}")
    Call<List<WalletTransaction>> getwallettrans(@Path("user") Integer user_id);

    @Headers("Accept: application/json")
    @POST("api/logout")
    Call<UserResponse> logout();

    @Headers("Accept: application/json")
    @GET("api/user/")
    Call<List<User>> getusers();

    @Headers("Accept: application/json")
    @GET("api/user/search?user_type=2")
    Call<List<User>> getallart();

    @Headers("Accept: application/json")
    @GET("api/user/{user_id}")
    Call<User> getuser(@Path("user_id") String user_id);

    @Headers("Accept: application/json")
    @PATCH("api/user/{user_id}")
    Call<UserResponse> updateuser(@Path("user_id") Integer user_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @DELETE("api/user/{user_id}")
    Call<UserResponse> destroyuser(@Path("user_id") String user_id);

    @Headers("Accept: application/json")
    @GET("api/user/search")
    Call<List<User>> searchuser(@QueryMap Map<String, String> option);
}