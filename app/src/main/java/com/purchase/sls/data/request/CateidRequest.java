package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/13.
 */

public class CateidRequest {
    @SerializedName("cateid")
    private String cateid;

    public CateidRequest(String cateid) {
        this.cateid = cateid;
    }
}
