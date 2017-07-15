package com.TA.MVP.appmobilemember.Route.Repositories;

import com.TA.MVP.appmobilemember.Model.Basic.Article;
import com.TA.MVP.appmobilemember.Model.Responses.ArticleResponse;

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

public interface ArticleRepo {
    @Headers("Accept: application/json")
    @GET("api/article/{article_id}/comment")
    Call<ArticleResponse> getarticlecomment(@Path("article_id") Integer article_id);

    @Headers("Accept: application/json")
    @POST("api/article/{article_id}/comment")
    Call<ArticleResponse> postarticlecomment(@Path("article_id") Integer article_id, @Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/article/{article_id}/comment/{comment_id}")
    Call<ArticleResponse> getcommentinarticle(@Path("article_id") Integer article_id, @Path("comment_id") Integer comment_id);

    @Headers("Accept: application/json")
    @PATCH("api/article/{article_id}/comment/{comment_id}")
    Call<ArticleResponse> patchcommentinarticle(@Path("article_id") Integer article_id, @Path("comment_id") Integer comment_id);

    @Headers("Accept: application/json")
    @DELETE("api/article/{article_id}/comment/{comment_id}")
    Call<ArticleResponse> deletecommentinarticle(@Path("article_id") Integer article_id, @Path("comment_id") Integer comment_id);

    @Headers("Accept: application/json")
    @GET("api/article/")
    Call<List<Article>> getarticles();

    @Headers("Accept: application/json")
    @POST("api/article/")
    Call<ArticleResponse> postarticle(@Body HashMap map);

    @Headers("Accept: application/json")
    @GET("api/article/{comment_id}")
    Call<ArticleResponse> getcomment(@Path("comment_id") Integer comment_id);

    @Headers("Accept: application/json")
    @PATCH("api/article/{comment_id}")
    Call<ArticleResponse> patchcomment(@Path("comment_id") Integer comment_id);

    @Headers("Accept: application/json")
    @DELETE("api/article/{comment_id}")
    Call<ArticleResponse> deletecomment(@Path("comment_id") Integer comment_id);

    @Headers("Accept: application/json")
    @GET("api/article/search/{param}/{text}")
    Call<List<Article>> getarticlesearchbyparam(@Path("param") Integer param, @Path("text") Integer text);
}
