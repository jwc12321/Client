package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/3.
 */

public class PageRequest {
    @SerializedName("page")
    private String page;

    public PageRequest(String page) {
        this.page = page;
    }
}
