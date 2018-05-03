package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/3.
 */

public class CouponListRequest {
    @SerializedName("type")
    private String type;
    @SerializedName("page")
    private String page;

    public CouponListRequest(String type, String page) {
        this.type = type;
        this.page = page;
    }
}
