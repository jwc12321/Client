package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/6/8.
 */

public class LogisticRracesInfo implements Serializable {
    @SerializedName("AcceptStation")
    private String acceptStation;
    @SerializedName("AcceptTime")
    private String acceptTime;

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }
}
