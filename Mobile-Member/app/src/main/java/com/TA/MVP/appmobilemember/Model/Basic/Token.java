package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by jcla123ns on 02/08/17.
 */

public class Token {
    private String token_type;
    private double expired_in;
    private String access_token;
    private String refresh_token;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public double getExpired_in() {
        return expired_in;
    }

    public void setExpired_in(double expired_in) {
        this.expired_in = expired_in;
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
}
