package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/8/22.
 */

public class PayPwPowerRequest {
    @SerializedName("orderno")
    private String orderno;
    @SerializedName("paypassword")
    private String paypassword;

    public PayPwPowerRequest(String orderno, String paypassword) {
        this.orderno = orderno;
        this.paypassword = paypassword;
    }
}
