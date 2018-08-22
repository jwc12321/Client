package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/8/22.
 */

public class SetPayPwRequest {
    @SerializedName("paypassword")
    private String paypassword;
    @SerializedName("type")
    private String type;
    @SerializedName("details")
    private String details;

    public SetPayPwRequest(String paypassword, String type, String details) {
        this.paypassword = paypassword;
        this.type = type;
        this.details = details;
    }
}
