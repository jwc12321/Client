package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/7.
 */

public class OrderDetailRequest {
    @SerializedName("orderno")
    private String orderno;

    public OrderDetailRequest(String orderno) {
        this.orderno = orderno;
    }
}
