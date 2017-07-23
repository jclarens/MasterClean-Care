package com.mvp.mobile_art.Route.Repositories;


import com.mvp.mobile_art.Model.Basic.Language;
import com.mvp.mobile_art.Model.Responses.LanguageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Zackzack on 11/07/2017.
 */

public interface LanguageRepo {
    @Headers("Accept: application/json")
    @GET("api/language/")
    Call<List<Language>> getlanguages();

    @Headers("Accept: application/json")
    @POST("api/language/")
    Call<LanguageResponse> postlanguage();

    @Headers("Accept: application/json")
    @GET("api/language/{language_id}")
    Call<LanguageResponse> showlanguage(@Path("language_id") Integer language_id);

    @Headers("Accept: application/json")
    @PATCH("api/language/{language_id}")
    Call<LanguageResponse> updatelanguage(@Path("language_id") Integer language_id);

    @Headers("Accept: application/json")
    @DELETE("api/language/{language_id}")
    Call<LanguageResponse> destroylanguage(@Path("language_id") Integer language_id);

    @Headers("Accept: application/json")
    @GET("api/language/search/{param}/{text}")
    Call<List<Language>> searchlanguageByParam(@Path("param") Integer param, @Path("text") Integer text);
}
