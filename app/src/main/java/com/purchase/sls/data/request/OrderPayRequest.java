package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/11.
 */

public class OrderPayRequest {
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("ordercode")
    private String ordercode;

    public OrderPayRequest(String paytype, String ordercode) {
        this.paytype = paytype;
        this.ordercode = ordercode;
    }
}
