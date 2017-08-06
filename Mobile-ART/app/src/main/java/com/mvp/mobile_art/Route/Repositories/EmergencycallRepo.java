package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.Emergencycall;
import com.mvp.mobile_art.Model.Responses.EmergencyCallResponse;

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

public interface EmergencycallRepo {
    @Headers("Accept: application/json")
    @POST("api/emergency_call/")
    Call<EmergencyCallResponse> postemergencycall(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/emergency_call/get/{callerId}/{status}")
    Call<Emergencycall> getemergencycall(@Path("callerId") Integer callerid, @Path("status") Integer status);

    @Headers("Accept: application/json")
    @PATCH("api/emergency_call/{emergency_call_id}")
    Call<EmergencyCallResponse> patchemergencycall(@Path("emergency_call_id") Integer emergency_call_id, @Body HashMap map);
}
