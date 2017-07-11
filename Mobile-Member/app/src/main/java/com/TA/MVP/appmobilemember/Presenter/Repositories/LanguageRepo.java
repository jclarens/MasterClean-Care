package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Bahasa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface LanguageRepo {
    @GET("language")
    Call<List<Bahasa>> getLanguages();
}
