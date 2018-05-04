package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class CheckNewCodeRequest {
    @SerializedName("newtel")
    private String newtel;
    @SerializedName("code")
    private String code;

    public CheckNewCodeRequest(String newtel, String code) {
        this.newtel = newtel;
        this.code = code;
    }
}
