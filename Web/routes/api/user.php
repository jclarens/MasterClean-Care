<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| User
|--------------------------------------------------------------------------
*/

Route::group(['prefix' => 'user', 'middleware' => ['api']], function () {
    Route::get('/', 'UserController@index');

    Route::post('/', 'UserController@store');

    Route::post('/login', 'UserController@login');

    Route::get('/{id}', 'UserController@show')->where('id', '[0-9]+');

    Route::patch('/{id}', 'UserController@update')->where('id', '[0-9]+');

    Route::delete('/{id}', 'UserController@destroy')->where('id', '[0-9]+');

    Route::get('/search/{text}', 'UserController@search');
    
    Route::get('/search/{param}/{text}', 'UserController@searchByParam');
});
