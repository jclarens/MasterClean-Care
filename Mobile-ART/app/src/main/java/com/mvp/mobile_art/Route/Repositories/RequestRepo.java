package com.mvp.mobile_art.Route.Repositories;

import com.mvp.mobile_art.Model.Basic.Request;
import com.mvp.mobile_art.Model.Responses.RequestResponse;

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

public interface RequestRepo {
    @Headers("Accept: application/json")
    @GET("api/request/{request_id}/requested_art")
    Call<List<Request>> getrequestingarts(@Path("request_id") Integer art_id);

    @Headers("Accept: application/json")
    @POST("api/request/{request_id}/requested_art")
    Call<RequestResponse> postrequestingart(@Path("request_id") Integer art_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/request/{request_id}/requested_art/{art_id}")
    Call<RequestResponse> showrequestingart(@Path("request_id") Integer request_id, @Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @PATCH("api/request/{request_id}/requested_art/{art_id}")
    Call<RequestResponse> updaterequestingart(@Path("request_id") Integer request_id, @Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @DELETE("api/request/{request_id}/requested_art/{art_id}")
    Call<RequestResponse> destroyrequestingart(@Path("request_id") Integer request_id, @Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @GET("api/request/")
    Call<List<Request>> getrequests();

    @Headers("Accept: application/json")
    @POST("api/request/")
    Call<RequestResponse> postrequest(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/request/{art_id}")
    Call<RequestResponse> showrequest(@Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @PATCH("api/request/{art_id}")
    Call<RequestResponse> updaterequest(@Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @DELETE("api/request/{art_id}")
    Call<RequestResponse> destroyrequest(@Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @GET("api/request/search/{param}/{text}")
    Call<List<Request>> searchrequestByParam(@Path("param") Integer param, @Path("text") Integer text);
}
