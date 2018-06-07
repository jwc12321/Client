package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/7.
 */

public class ActivityOrderListResponse {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<ActivityOrderInfo> orderInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<ActivityOrderInfo> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(List<ActivityOrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
    }
}
