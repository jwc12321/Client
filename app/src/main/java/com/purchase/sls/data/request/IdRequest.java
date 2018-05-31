package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/31.
 */

public class IdRequest {
    @SerializedName("id")
    private String id;

    public IdRequest(String id) {
        this.id = id;
    }
}
