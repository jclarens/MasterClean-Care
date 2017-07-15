package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Message;
import com.TA.MVP.appmobilemember.Model.Responses.MessageResponse;

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
 * Created by Zackzack on 15/07/2017.
 */

public interface MessageRepo {
    @Headers("Accept: application/json")
    @GET("api/message/")
    Call<List<Message>> getmessages();

    @Headers("Accept: application/json")
    @POST("api/message/")
    Call<MessageResponse> postmessage(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/message/{message_id}")
    Call<MessageResponse> showmessage(@Path("message_id") Integer message_id);

    @Headers("Accept: application/json")
    @PATCH("api/message/{message_id}")
    Call<MessageResponse> updatemessage(@Path("message_id") Integer message_id);

    @Headers("Accept: application/json")
    @DELETE("api/message/{message_id}")
    Call<MessageResponse> destroymessage(@Path("message_id") Integer message_id);

    @Headers("Accept: application/json")
    @GET("api/message/search/{param}/{text}")
    Call<List<Message>> searchmessageByParam(@Path("param") Integer param, @Path("text") Integer text);
}
