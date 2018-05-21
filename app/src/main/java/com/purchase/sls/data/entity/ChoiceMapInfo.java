package com.purchase.sls.data.entity;

/**
 * Created by JWC on 2018/5/21.
 */

public class ChoiceMapInfo {
    private String packName;
    private String appName;

    public ChoiceMapInfo(String packName, String appName) {
        this.packName = packName;
        this.appName = appName;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
