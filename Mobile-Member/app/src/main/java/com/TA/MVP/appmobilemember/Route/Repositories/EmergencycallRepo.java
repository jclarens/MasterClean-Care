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
    @POST("api/emergency_call/")
    Call<EmergencyCallResponse> postemergencycall(@Body HashMap map);
}
