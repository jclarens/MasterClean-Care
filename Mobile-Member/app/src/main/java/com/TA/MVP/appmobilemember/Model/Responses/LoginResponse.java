package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Token;
import com.TA.MVP.appmobilemember.Model.Basic.User;
import com.TA.MVP.appmobilemember.lib.models.Response;

/**
 * Created by jcla123ns on 02/08/17.
 */

public class LoginResponse extends Response{
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
