package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/28.
 */

public class UserpowerRequest {
    @SerializedName("price")
    private String price;
    @SerializedName("storeid")
    private String storeid;

    public UserpowerRequest(String price, String storeid) {
        this.price = price;
        this.storeid = storeid;
    }
}
