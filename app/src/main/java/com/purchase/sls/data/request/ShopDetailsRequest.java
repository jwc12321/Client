package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/24.
 */

public class ShopDetailsRequest {
    @SerializedName("storeid")
    private String storeid;

    public ShopDetailsRequest(String storeid) {
        this.storeid = storeid;
    }
}
