package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/7.
 */

public class CartidRequest {
    @SerializedName("cartid")
    private String cartid;

    public CartidRequest(String cartid) {
        this.cartid = cartid;
    }
}
