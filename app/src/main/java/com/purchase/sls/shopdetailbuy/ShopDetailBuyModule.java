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
    private ShopDetailBuyContract.OrderDetailView orderDetailView;

    public ShopDetailBuyModule(ShopDetailBuyContract.ShopDetailView shopDetailView) {
        this.shopDetailView = shopDetailView;
    }

    public ShopDetailBuyModule(ShopDetailBuyContract.PaymentOrderView paymentOrderView) {
        this.paymentOrderView = paymentOrderView;
    }

    public ShopDetailBuyModule(ShopDetailBuyContract.OrderDetailView orderDetailView) {
        this.orderDetailView = orderDetailView;
    }

    @Provides
    ShopDetailBuyContract.ShopDetailView provideShopDetailView(){
        return shopDetailView;
    }
    @Provides
    ShopDetailBuyContract.PaymentOrderView providePaymentOrderView(){
        return paymentOrderView;
    }
    @Provides
    ShopDetailBuyContract.OrderDetailView provideOrderDetailView(){
        return orderDetailView;
    }
}
