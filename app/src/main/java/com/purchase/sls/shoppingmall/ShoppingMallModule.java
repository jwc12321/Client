package com.purchase.sls.shoppingmall;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/19.
 */

@Module
public class ShoppingMallModule {
    private ShoppingMallContract.GoodsListView goodsListView;
    private ShoppingMallContract.GoodsDetailView goodsDetailView;

    public ShoppingMallModule(ShoppingMallContract.GoodsListView goodsListView) {
        this.goodsListView = goodsListView;
    }

    public ShoppingMallModule(ShoppingMallContract.GoodsDetailView goodsDetailView) {
        this.goodsDetailView = goodsDetailView;
    }

    @Provides
    ShoppingMallContract.GoodsListView provideGoodsListView(){
        return goodsListView;
    }

    @Provides
    ShoppingMallContract.GoodsDetailView provideGoodsDetailView(){
        return goodsDetailView;
    }
}
