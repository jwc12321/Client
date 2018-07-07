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
    private ShoppingMallContract.GoodsSearchView goodsSearchView;
    private ShoppingMallContract.ShoppingCartView shoppingCartView;
    private ShoppingMallContract.FillOrderView fillOrderView;

    public ShoppingMallModule(ShoppingMallContract.GoodsListView goodsListView) {
        this.goodsListView = goodsListView;
    }

    public ShoppingMallModule(ShoppingMallContract.GoodsDetailView goodsDetailView) {
        this.goodsDetailView = goodsDetailView;
    }

    public ShoppingMallModule(ShoppingMallContract.GoodsSearchView goodsSearchView) {
        this.goodsSearchView = goodsSearchView;
    }

    public ShoppingMallModule(ShoppingMallContract.ShoppingCartView shoppingCartView) {
        this.shoppingCartView = shoppingCartView;
    }

    public ShoppingMallModule(ShoppingMallContract.FillOrderView fillOrderView) {
        this.fillOrderView = fillOrderView;
    }

    @Provides
    ShoppingMallContract.GoodsListView provideGoodsListView() {
        return goodsListView;
    }

    @Provides
    ShoppingMallContract.GoodsDetailView provideGoodsDetailView() {
        return goodsDetailView;
    }

    @Provides
    ShoppingMallContract.GoodsSearchView provideGoodsSearchView() {
        return goodsSearchView;
    }

    @Provides
    ShoppingMallContract.ShoppingCartView provideShoppingCartView(){
        return shoppingCartView;
    }

    @Provides
    ShoppingMallContract.FillOrderView provideFillOrderView(){
        return fillOrderView;
    }
}
