package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/21.
 */

public class NearbyInfoRequest {
    @SerializedName("address")
    private String address;

    public NearbyInfoRequest(String address) {
        this.address = address;
    }
}
