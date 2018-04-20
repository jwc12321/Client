package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/17.
 */

public class LoginRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("pwd")
    private String pwd;
    @SerializedName("clientid")
    private String clientid;

    public LoginRequest(String username, String pwd, String clientid) {
        this.username = username;
        this.pwd = pwd;
        this.clientid = clientid;
    }
}
