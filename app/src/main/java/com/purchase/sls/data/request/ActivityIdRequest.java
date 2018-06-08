package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/8.
 */

public class ActivityIdRequest {
    @SerializedName("activityid")
    private String activityid;

    public ActivityIdRequest(String activityid) {
        this.activityid = activityid;
    }
}
