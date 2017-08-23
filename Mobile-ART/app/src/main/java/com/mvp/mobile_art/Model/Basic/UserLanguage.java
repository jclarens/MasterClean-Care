package com.mvp.mobile_art.Model.Basic;

/**
 * Created by Zackzack on 14/07/2017.
 */

public class UserLanguage {
    private User user;
    private Integer user_id;
    private Integer language_id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }
}
