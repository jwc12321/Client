package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/1.
 */

public class UploadXyRequest {
    @SerializedName("address_x")
    private String addressX;
    @SerializedName("address_y")
    private String addressY;

    public UploadXyRequest(String addressX, String addressY) {
        this.addressX = addressX;
        this.addressY = addressY;
    }
}
