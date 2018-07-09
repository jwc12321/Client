package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/9.
 */

public class TypePageRequest {
    @SerializedName("type")
    private String type;
    @SerializedName("page")
    private String page;

    public TypePageRequest(String type, String page) {
        this.type = type;
        this.page = page;
    }
}
