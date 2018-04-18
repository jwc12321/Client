package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/17.
 */

public class LoginRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("type")
    private String type;
    @SerializedName("code")
    private String code;
    @SerializedName("password")
    private String password;

    public LoginRequest(String username, String type, String code, String password) {
        this.username = username;
        this.type = type;
        this.code = code;
        this.password = password;
    }
}
