package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/9.
 */

public class OrdernumRequest {
    @SerializedName("ordernum")
    private String ordernum;

    public OrdernumRequest(String ordernum) {
        this.ordernum = ordernum;
    }
}
