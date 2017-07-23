package com.mvp.mobile_art.Model.Responses;


import com.mvp.mobile_art.Model.Basic.Article;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class ArticleResponse extends Response {
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
