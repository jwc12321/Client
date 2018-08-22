package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/8/22.
 */

public class PaysecKillRequest {
    @SerializedName("paypassword")
    private String paypassword;
    @SerializedName("id")
    private String id;
    @SerializedName("aid")
    private String aid;

    public PaysecKillRequest(String paypassword, String id, String aid) {
        this.paypassword = paypassword;
        this.id = id;
        this.aid = aid;
    }
}
