package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/23.
 */

public class ScreeningListResponse {
    @SerializedName("cate")
    private List<CateInfo> cateInfos;
    @SerializedName("stores")
    private LikeStoreResponse likeStoreResponse;

    public List<CateInfo> getCateInfos() {
        return cateInfos;
    }

    public void setCateInfos(List<CateInfo> cateInfos) {
        this.cateInfos = cateInfos;
    }

    public LikeStoreResponse getLikeStoreResponse() {
        return likeStoreResponse;
    }

    public void setLikeStoreResponse(LikeStoreResponse likeStoreResponse) {
        this.likeStoreResponse = likeStoreResponse;
    }

    public static class CateInfo {
        @SerializedName("sum")
        private String sum;
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("pic")
        private String pic;
        @SerializedName("pid")
        private String pid;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
