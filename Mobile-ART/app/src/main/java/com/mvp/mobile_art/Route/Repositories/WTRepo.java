package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.Model.Responses.WTResponse;

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
 * Created by Zackzack on 11/07/2017.
 */

public interface WTRepo {
    @Headers("Accept: application/json")
    @GET("api/work_time/")
    Call<List<Waktu_Kerja>> getworktimes();

    @Headers("Accept: application/json")
    @POST("api/work_time/")
    Call<WTResponse> postworktime(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/work_time/{work_time_id}")
    Call<WTResponse> showworktime(@Path("work_time_id") Integer work_time_id);

    @Headers("Accept: application/json")
    @PATCH("api/work_time/{work_time_id}")
    Call<WTResponse> updateworktime(@Path("work_time_id") Integer work_time_id);

    @Headers("Accept: application/json")
    @DELETE("api/work_time/{work_time_id}")
    Call<WTResponse> destroyworktime(@Path("work_time_id") Integer work_time_id);

    @Headers("Accept: application/json")
    @GET("api/work_time/search/{param}/{text}")
    Call<List<Waktu_Kerja>> searchworktimeByParam(@Path("param") Integer param, @Path("text") Integer text);
}
