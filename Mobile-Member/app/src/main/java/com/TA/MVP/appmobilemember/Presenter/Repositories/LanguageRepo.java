package com.TA.MVP.appmobilemember.Presenter.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Language;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface LanguageRepo {
    @GET("Language")
    Call<List<Language>> getLanguages();
}
