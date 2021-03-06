package com.purchase.sls.data.request;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/27.
 */

public class CaptchaRequest {
    //电话
    @SerializedName("username")
    private String username;

    @SerializedName("type")
    private String type;
    @SerializedName("code")
    private String code;

    @SerializedName("password")
    private String password;

    public CaptchaRequest(String username, String type, String code, String password) {
        this.username = username;
        this.type = type;
        this.code = code;
        this.password = password;
    }
}
