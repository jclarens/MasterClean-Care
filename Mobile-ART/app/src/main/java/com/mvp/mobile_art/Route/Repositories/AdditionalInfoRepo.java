package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.AdditionalInfo;
import com.TA.MVP.appmobilemember.Model.Responses.AdditionalInfoResponse;

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

public interface AdditionalInfoRepo {
    @Headers("Accept: application/json")
    @GET("api/additional_info/")
    Call<List<AdditionalInfo>> getadditional_infos();

    @Headers("Accept: application/json")
    @POST("api/additional_info/")
    Call<AdditionalInfoResponse> postadditional_info(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/additional_info/{info_id}")
    Call<AdditionalInfoResponse> showadditional_info(@Path("info_id") Integer info_id);

    @Headers("Accept: application/json")
    @PATCH("api/additional_info/{info_id}")
    Call<AdditionalInfoResponse> updateadditional_info(@Path("info_id") Integer info_id);

    @Headers("Accept: application/json")
    @DELETE("api/additional_info/{info_id}")
    Call<AdditionalInfoResponse> destroyadditional_info(@Path("info_id") Integer info_id);

    @Headers("Accept: application/json")
    @GET("api/additional_info/search/{param}/{text}")
    Call<List<AdditionalInfo>> searchadditional_infoByParam(@Path("param") Integer param, @Path("text") Integer text);
}
