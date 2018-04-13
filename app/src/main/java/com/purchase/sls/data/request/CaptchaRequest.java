package com.purchase.sls.data.request;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/27.
 */

public class CaptchaRequest {
    //电话
    @SerializedName("username")
    private String username;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("clientid")
    private String clientid;


    public CaptchaRequest(String username, String pwd, String clientid) {
        this.username = username;
        this.pwd = pwd;
        this.clientid = clientid;
    }
}
