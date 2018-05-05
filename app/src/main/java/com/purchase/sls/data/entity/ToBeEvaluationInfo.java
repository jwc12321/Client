package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/5.
 */

public class ToBeEvaluationInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<ToBeEvaluationItem> toBeEvaluationItems;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<ToBeEvaluationItem> getToBeEvaluationItems() {
        return toBeEvaluationItems;
    }

    public void setToBeEvaluationItems(List<ToBeEvaluationItem> toBeEvaluationItems) {
        this.toBeEvaluationItems = toBeEvaluationItems;
    }

    public static class ToBeEvaluationItem{
        @SerializedName("orderid")
        private String orderid;
        @SerializedName("storeid")
        private String storeid;
        @SerializedName("allprice")
        private String allprice;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("z_pics")
        private String zPics;
        @SerializedName("title")
        private String title;
        @SerializedName("address")
        private String address;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getzPics() {
            return zPics;
        }

        public void setzPics(String zPics) {
            this.zPics = zPics;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
