package com.purchase.sls.data.entity;

/**
 * Created by JWC on 2018/4/24.
 */

public class ComprehensiveInfo {
    private String name;
    private String sort;

    public ComprehensiveInfo(String name, String sort) {
        this.name = name;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
