package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/10.
 */

public class ChangepwdRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("password")
    private String password;
    @SerializedName("type")
    private String type;

    public ChangepwdRequest(String tel, String password, String type) {
        this.tel = tel;
        this.password = password;
        this.type = type;
    }
}
