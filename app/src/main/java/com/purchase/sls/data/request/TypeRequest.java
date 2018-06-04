package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/4.
 */

public class TypeRequest {
    @SerializedName("type")
    private String type;

    public TypeRequest(String type) {
        this.type = type;
    }
}
