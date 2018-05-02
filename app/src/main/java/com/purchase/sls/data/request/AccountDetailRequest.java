package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountDetailRequest {
    @SerializedName("billid")
    private String billid;

    public AccountDetailRequest(String billid) {
        this.billid = billid;
    }
}
