package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Emergencycall;
import com.TA.MVP.appmobilemember.Model.Responses.EmergencyCallResponse;

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
    @GET("api/emergency_call/")
    Call<List<Emergencycall>> getemergencycalls();

    @Headers("Accept: application/json")
    @POST("api/emergency_call/")
    Call<EmergencyCallResponse> postemergencycall(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/emergency_call/{emergency_call_id}")
    Call<EmergencyCallResponse> getemergencycall(@Path("emergency_call_id") Integer emergency_call_id);

    @Headers("Accept: application/json")
    @PATCH("api/emergency_call/{emergency_call_id}")
    Call<EmergencyCallResponse> patchemergencycall(@Path("emergency_call_id") Integer emergency_call_id);

    @Headers("Accept: application/json")
    @DELETE("api/emergency_call/{emergency_call_id}")
    Call<EmergencyCallResponse> deleteemergencycall(@Path("emergency_call_id") Integer emergency_call_id);

    @Headers("Accept: application/json")
    @GET("api/emergency_call/search/{param}/{operator}/{text}")
    Call<List<Emergencycall>> getemergencycallsearchbyparam1(@Path("param") Integer param, @Path("operator") Integer operator, @Path("text") Integer text);

    @Headers("Accept: application/json")
    @GET("api/emergency_call/search/{param}/{text}")
    Call<List<Emergencycall>> getemergencycallsearchbyparam2(@Path("param") Integer param, @Path("text") Integer text);
}
