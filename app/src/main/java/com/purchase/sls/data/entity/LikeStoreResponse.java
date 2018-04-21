package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/20.
 */

public class LikeStoreResponse {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<likeInfo> likeInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<likeInfo> getLikeInfos() {
        return likeInfos;
    }

    public void setLikeInfos(List<likeInfo> likeInfos) {
        this.likeInfos = likeInfos;
    }

    public static class likeInfo {
        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("z_pics")
        private String zPics;
        @SerializedName("buzz")
        private String buzz;
        @SerializedName("address_xy")
        private String address;
        @SerializedName("rebate")
        private String rebate;
        @SerializedName("average")
        private String average;
        @SerializedName("cid")
        private String cid;
        @SerializedName("bcate")
        private BcateInfo bcateInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getzPics() {
            return zPics;
        }

        public void setzPics(String zPics) {
            this.zPics = zPics;
        }

        public String getBuzz() {
            return buzz;
        }

        public void setBuzz(String buzz) {
            this.buzz = buzz;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public BcateInfo getBcateInfo() {
            return bcateInfo;
        }

        public void setBcateInfo(BcateInfo bcateInfo) {
            this.bcateInfo = bcateInfo;
        }

        public static class BcateInfo {
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;
            @SerializedName("scalea")
            private String scalea;
            @SerializedName("created_at")
            private String created_at;
            @SerializedName("updated_at")
            private String updated_at;
            @SerializedName("pid")
            private String pid;
            @SerializedName("aid")
            private String aid;
            @SerializedName("pic")
            private String pic;
            @SerializedName("status")
            private String status;
            @SerializedName("rank")
            private String rank;

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

            public String getScalea() {
                return scalea;
            }

            public void setScalea(String scalea) {
                this.scalea = scalea;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }
        }

    }
}
