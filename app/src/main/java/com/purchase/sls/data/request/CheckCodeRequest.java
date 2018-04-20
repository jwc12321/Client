package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/19.
 */

public class CheckCodeRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;

    public CheckCodeRequest(String tel, String code, String type) {
        this.tel = tel;
        this.code = code;
        this.type = type;
    }
}
