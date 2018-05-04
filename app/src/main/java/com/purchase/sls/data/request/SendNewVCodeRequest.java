package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class SendNewVCodeRequest {
    @SerializedName("newtel")
    private String newtel;

    public SendNewVCodeRequest(String newtel) {
        this.newtel = newtel;
    }
}
