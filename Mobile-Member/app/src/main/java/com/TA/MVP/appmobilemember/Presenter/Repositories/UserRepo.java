package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Array.ArrayAgama;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.UserResponse;
import com.TA.MVP.appmobilemember.lib.models.GenericResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.path;

/**
 * Created by Jay Clarens on 7/8/2017.
 */

public interface UserRepo {

    @POST("check_login")
    Call<UserResponse> checkLogin(@Body HashMap map);

    @POST("logout")
    Call<UserResponse> logout();

    @GET("user")
    Call<List<User>> getUsers();

    @POST("user")
    Call<UserResponse> registerMember();

    @GET("user/")
    Call<UserResponse> getUser(@Query("id") String id);

    @PATCH("user/")
    Call<UserResponse> update(@Query("id") String id);

    @DELETE("user/")
    Call<UserResponse> destroy(@Query("id") String id);

//    @GET("user/search/{text}")
//    Call<List<User>> search(@Query("text") String text);
//
//    @GET("user/search/{param}/{text}")
//    Call<List<User>> searchbyparam(@Path("param") String param, @Path("text") String text);
}
