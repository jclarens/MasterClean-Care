package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.Model.Responses.WalletResponse;

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
 * Created by Zackzack on 09/07/2017.
 */

public interface WalletRepo {
    @Headers("Accept: application/json")
    @GET("api/wallet/")
    Call<List<Wallet>> getwallets();

    @Headers("Accept: application/json")
    @POST("api/wallet/")
    Call<WalletResponse> postwallet(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/wallet/{wallet_id}")
    Call<WalletResponse> showwallet(@Path("wallet_id") Integer wallet_id);

    @Headers("Accept: application/json")
    @PATCH("api/wallet/{wallet_id}")
    Call<WalletResponse> updatewallet(@Path("wallet_id") Integer wallet_id);

    @Headers("Accept: application/json")
    @DELETE("api/wallet/{wallet_id}")
    Call<WalletResponse> destroywallet(@Path("wallet_id") Integer wallet_id);

    @Headers("Accept: application/json")
    @GET("api/wallet/search/{param}/{text}")
    Call<List<Wallet>> searchwalletByParam(@Path("param") Integer param, @Path("text") Integer text);
}
