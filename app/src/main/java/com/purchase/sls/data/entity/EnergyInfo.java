package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyInfo {
    @SerializedName("sumPower")
    private SumPower sumPower;
    @SerializedName("userlog")
    private Userlog userlog;

    public SumPower getSumPower() {
        return sumPower;
    }

    public void setSumPower(SumPower sumPower) {
        this.sumPower = sumPower;
    }

    public Userlog getUserlog() {
        return userlog;
    }

    public void setUserlog(Userlog userlog) {
        this.userlog = userlog;
    }

    public static class SumPower {
        @SerializedName("power")
        private String power;
        @SerializedName("dpower")
        private String dpower;

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getDpower() {
            return dpower;
        }

        public void setDpower(String dpower) {
            this.dpower = dpower;
        }
    }

    public static class Userlog {
        @SerializedName("current_page")
        private String currentPage;
        @SerializedName("data")
        private List<EnergyIncomeDetail> energyIncomeDetails;

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public List<EnergyIncomeDetail> getEnergyIncomeDetails() {
            return energyIncomeDetails;
        }

        public void setEnergyIncomeDetails(List<EnergyIncomeDetail> energyIncomeDetails) {
            this.energyIncomeDetails = energyIncomeDetails;
        }
    }
}
