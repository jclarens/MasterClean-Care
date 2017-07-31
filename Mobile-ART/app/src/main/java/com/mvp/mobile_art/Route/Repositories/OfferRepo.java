package com.mvp.mobile_art.Route.Repositories;

import com.mvp.mobile_art.Model.Basic.Offer;
import com.mvp.mobile_art.Model.Basic.OfferArt;
import com.mvp.mobile_art.Model.Responses.OfferResponse;

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
    Call<Offer> getofferById(@Path("id") String id);

    @Headers("Accept: application/json")
    @PATCH("api/offer/{offer_id}")
    Call<OfferResponse> patchoffer(@Path("offer_id") Integer offer_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @POST("api/offer/")
    Call<OfferResponse> postoffer(@Body HashMap map);

    @Headers("Accept: application/json")
    @POST("api/offer/{offer_id}/offer_art")
    Call<OfferResponse> postartoffer(@Path("offer_id") Integer offer_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/offer/")
    Call<List<Offer>> getoffers();

    @Headers("Accept: application/json")
    @GET("api/offer/art/{art_id}")
    Call<List<Offer>> getoffersbyart(@Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @GET("api/offer/status/{status}")
    Call<List<Offer>> getoffersbystatus(@Path("status") Integer status);

    @Headers("Accept: application/json")
    @GET("api/offer/{offer_id}/offer_art/{art_id}")
    Call<List<OfferArt>> getofferartbyid(@Path("offer_id") Integer offer_id, @Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @DELETE("api/offer/{offer_id}/offer_art/{art_id}")
    Call<OfferArt> deleteofferartbyid(@Path("offer_id") Integer offer_id, @Path("art_id") Integer art_id);

    @Headers("Accept: application/json")
    @GET("api/offer/{id}/offer_art")
    Call<List<OfferArt>> getofferarts(@Path("id") Integer offer_id);

    @Headers("Accept: application/json")
    @GET("api/offer/member/{member}")
    Call<List<Offer>> getofferByMember(@Path("member") String member);


}
