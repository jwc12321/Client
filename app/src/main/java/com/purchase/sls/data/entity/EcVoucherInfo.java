package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/8/2.
 */

public class EcVoucherInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<EcVoucherItem> ecVoucherItems;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<EcVoucherItem> getEcVoucherItems() {
        return ecVoucherItems;
    }

    public void setEcVoucherItems(List<EcVoucherItem> ecVoucherItems) {
        this.ecVoucherItems = ecVoucherItems;
    }
}
