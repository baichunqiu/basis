package com.qunli.ui;

import java.io.Serializable;

/**
 * 登录token
 */
public class TokenBean implements Serializable {
    // token
    private String access_token;
    // token类型
    private String token_type;
    // 失效时间
    private String expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
