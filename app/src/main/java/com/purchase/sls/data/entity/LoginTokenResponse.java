package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/17.
 */

public class LoginTokenResponse {
    //结果
    @SerializedName("result")
    private String result;
    //token
    @SerializedName("jwt")
    private String token;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
