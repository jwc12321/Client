package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/7.
 */

public class OrderDetailInfo {
    @SerializedName("results")
    private ResultsItem resultsItem;
    @SerializedName("order")
    private OrderItem orderItem;

    public ResultsItem getResultsItem() {
        return resultsItem;
    }

    public void setResultsItem(ResultsItem resultsItem) {
        this.resultsItem = resultsItem;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public static class ResultsItem{
        @SerializedName("quans")
        List<QuanInfo> quanInfos;
        @SerializedName("power")
        private String power;

        public List<QuanInfo> getQuanInfos() {
            return quanInfos;
        }

        public void setQuanInfos(List<QuanInfo> quanInfos) {
            this.quanInfos = quanInfos;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }
    }
    public static class OrderItem{
        @SerializedName("id")
        private String id;
        @SerializedName("storeid")
        private String storeid;
        @SerializedName("allprice")
        private String allprice;
        @SerializedName("price")
        private String price;
        @SerializedName("power")
        private String power;
        @SerializedName("quannum")
        private String quannum;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("status")
        private String status;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("uid")
        private String uid;
        @SerializedName("quan_id")
        private String quanId;
        @SerializedName("orderno")
        private String orderno;
        @SerializedName("issend")
        private String issend;
        @SerializedName("pinglun")
        private String pinglun;
        @SerializedName("paytype")
        private String paytype;
        @SerializedName("has_quan")
        private String hasQuan;
        @SerializedName("notes")
        private String notes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public String getAllprice() {
            return allprice;
        }

        public void setAllprice(String allprice) {
            this.allprice = allprice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getQuannum() {
            return quannum;
        }

        public void setQuannum(String quannum) {
            this.quannum = quannum;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getQuanId() {
            return quanId;
        }

        public void setQuanId(String quanId) {
            this.quanId = quanId;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getIssend() {
            return issend;
        }

        public void setIssend(String issend) {
            this.issend = issend;
        }

        public String getPinglun() {
            return pinglun;
        }

        public void setPinglun(String pinglun) {
            this.pinglun = pinglun;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getHasQuan() {
            return hasQuan;
        }

        public void setHasQuan(String hasQuan) {
            this.hasQuan = hasQuan;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
