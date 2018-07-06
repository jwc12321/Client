package com.purchase.sls.data.entity;

/**
 * Created by JWC on 2018/7/6.
 */

public class ShoppingCartInfo {
    private String name;
    private String url;
    private String price;
    //选中
    private boolean isChoosed;
    //多少数量
    private int count;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
