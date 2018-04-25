package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/24.
 */

public class ShopDetailsInfo {
    @SerializedName("storeinfo")
    private StoreInfo storeInfo;
    @SerializedName("result")
    private EvaluateResult evaluateResult;
    @SerializedName("moreStore")
    private LikeStoreResponse likeStoreResponse;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public EvaluateResult getEvaluateResult() {
        return evaluateResult;
    }

    public void setEvaluateResult(EvaluateResult evaluateResult) {
        this.evaluateResult = evaluateResult;
    }

    public LikeStoreResponse getLikeStoreResponse() {
        return likeStoreResponse;
    }

    public void setLikeStoreResponse(LikeStoreResponse likeStoreResponse) {
        this.likeStoreResponse = likeStoreResponse;
    }

    public static class EvaluateResult {
        @SerializedName("total")
        private String total;
        @SerializedName("lists")
        private EvaluateInfo evaluateInfo;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public EvaluateInfo getEvaluateInfo() {
            return evaluateInfo;
        }

        public void setEvaluateInfo(EvaluateInfo evaluateInfo) {
            this.evaluateInfo = evaluateInfo;
        }
    }
}
