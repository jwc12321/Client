package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/11/27.
 */

public class AddBankCardRequest {
    @SerializedName("bank_number")
    private String bankNumber;
    @SerializedName("bank_name")
    private String bankName;
    @SerializedName("bank_subbranch_name")
    private String bankSubbranchName;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("owner_name")
    private String ownerName;

    public AddBankCardRequest(String bankNumber, String bankName, String bankSubbranchName, String mobile, String ownerName) {
        this.bankNumber = bankNumber;
        this.bankName = bankName;
        this.bankSubbranchName = bankSubbranchName;
        this.mobile = mobile;
        this.ownerName = ownerName;
    }
}
