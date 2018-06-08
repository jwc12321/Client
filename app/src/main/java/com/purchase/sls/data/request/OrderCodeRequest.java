package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/8.
 */

public class OrderCodeRequest {
    @SerializedName("orderCode")
    private String orderCode;

    public OrderCodeRequest(String orderCode) {
        this.orderCode = orderCode;
    }
}
