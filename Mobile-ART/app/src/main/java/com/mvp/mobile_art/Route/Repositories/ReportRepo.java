package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Responses.ReportResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jcla123ns on 10/08/17.
 */

public interface ReportRepo {
    @Headers("Accept: application/json")
    @POST("api/report/")
    Call<ReportResponse> postreport(@Body HashMap map);
}
