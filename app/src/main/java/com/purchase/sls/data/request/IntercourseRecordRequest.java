package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/2.
 */

public class IntercourseRecordRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("storeid")
    private String storeid;

    public IntercourseRecordRequest(String page, String storeid) {
        this.page = page;
        this.storeid = storeid;
    }
}
