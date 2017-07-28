package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.MyTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by jcla123ns on 27/07/17.
 */

public interface MyTaskRepo {
    @Headers("Accept: application/json")
    @GET("api/task_list/")
    Call<List<MyTask>> gettasks();
//    Route::get('/', 'TaskListController@index');
//
//    Route::post('/', 'TaskListController@store');
//
//    Route::get('/{task_list_id}', 'TaskListController@show')->where('task_list_id', '[0-9]+');
//
//    Route::patch('/{task_list_id}', 'TaskListController@update')->where('task_list_id', '[0-9]+');
//
//    Route::delete('/{task_list_id}', 'TaskListController@destroy')->where('task_list_id', '[0-9]+');
//
//    Route::get('/search/{param}/{text}', 'TaskListController@searchByParam');
}
