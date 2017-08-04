package com.mvp.mobile_art.Model.Responses;
import com.mvp.mobile_art.Model.Basic.User;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by jcla123ns on 02/08/17.
 */

public class LoginResponse extends Response {
    private User user;
    private Token token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
