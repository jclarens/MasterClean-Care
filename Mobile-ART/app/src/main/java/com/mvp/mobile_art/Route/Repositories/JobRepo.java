package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.Job;
import com.mvp.mobile_art.Model.Responses.JobResponse;

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

public interface JobRepo {
    @Headers("Accept: application/json")
    @GET("api/job/")
    Call<List<Job>> getJobs();

    @Headers("Accept: application/json")
    @POST("api/job/")
    Call<JobResponse> postJob(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/job/{job_id}")
    Call<JobResponse> showJob(@Path("job_id") Integer job_id);

    @Headers("Accept: application/json")
    @PATCH("api/job/{job_id}")
    Call<JobResponse> updateJob(@Path("job_id") Integer job_id);

    @Headers("Accept: application/json")
    @DELETE("api/job/{job_id}")
    Call<JobResponse> destroyJob(@Path("job_id") Integer job_id);

    @Headers("Accept: application/json")
    @GET("api/job/search/{param}/{text}")
    Call<List<Job>> searchjobByParam(@Path("param") Integer param, @Path("text") Integer text);
}
