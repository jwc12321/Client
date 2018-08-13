package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCategoriesInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("pic")
    private String pic;
    @SerializedName("list")
    private List<CategoriesItemInfo> categoriesItemInfos;

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

    public List<CategoriesItemInfo> getCategoriesItemInfos() {
        return categoriesItemInfos;
    }

    public void setCategoriesItemInfos(List<CategoriesItemInfo> categoriesItemInfos) {
        this.categoriesItemInfos = categoriesItemInfos;
    }
}
