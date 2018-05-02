package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountListRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("dates1")
    private String dates1;
    @SerializedName("dates2")
    private String dates2;

    public AccountListRequest(String page, String dates1, String dates2) {
        this.page = page;
        this.dates1 = dates1;
        this.dates2 = dates2;
    }
}
