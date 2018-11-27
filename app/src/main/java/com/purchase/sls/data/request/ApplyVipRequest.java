package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/11/27.
 */

public class ApplyVipRequest {
    @SerializedName("idcard")
    private String idcard;
    @SerializedName("realname")
    private String realname;

    public ApplyVipRequest(String idcard, String realname) {
        this.idcard = idcard;
        this.realname = realname;
    }
}
