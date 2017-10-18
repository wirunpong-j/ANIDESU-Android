package com.bellkung.anidesu.model;

/**
 * Created by BellKunG on 18/10/2017 AD.
 */

public class AccessToken {
    private String access_token;
    private String token_type;
    private int expires;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires() {
        return expires;
    }

    public int getExpires_in() {
        return expires_in;
    }
}
