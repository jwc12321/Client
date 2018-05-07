package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JWC on 2018/4/23.
 */

public class ScreeningListRequest {
    @SerializedName("address")
    private String address;
    @SerializedName("cid")
    private String cid;
    @SerializedName("sort")
    private String sort;
    @SerializedName("page")
    private String page;
    @SerializedName("screen")
    private String screen;
    @SerializedName("storename")
    private String storename;

    public ScreeningListRequest(String address, String cid, String sort, String page, String screen, String storename) {
        this.address = address;
        this.cid = cid;
        this.sort = sort;
        this.page = page;
        this.screen = screen;
        this.storename = storename;
    }
}
