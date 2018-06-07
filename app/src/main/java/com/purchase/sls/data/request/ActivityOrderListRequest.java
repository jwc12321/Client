package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/7.
 */

public class ActivityOrderListRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("type")
    private String type;

    public ActivityOrderListRequest(String page, String type) {
        this.page = page;
        this.type = type;
    }
}
