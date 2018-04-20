package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/19.
 */

public class SendCodeRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("do")
    private String doStr;

    public SendCodeRequest(String tel, String doStr) {
        this.tel = tel;
        this.doStr = doStr;
    }
}
