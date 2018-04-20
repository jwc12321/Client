package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/19.
 */

public class PhoneLoginRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("code")
    private String code;

    public PhoneLoginRequest(String tel, String code) {
        this.tel = tel;
        this.code = code;
    }
}
