package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/11/27.
 */

public class CmIncomeInfo {
    @SerializedName("yesterday_money")
    private String yesterdayMoney;
    @SerializedName("month_money")
    private String monthMoney;
    @SerializedName("total_money")
    private String totalMoney;

    public String getYesterdayMoney() {
        return yesterdayMoney;
    }

    public void setYesterdayMoney(String yesterdayMoney) {
        this.yesterdayMoney = yesterdayMoney;
    }

    public String getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(String monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
