package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Place;
import com.TA.MVP.appmobilemember.Model.Responses.PlaceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface PlaceRepo {
    @GET("place")
    Call<List<Place>> getPlaces();
    @POST("place")
    Call<PlaceResponse> postplase();
    @GET("place/")
    Call<PlaceResponse> showPlace(@Query("id") String id);
    @PATCH("place/")
    Call<PlaceResponse> updatePlaces(@Query("id") String id);
    @DELETE("place/")
    Call<PlaceResponse> destroyPlaces(@Query("id") String id);
}
