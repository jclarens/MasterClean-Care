package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class Token extends Response {
    private String token_type;
    private long expires_in;
    private String access_token;
    private String refresh_token;
    private User user;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
