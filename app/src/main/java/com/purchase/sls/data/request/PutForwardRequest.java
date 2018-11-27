package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardRequest {
    @SerializedName("user_bank_id")
    private String userBankId;
    @SerializedName("price")
    private String price;

    public PutForwardRequest(String userBankId, String price) {
        this.userBankId = userBankId;
        this.price = price;
    }
}
