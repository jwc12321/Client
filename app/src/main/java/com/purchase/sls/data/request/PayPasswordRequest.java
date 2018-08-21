package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/8/21.
 */

public class PayPasswordRequest {
    @SerializedName("paypassword")
    private String paypassword;

    public PayPasswordRequest(String paypassword) {
        this.paypassword = paypassword;
    }
}
