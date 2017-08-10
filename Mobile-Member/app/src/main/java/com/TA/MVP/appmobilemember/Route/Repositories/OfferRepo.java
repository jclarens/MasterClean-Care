package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Offer;
import com.TA.MVP.appmobilemember.Model.Basic.OfferArt;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.Model.Responses.OfferResponse;

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
 * Created by jcla123ns on 28/07/17.
 */

public interface OfferRepo {
    @Headers("Accept: application/json")
    @GET("api/offer/{id}")
    Call<Offer> getofferById(@Path("id") Integer id);

    @Headers("Accept: application/json")
    @PATCH("api/offer/{offer_id}")
    Call<OfferResponse> patchoffer(@Path("offer_id") Integer offer_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @PATCH("api/offer/offer_art/{offer_art_id}")
    Call<OfferResponse> patchofferart(@Path("offer_art_id") Integer offer_art_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @POST("api/offer/")
    Call<OfferResponse> postoffer(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/offer/{id}/offer_art")
    Call<List<OfferArt>> getofferarts(@Path("id") Integer offer_id);

    @Headers("Accept: application/json")
    @GET("api/offer/art/{art}")
    Call<List<Offer>> getofferByArt(@Path("art") String art);

    @Headers("Accept: application/json")
    @GET("api/offer/member/{member}")
    Call<List<Offer>> getofferByMember(@Path("member") String member);


}
