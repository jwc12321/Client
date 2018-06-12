package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/12.
 */

public class ReceiveCouponRequest {
    @SerializedName("id")
    private String id;
    @SerializedName("orderno")
    private String orderno;

    public ReceiveCouponRequest(String id, String orderno) {
        this.id = id;
        this.orderno = orderno;
    }
}
