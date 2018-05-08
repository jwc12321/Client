package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/8.
 */

public class MessageListRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("type")
    private String type;

    public MessageListRequest(String page, String type) {
        this.page = page;
        this.type = type;
    }
}
