<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

//middleware('auth:api')->
Route::group(['prefix' => 'user'], function () {
    Route::get('/', 'UsersController@index');

    Route::post('/', 'UsersController@store');

    Route::get('/{id}', 'UsersController@show')->where('id', '[0-9]+');

    Route::patch('/{id}', 'UsersController@update')->where('id', '[0-9]+');

    Route::delete('/{id}', 'UsersController@destroy')->where('id', '[0-9]+');

    Route::get('search/{text}', 'UsersController@search');
    Route::get('search/{param}/{text}', 'UsersController@searchByParam');
});
