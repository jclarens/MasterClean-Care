package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class Comment {
    private Article article;
    private User user;
    private String comment;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
