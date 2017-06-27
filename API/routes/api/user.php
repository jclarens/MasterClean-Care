<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| User
|--------------------------------------------------------------------------
*/

Route::group(['prefix' => 'user', 'middleware' => ['api']], function () {
    Route::get('/', 'UsersController@index');

    Route::post('/', 'UsersController@store');

    Route::get('/{id}', 'UsersController@show')->where('id', '[0-9]+');

    Route::patch('/{id}', 'UsersController@update')->where('id', '[0-9]+');

    Route::delete('/{id}', 'UsersController@destroy')->where('id', '[0-9]+');

    Route::get('/search/{text}', 'UsersController@search');
    
    Route::get('/search/{param}/{text}', 'UsersController@searchByParam');
});
