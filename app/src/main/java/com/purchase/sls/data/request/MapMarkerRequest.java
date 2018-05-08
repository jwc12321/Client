package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/8.
 */

public class MapMarkerRequest {
    @SerializedName("cid")
    private String cid;
    @SerializedName("address_xy")
    private String addressXy;

    public MapMarkerRequest(String cid, String addressXy) {
        this.cid = cid;
        this.addressXy = addressXy;
    }
}
