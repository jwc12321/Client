package com.purchase.sls.data.entity;

/**
 * Created by JWC on 2018/4/24.
 */

public class MealsNumberInfo {
    private String screenId;
    private String number;

    public MealsNumberInfo(String screenId, String number) {
        this.screenId = screenId;
        this.number = number;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
