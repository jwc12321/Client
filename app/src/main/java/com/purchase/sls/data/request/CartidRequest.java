package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/7.
 */

public class CartidRequest {
    @SerializedName("cartid")
    private String cartid;
    @SerializedName("num")
    private String num;

    public CartidRequest(String cartid, String num) {
        this.cartid = cartid;
        this.num = num;
    }
}
