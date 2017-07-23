package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.Model.Responses.MyMessageResponse;
import com.TA.MVP.appmobilemember.lib.models.GenericResponse;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Zackzack on 15/07/2017.
 */

public interface MessageRepo {

    @Headers("Accept: application/json")
    @POST("api/message/")
    Call<MyMessageResponse> postmessage(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/message/get_all/{user}")
    Call<List<MyMessage>> getallmsgfromuserid(@Path("user") String userid);

    @Headers("Accept: application/json")
    @GET("api/message/get_sender/{user}")
    Call<List<MyMessage>> getallmsgfromsenderid(@Path("user") String userid);

    @Headers("Accept: application/json")
    @GET("api/message/get_receiver/{user}")
    Call<List<MyMessage>> getallmsgfromreciverid(@Path("user") String userid);

    @Headers("Accept: application/json")
    @DELETE("api/message/{message_id}")
    Call<MyMessageResponse> deletemessage(@Path("message_id") String message_id);
}
