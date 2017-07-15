package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Article;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
