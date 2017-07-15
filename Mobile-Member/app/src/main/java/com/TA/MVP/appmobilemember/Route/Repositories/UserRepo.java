package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.Token;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;

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
 * Created by Jay Clarens on 7/8/2017.
 */

public interface UserRepo {
    @Headers("Accept: application/json")
    @POST("api/check_login")
    Call<UserResponse> checkLogin(@Body HashMap map);

    @Headers("Accept: application/json")
    @POST("me")
    Call<User> getOwnData();

    @Headers("Accept: application/json")
    @POST("api/logout")
    Call<UserResponse> logout();

    @Headers("Accept: application/json")
    @POST("oauth/token")
    Call<Token> logintoken(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/user/")
    Call<List<User>> getusers();

    @Headers("Accept: application/json")
    @POST("api/user/")
    Call<UserResponse> registeruser(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/user/{user_id}")
    Call<UserResponse> getuser(@Path("user_id") String user_id);

    @Headers("Accept: application/json")
    @PATCH("api/user/{user_id}")
    Call<UserResponse> updateuser(@Path("user_id") String user_id);

    @Headers("Accept: application/json")
    @DELETE("api/user/{user_id}")
    Call<UserResponse> destroyuser(@Path("user_id") String user_id);

    @Headers("Accept: application/json")
    @GET("job/search/{text}")
    Call<List<User>> searchuserByParam(@Path("text") Integer text);

    @Headers("Accept: application/json")
    @GET("job/search/{param}/{text}")
    Call<List<User>> searchuserByParam(@Path("param") Integer param, @Path("text") Integer text);
}