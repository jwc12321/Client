package com.purchase.sls.goodsordermanage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/7/9.
 */

@Module
public class GoodsOrderModule {
    private GoodsOrderContract.GoodsOrderListView goodsOrderListView;
    private GoodsOrderContract.GoodsOrderDetailView goodsOrderDetailView;

    public GoodsOrderModule(GoodsOrderContract.GoodsOrderListView goodsOrderListView) {
        this.goodsOrderListView = goodsOrderListView;
    }

    public GoodsOrderModule(GoodsOrderContract.GoodsOrderDetailView goodsOrderDetailView) {
        this.goodsOrderDetailView = goodsOrderDetailView;
    }

    @Provides
    GoodsOrderContract.GoodsOrderListView provideGoodsOrderListView(){
        return goodsOrderListView;
    }

    @Provides
    GoodsOrderContract.GoodsOrderDetailView provideGoodsOrderDetailView(){
        return goodsOrderDetailView;
    }
}
