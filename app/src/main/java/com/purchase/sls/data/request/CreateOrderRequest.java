package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/10.
 */

public class CreateOrderRequest {
    @SerializedName("carts")
    private String carts ;
    @SerializedName("addressid")
    private String addressid;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("isquan")
    private String isquan;

    public CreateOrderRequest(String carts, String addressid, String paytype, String isquan) {
        this.carts = carts;
        this.addressid = addressid;
        this.paytype = paytype;
        this.isquan = isquan;
    }
}
