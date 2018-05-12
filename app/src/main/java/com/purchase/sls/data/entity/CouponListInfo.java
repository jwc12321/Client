package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/3.
 */

public class CouponListInfo {
    @SerializedName("sd")
    private CouponList couponList;

    @SerializedName("sc")
    private ScItem scItem;

    public ScItem getScItem() {
        return scItem;
    }

    public void setScItem(ScItem scItem) {
        this.scItem = scItem;
    }

    public CouponList getCouponList() {
        return couponList;
    }

    public void setCouponList(CouponList couponList) {
        this.couponList = couponList;
    }

    public static class ScItem {
        @SerializedName("quannum")
        private String quannum;

        public String getQuannum() {
            return quannum;
        }

        public void setQuannum(String quannum) {
            this.quannum = quannum;
        }
    }

    public static class CouponList {
        @SerializedName("current_page")
        private String currentPage;
        @SerializedName("data")
        private List<CouponInfo> couponInfos;

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public List<CouponInfo> getCouponInfos() {
            return couponInfos;
        }

        public void setCouponInfos(List<CouponInfo> couponInfos) {
            this.couponInfos = couponInfos;
        }
    }
}
