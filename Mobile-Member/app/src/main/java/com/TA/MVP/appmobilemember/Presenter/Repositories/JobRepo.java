package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.Model.Responses.JobResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface JobRepo {
    @GET("job")
    Call<List<Job>> getJobs();
    @POST("job")
    Call<JobResponse> postJob();
    @GET("job/")
    Call<JobResponse> showJob();
    @PATCH("job/")
    Call<JobResponse> updateJob();
    @DELETE("job/")
    Call<JobResponse> destroyJob();
//    Route::group(['prefix' => 'job', 'middleware' => ['api']], function () {
//        Route::get('/', 'JobController@index');
//
//        Route::post('/', 'JobController@store');
//
//        Route::get('/{id}', 'JobController@show')->where('id', '[0-9]+');
//
//        Route::patch('/{id}', 'JobController@update')->where('id', '[0-9]+');
//
//        Route::delete('/{id}', 'JobController@destroy')->where('id', '[0-9]+');
//
//        Route::get('/search/{param}/{text}', 'JobController@searchByParam');
}
