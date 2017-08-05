package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Responses.UploadResponse;
import com.TA.MVP.appmobilemember.Model.Responses.WalletTransactionResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Zackzack on 20/07/2017.
 */

public interface WalletTransactionRepo {
    @Headers("Accept: application/json")
    @POST("api/")
    Call<WalletTransactionResponse> getwallettransaction();

    @Multipart
    @POST("api/wallet_transaction/image")
    Call<WalletTransactionResponse> uploadtransaction(@Part MultipartBody.Part filePart, @Part("user_id") RequestBody user_id, @Part("amt") RequestBody amt);
}
