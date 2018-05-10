package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/28.
 */

public class UserpowerInfo {
    @SerializedName("userinfo")
    private PersionInfoResponse persionInfoResponse;
    @SerializedName("coupon")
    private List<CouponInfo> couponInfos;
    @SerializedName("proportion")
    private String proportion;

    public PersionInfoResponse getPersionInfoResponse() {
        return persionInfoResponse;
    }

    public void setPersionInfoResponse(PersionInfoResponse persionInfoResponse) {
        this.persionInfoResponse = persionInfoResponse;
    }

    public List<CouponInfo> getCouponInfos() {
        return couponInfos;
    }

    public void setCouponInfos(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}
