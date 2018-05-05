package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/5.
 */

public class StoreIdPageRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("storeid")
    private String storeid;

    public StoreIdPageRequest(String page, String storeid) {
        this.page = page;
        this.storeid = storeid;
    }
}
