package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/28.
 */

public class GeneratingOrderRequest {
    @SerializedName("allprice")
    private String allprice;
    @SerializedName("storeid")
    private String storeid;
    @SerializedName("coupon")
    private String coupon;
    @SerializedName("power")
    private String power;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("notes")
    private String notes;
    @SerializedName("type")
    private String type;

    public GeneratingOrderRequest(String allprice, String storeid, String coupon, String power, String paytype, String notes,String type) {
        this.allprice = allprice;
        this.storeid = storeid;
        this.coupon = coupon;
        this.power = power;
        this.paytype = paytype;
        this.notes = notes;
        this.type=type;
    }
}
