package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsItemRequest {
    //搜索
    @SerializedName("keyword")
    private String keyword;
    //排序类型 1销量2价格3券
    @SerializedName("orderby")
    private String orderby;
    //排序规则 1小到大/ 2大到小
    @SerializedName("order")
    private String order;
    //分类id
    @SerializedName("cate")
    private String cate;
    @SerializedName("page")
    private String page;

    public GoodsItemRequest(String keyword, String orderby, String order, String cate, String page) {
        this.keyword = keyword;
        this.orderby = orderby;
        this.order = order;
        this.cate = cate;
        this.page = page;
    }
}
