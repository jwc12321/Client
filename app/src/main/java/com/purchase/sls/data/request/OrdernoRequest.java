package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/13.
 */

public class OrdernoRequest {
    @SerializedName("orderno")
    private String orderno;

    public OrdernoRequest(String orderno) {
        this.orderno = orderno;
    }
}
