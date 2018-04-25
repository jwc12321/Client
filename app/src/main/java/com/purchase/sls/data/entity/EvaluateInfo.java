package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/24.
 * 评价信息
 */

public class EvaluateInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<EvaluateItemInfo> evaluateItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<EvaluateItemInfo> getEvaluateItemInfos() {
        return evaluateItemInfos;
    }

    public void setEvaluateItemInfos(List<EvaluateItemInfo> evaluateItemInfos) {
        this.evaluateItemInfos = evaluateItemInfos;
    }

    public static class EvaluateItemInfo{
        @SerializedName("id")
        private String id;
        @SerializedName("uid")
        private String uid;
        @SerializedName("storeid")
        private String storeid;
        @SerializedName("starts")
        private String starts;
        @SerializedName("pics")
        private String pics;
        @SerializedName("zans")
        private String zans;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("type")
        private String type;
        @SerializedName("words")
        private String words;
        @SerializedName("users")
        private List<usersInfo> usersInfos ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public String getStarts() {
            return starts;
        }

        public void setStarts(String starts) {
            this.starts = starts;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public String getZans() {
            return zans;
        }

        public void setZans(String zans) {
            this.zans = zans;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public List<usersInfo> getUsersInfos() {
            return usersInfos;
        }

        public void setUsersInfos(List<usersInfo> usersInfos) {
            this.usersInfos = usersInfos;
        }

        public static class usersInfo{
            @SerializedName("id")
            private String id;
            @SerializedName("username")
            private String username;
            @SerializedName("avatar")
            private String avatar;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
