package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class DetectionVersionRequest {
    @SerializedName("edition")
    private String edition;
    @SerializedName("type")
    private String type;

    public DetectionVersionRequest(String edition, String type) {
        this.edition = edition;
        this.type = type;
    }
}
