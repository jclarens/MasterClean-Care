package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Wallet;
import com.TA.MVP.appmobilemember.lib.models.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zackzack on 09/07/2017.
 */

public interface WalletRepo {
    @GET()
    Call<GenericResponse<Wallet>> getWallet();
    @POST()
    Call<GenericResponse<Wallet>> postWallet();
    @GET("wallet")
    Call<GenericResponse<List<Wallet>>> getAllWallet();
}
