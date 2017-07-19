package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Message;
import com.TA.MVP.appmobilemember.Model.Responses.MessageResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Zackzack on 15/07/2017.
 */

public interface MessageRepo {
    @Headers("Accept: application/json")
    @GET("api/message/get_all/{user}")
    Call<List<Message>> getallmsgfromuserid(@Path("user") String userid);

    @Headers("Accept: application/json")
    @GET("api/message/get_sender/{user}")
    Call<List<Message>> getallmsgfromsenderid(@Path("user") String userid);

    @Headers("Accept: application/json")
    @GET("api/message/get_receiver/{user}")
    Call<List<Message>> getallmsgfromreciverid(@Path("user") String userid);

    @Headers("Accept: application/json")
    @DELETE("api/message/{message_id}")
    Call<MessageResponse> deletemessage(@Path("message_id") String message_id);

    @Headers("Accept: application/json")
    @POST("api/message/")
    Call<MessageResponse> postmessage(@Body HashMap map);
}
