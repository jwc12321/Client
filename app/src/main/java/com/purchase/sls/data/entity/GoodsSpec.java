package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/7/6.
 */

public class GoodsSpec implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("spec")
    private List<String> specs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSpecs() {
        return specs;
    }

    public void setSpecs(List<String> specs) {
        this.specs = specs;
    }
}
