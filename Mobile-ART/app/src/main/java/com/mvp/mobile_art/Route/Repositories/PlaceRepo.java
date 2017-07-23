package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.Place;
import com.mvp.mobile_art.Model.Responses.PlaceResponse;

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
import retrofit2.http.Query;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface PlaceRepo {
    @Headers("Accept: application/json")
    @GET("api/place/")
    Call<List<Place>> getplaces();

    @Headers("Accept: application/json")
    @POST("api/place/")
    Call<PlaceResponse> postplace(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/place/{place_id}")
    Call<PlaceResponse> showplace(@Path("place_id") Integer place_id);

    @Headers("Accept: application/json")
    @PATCH("api/place/{place_id}")
    Call<PlaceResponse> updateplace(@Path("place_id") Integer place_id);

    @Headers("Accept: application/json")
    @DELETE("api/place/{place_id}")
    Call<PlaceResponse> destroyplace(@Path("place_id") Integer place_id);

    @Headers("Accept: application/json")
    @GET("api/place/search/{param}/{text}")
    Call<List<Place>> searchplaceByParam(@Path("param") Integer param, @Path("text") Integer text);
}
