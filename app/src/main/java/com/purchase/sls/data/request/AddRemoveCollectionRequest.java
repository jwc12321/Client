package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/27.
 */

public class AddRemoveCollectionRequest {
    @SerializedName("storeid")
    private String storeid;
    //1/添加收藏   2/删除收藏
    @SerializedName("type")
    private String type;
    @SerializedName("fid")
    private String[] fidArray;

    public AddRemoveCollectionRequest(String storeid, String type, String[] fidArray) {
        this.storeid = storeid;
        this.type = type;
        this.fidArray = fidArray;
    }
}
