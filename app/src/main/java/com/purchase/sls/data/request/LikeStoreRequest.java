package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/20.
 */

public class LikeStoreRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("areaname")
    private String areaname;

    public LikeStoreRequest(String page, String areaname) {
        this.page = page;
        this.areaname = areaname;
    }
}
