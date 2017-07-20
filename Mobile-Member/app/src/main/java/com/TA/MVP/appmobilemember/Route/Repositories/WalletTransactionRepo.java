package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Responses.WalletTransactionResponse;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Zackzack on 20/07/2017.
 */

public interface WalletTransactionRepo {
    @Headers("Accept: application/json")
    @POST("api/")
    Call<WalletTransactionResponse> getwallettransaction();
}
