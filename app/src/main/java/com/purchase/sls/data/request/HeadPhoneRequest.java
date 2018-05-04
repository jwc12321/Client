package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class HeadPhoneRequest {
    @SerializedName("file")
    private String file;

    public HeadPhoneRequest(String file) {
        this.file = file;
    }
}
