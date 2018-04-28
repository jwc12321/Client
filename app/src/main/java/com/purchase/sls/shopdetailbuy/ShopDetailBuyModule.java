package com.purchase.sls.shopdetailbuy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/4/24.
 */

@Module
public class ShopDetailBuyModule {
    private ShopDetailBuyContract.ShopDetailView shopDetailView;
    private ShopDetailBuyContract.PaymentOrderView paymentOrderView;

    public ShopDetailBuyModule(ShopDetailBuyContract.ShopDetailView shopDetailView) {
        this.shopDetailView = shopDetailView;
    }

    public ShopDetailBuyModule(ShopDetailBuyContract.PaymentOrderView paymentOrderView) {
        this.paymentOrderView = paymentOrderView;
    }

    @Provides
    ShopDetailBuyContract.ShopDetailView provideShopDetailView(){
        return shopDetailView;
    }
    @Provides
    ShopDetailBuyContract.PaymentOrderView providePaymentOrderView(){
        return paymentOrderView;
    }
}
