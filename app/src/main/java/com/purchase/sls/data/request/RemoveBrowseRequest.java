package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class RemoveBrowseRequest {
    @SerializedName("id")
    private String[] idArray;

    public RemoveBrowseRequest(String[] idArray) {
        this.idArray = idArray;
    }
}
