package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/26.
 */

public class CollectionListRequest {
    @SerializedName("page")
    private String page;

    public CollectionListRequest(String page) {
        this.page = page;
    }
}
