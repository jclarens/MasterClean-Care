package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface WTRepo {
    @GET("Language")
    Call<List<Waktu_Kerja>> getAllWT();
}
