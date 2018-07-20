package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/20.
 */

public class BannerHotResponse {
    @SerializedName("banner")
    private List<BannerInfo> bannerInfos;
    @SerializedName("storecate")
    private List<StorecateInfo> storecateInfos;
    @SerializedName("article")
    private ArticleInfo articleInfo;

    public List<BannerInfo> getBannerInfos() {
        return bannerInfos;
    }

    public void setBannerInfos(List<BannerInfo> bannerInfos) {
        this.bannerInfos = bannerInfos;
    }

    public List<StorecateInfo> getStorecateInfos() {
        return storecateInfos;
    }

    public void setStorecateInfos(List<StorecateInfo> storecateInfos) {
        this.storecateInfos = storecateInfos;
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public static class BannerInfo {
        @SerializedName("id")
        private String id;
        @SerializedName("displayorder")
        private String displayorder;
        @SerializedName("banner")
        private String banner;
        @SerializedName("ids")
        private String ids;
        @SerializedName("type")
        private String type;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("aid")
        private String aid;
        @SerializedName("page_url")
        private String page_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(String displayorder) {
            this.displayorder = displayorder;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getPage_url() {
            return page_url;
        }

        public void setPage_url(String page_url) {
            this.page_url = page_url;
        }

    }

    public static class StorecateInfo {
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

    public static class ArticleInfo {
        @SerializedName("current_page")
        private String currentPage;
        @SerializedName("data")
        private List<Datainfo> datainfos;

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public List<Datainfo> getDatainfos() {
            return datainfos;
        }

        public void setDatainfos(List<Datainfo> datainfos) {
            this.datainfos = datainfos;
        }

        public static class Datainfo {
            @SerializedName("id")
            private String id;
            @SerializedName("title")
            private String title;
            @SerializedName("cate")
            private String cate;

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

            public String getCate() {
                return cate;
            }

            public void setCate(String cate) {
                this.cate = cate;
            }
        }
    }
}
