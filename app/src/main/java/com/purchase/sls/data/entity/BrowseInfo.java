package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/3.
 */

public class BrowseInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<BrowseItemInfo> browseItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<BrowseItemInfo> getBrowseItemInfos() {
        return browseItemInfos;
    }

    public void setBrowseItemInfos(List<BrowseItemInfo> browseItemInfos) {
        this.browseItemInfos = browseItemInfos;
    }

    public static class BrowseItemInfo {
        @SerializedName("id")
        private String id;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("history")
        private String history;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("store")
        private Store store;
        //选中
        private boolean isChoosed=false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Store getStore() {
            return store;
        }

        public void setStore(Store store) {
            this.store = store;
        }

        public boolean isChoosed() {
            return isChoosed;
        }

        public void setChoosed(boolean choosed) {
            isChoosed = choosed;
        }

        public static class Store {
            @SerializedName("id")
            private String id;
            @SerializedName("title")
            private String title;
            @SerializedName("buzz")
            private String buzz;
            @SerializedName("z_pics")
            private String zPics;
            @SerializedName("address_xy")
            private String addressXy;
            @SerializedName("name")
            private String name;
            @SerializedName("average")
            private String average;

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

            public String getBuzz() {
                return buzz;
            }

            public void setBuzz(String buzz) {
                this.buzz = buzz;
            }

            public String getzPics() {
                return zPics;
            }

            public void setzPics(String zPics) {
                this.zPics = zPics;
            }

            public String getAddressXy() {
                return addressXy;
            }

            public void setAddressXy(String addressXy) {
                this.addressXy = addressXy;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }
        }

    }

}
