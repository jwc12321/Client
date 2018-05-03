package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyInfoRequest {
    @SerializedName("pool")
    private String pool;
    @SerializedName("page")
    private String page;

    public EnergyInfoRequest(String pool, String page) {
        this.pool = pool;
        this.page = page;
    }
}
