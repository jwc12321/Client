package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/12/12.
 */

public class GoodsShareLinkInfo {
    @SerializedName("qrcode")
    private String qrcode;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
