package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/20.
 */

public class BannerHotRequest {
    //地区
    @SerializedName("areaname")
    private String areaname;

    public BannerHotRequest(String areaname) {
        this.areaname = areaname;
    }
}
