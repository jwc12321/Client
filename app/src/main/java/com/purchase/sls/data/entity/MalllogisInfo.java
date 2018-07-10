package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/7/10.
 */

public class MalllogisInfo implements Serializable{
    @SerializedName("LogisticCode")
    private String logisticCode;
    @SerializedName("ShipperCode")
    private String shipperCode;
    @SerializedName("Traces")
    private List<LogisticRracesInfo> logisticRracesInfos;

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public List<LogisticRracesInfo> getLogisticRracesInfos() {
        return logisticRracesInfos;
    }

    public void setLogisticRracesInfos(List<LogisticRracesInfo> logisticRracesInfos) {
        this.logisticRracesInfos = logisticRracesInfos;
    }
}
