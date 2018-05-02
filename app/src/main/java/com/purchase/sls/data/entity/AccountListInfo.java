package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountListInfo {
    @SerializedName("sum")
    private List<AccountSum> accountSums;
    @SerializedName("power")
    private List<Accountpower> accountpowers;
    @SerializedName("list")
    private AccountItemList accountItemList;

    public List<AccountSum> getAccountSums() {
        return accountSums;
    }

    public void setAccountSums(List<AccountSum> accountSums) {
        this.accountSums = accountSums;
    }

    public List<Accountpower> getAccountpowers() {
        return accountpowers;
    }

    public void setAccountpowers(List<Accountpower> accountpowers) {
        this.accountpowers = accountpowers;
    }

    public AccountItemList getAccountItemList() {
        return accountItemList;
    }

    public void setAccountItemList(AccountItemList accountItemList) {
        this.accountItemList = accountItemList;
    }

    public static class AccountSum{
        @SerializedName("sum")
        private String sum;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }
    }
    public static class Accountpower{
        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        @SerializedName("power")
        private String power;

    }
    public static class AccountItemList{
        @SerializedName("current_page")
        private String currentPage;
        @SerializedName("data")
        private List<AccountItemInfo> accountItemInfos;

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public List<AccountItemInfo> getAccountItemInfos() {
            return accountItemInfos;
        }

        public void setAccountItemInfos(List<AccountItemInfo> accountItemInfos) {
            this.accountItemInfos = accountItemInfos;
        }
    }
}
