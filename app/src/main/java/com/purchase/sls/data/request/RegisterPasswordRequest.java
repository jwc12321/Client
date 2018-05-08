package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/19.
 */

public class RegisterPasswordRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("password")
    private String password;
    @SerializedName("address")
    private String address;
    @SerializedName("type")
    private String type;
    @SerializedName("storeid")
    private String storeid;

    public RegisterPasswordRequest(String tel, String password, String address, String type, String storeid) {
        this.tel = tel;
        this.password = password;
        this.address = address;
        this.type = type;
        this.storeid = storeid;
    }
}
