package com.purchase.sls.shopdetailbuy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/24.
 */

@Module
public class ShopDetailBuyModule {
    private ShopDetailBuyContract.ShopDetailView shopDetailView;

    public ShopDetailBuyModule(ShopDetailBuyContract.ShopDetailView shopDetailView) {
        this.shopDetailView = shopDetailView;
    }
    @Provides
    ShopDetailBuyContract.ShopDetailView provideShopDetailView(){
        return shopDetailView;
    }
}
