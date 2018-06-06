package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/6.
 */

public class SubmitSpikeRequest {
    @SerializedName("id")
    private String id;
    @SerializedName("aid")
    private String aid;

    public SubmitSpikeRequest(String id, String aid) {
        this.id = id;
        this.aid = aid;
    }
}
